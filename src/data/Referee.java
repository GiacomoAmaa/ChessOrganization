package data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

/**
 * A representation of a data instance of a referee in the database.
 */
public class Referee {

    private int cardNumber;
    private String username; 
    private String password;
    private String cf;
    private String name;
    private String lastname;
    
    /**
     * the constructor.
     * @param cardNumber
     * @param username
     * @param password
     * @param cf
     * @param name
     * @param lastname
     */
    public Referee(final int cardNumber, final String username, final String password,
            final String cf, final String name, final String lastname) {
        this.cardNumber = cardNumber;
        this.username = username;
        this.password = password;
        this.cf = cf;
        this.name = name;
        this.lastname = lastname;
    }
    
    /**
     * getter for the card number (id).
     * @return the id
     */
    public int getCardNumber() {
        return cardNumber;
    }

    /**
     * getter for the username.
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * getter for the password.
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * getter for the cf.
     * @return the code
     */
    public String getCf() {
        return cf;
    }

    /**
     * getter for the name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * getter for the lastname.
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Utility class for the interations with the database.
     */
    public static final class DAO {

        public static int getWhite(final Connection conn, final int gameId) {
            try (var stmt = DAOUtils.prepare(conn, Queries.GET_GAME_WHITE, gameId)) {
                var resultSet = stmt.executeQuery();
                return resultSet.next() ? resultSet.getInt("idiscrizione") : -1;
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }

        public static int getBlack(final Connection conn, final int gameId) {
            try (var stmt = DAOUtils.prepare(conn, Queries.GET_GAME_BLACK, gameId)) {
                var resultSet = stmt.executeQuery();
                return resultSet.next() ? resultSet.getInt("idiscrizione") : -1;

            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }

        public static int registerMove(final Connection conn, final int subscrId, final int gameId, final char piece, final Optional<Character> newPiece,
                final Optional<Character> takenPiece, final boolean cMate, final char arrCol, final int arrLine, final char startCol,
                final int startLine, final String move) {

            try (var stmt = DAOUtils.prepare(conn, Queries.GAME_ADD_MOVE, subscrId, gameId, piece,
                    newPiece.isPresent() ? newPiece.get().charValue() : null,
                    takenPiece.isPresent() ? takenPiece.get().charValue() : null,
                    cMate, arrCol, arrLine, startCol, startLine, move)) {

                stmt.executeUpdate();
                var resultSet = stmt.getGeneratedKeys();
                return resultSet.next() ? resultSet.getInt("idmossa") : -1;

            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }

        public static int registerTurn(final Connection conn, final int whiteMove,  final Optional<Integer> blackMove, final int turn) {
            try (var stmt = DAOUtils.prepare(conn, Queries.GAME_ADD_TURN, whiteMove,
                    blackMove.isPresent() ? blackMove.get().intValue() : null, turn)) {
                stmt.executeUpdate();
                var resultSet = stmt.getGeneratedKeys();
                return resultSet.next() ? resultSet.getInt("idturno") : -1;
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }

        public static int getId(final Connection conn, final String username, final String password) {
            try (var stmt = DAOUtils.prepare(conn,  Queries.REF_GET_ID, username, password)) {
                var resultSet = stmt.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt("numtessera");
                }
                return -1;
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }

        public static Optional<Referee> exists (final Connection conn, final String username,
               final String password, final int cardNumber) {
            try (var stmt = DAOUtils.prepare(conn, Queries.REFEREE_EXISTS, username, password, cardNumber)) {
                var resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    String name = resultSet.getString("nome"),
                        lastname = resultSet.getString("cognome"),
                        cf = resultSet.getString("cf");
                    return Optional.of(new Referee(cardNumber, username, password, name, lastname, cf));
                }
                return Optional.empty();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }
}
