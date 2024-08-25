package data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import board.MoveParser;
import util.Pair;

/**
 * A representation of a data instance of a player in the database.
 */
public final class Player {

    private int id;
    private int elo;
    private String username; 
    private String password;
    private String cf;
    private String name;
    private String lastname;

    /**
     * the constructor.
     * @param id
     * @param elo
     * @param username
     * @param password
     * @param cf
     * @param name
     * @param lastname
     */
    public Player(final int id, final int elo, final String username,
            final String password, final String cf, final String name, final String lastname) {
        this.id = id;
        this.elo = elo;
        this.username = username;
        this.password = password;
        this.cf = cf;
        this.name = name;
        this.lastname = lastname;
    }

    /**
     * getter for the id.
     * @return the id
     */
    public int getId() {
        return this.id;
    }

    /**
     * getter for the elo score.
     * @return the int score
     */
    public int getElo() {
        return this.elo;
    }

    /**
     * getter for the password.
     * @return the password string
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * getter for the cf.
     * @return the code
     */
    public String getCf() {
        return this.cf;
    }

    /**
     * geter for the name.
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * getter for the lastname.
     * @return the lastname
     */
    public String getLastname() {
        return this.lastname;
    }

    /**
     * getter for the username.
     * @return the username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Utility class for the interations with the database.
     */
    public static final class DAO {

        /**
         * searches for a player through the db.
         * @param conn the connection to the bd
         * @param partialName input string for the name
         * @param partialSurname input string for the surname
         * @return a resultSet containing the corresponding records
         */
        public static List<List<Object>> searchPlayer(Connection conn, String partialName, String partialSurname ) {
            try(var stmt = DAOUtils.prepare(conn, Queries.GET_PLAYERS, partialName, partialSurname)){
                List<List<Object>> data = new ArrayList<>();
                var resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    List<Object> row = new ArrayList<>( List.of(resultSet.getInt("idgiocatore"),
                            resultSet.getString("nome"),
                            resultSet.getString("cognome"),
                            resultSet.getInt("punteggio")
                            ));
                    data.add(row);
                }
                return data;
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }

        public static List<List<Object>> mostActive(Connection conn) {
            try(var stmt = DAOUtils.prepare(conn, Queries.MOST_ACTIVE_PLAYERS)){
                List<List<Object>> data = new ArrayList<>();
                var resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    List<Object> row = new ArrayList<>( List.of(resultSet.getInt("idgiocatore"),
                            resultSet.getString("nome"),
                            resultSet.getString("cognome"),
                            resultSet.getInt("punteggio"),
                            resultSet.getInt("numero_partite")
                            ));
                    data.add(row);
                }
                return data;
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }

        public static List<List<Object>> highestRated(Connection conn) {
            try(var stmt = DAOUtils.prepare(conn, Queries.HIGHEST_RATED)){
                List<List<Object>> data = new ArrayList<>();
                var resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    List<Object> row = new ArrayList<>( List.of(resultSet.getInt("idgiocatore"),
                            resultSet.getString("nome"),
                            resultSet.getString("cognome"),
                            resultSet.getInt("punteggio"),
                            resultSet.getInt("numero_partite")
                            ));
                    data.add(row);
                }
                return data;
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }

        public static List<List<Object>> bestClimber(Connection conn) {
            try(var stmt = DAOUtils.prepare(conn, Queries.BEST_CLIMBERS, java.sql.Date.valueOf(LocalDate.now()))){
                List<List<Object>> data = new ArrayList<>();
                var resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    List<Object> row = new ArrayList<>( List.of(resultSet.getInt("idgiocatore"),
                            resultSet.getString("nome"),
                            resultSet.getString("cognome"),
                            resultSet.getInt("punteggio"),
                            resultSet.getInt("numero_vittorie")
                            ));
                    data.add(row);
                }
                return data;
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }

        /**
         * 
         * @param conn
         * @param username
         * @param password
         * @return an optional of a player if he/she exists,
         * otherwise an empty optional is returned
         */
        public static Optional<Player> exists(final Connection conn, final String username, final String password) {
            try (var stmt = DAOUtils.prepare(conn, Queries.PLAYER_EXISTS, username, password)) {
                var resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("idgiocatore"), elo = resultSet.getInt("punteggio");
                    String cf =    resultSet.getString("cf"),
                        name = resultSet.getString("nome"),
                        lastname = resultSet.getString("cognome");
                    return Optional.of(new Player(id, elo, username, password, name, lastname, cf));
                }
                return Optional.empty();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }

        /**
         * adds a new player to the database, through registration.
         * @param conn
         * @param name
         * @param lastname
         * @param cf
         * @param username
         * @param password
         */
        public static void newInstance(final Connection conn, final String name, final String lastname,
                final String cf, final String username, final String password) {
            try (var stmt = DAOUtils.prepare(conn, Queries.PLAYER_REGISTER, username, password, cf, name, lastname)) {
                stmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e);
                throw new DAOException(e);
            }
        }

        /**
         * gets all the tournament announces from the database.
         * @param conn the connection to the db
         * @return List where every row represents an announce, and every column
         * contains an information about it -> name, location, expire date, capacity, id
         */
        public static List<List<Object>> getAnnounces(final Connection conn) {
            var ret = new ArrayList<List<Object>>();
            try (var stmt = DAOUtils.prepare(conn, Queries.GET_ANNOUNCES, Date.valueOf(LocalDate.now()))) {
                var resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    var id = Integer.toString(resultSet.getInt("idannuncio"));
                    var location = resultSet.getString("indirizzo");
                    var name = resultSet.getString("nome");
                    var date = ((Date) resultSet.getObject("scadenza")).toString();
                    var capacity = "";
                    try {
                        int subs = Announce.DAO.subsPerAnnounce(conn, resultSet.getInt("idannuncio"));
                        capacity += Integer.toString(subs) + "/";
                    } catch (Exception e) {
                        e.printStackTrace();
                        capacity += "0/";
                    }
                    int max = resultSet.getInt("maxiscrizioni");
                    capacity += Integer.toString(max);
                    ret.add(List.of(name, location, date, capacity, id));
                }
                return ret;
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }

        /**
         * checks whether a player is subscribed to a certain tournament
         * or not.
         * @param conn the connection to the db
         * @param playerId the id of the player
         * @param idAnnounce the id of the announce to check
         * @return true if the player is subscribed, false otherwise
         */
        public static boolean isSubscribed(final Connection conn, final int playerId, final int idAnnounce) {
            try (var stmt = DAOUtils.prepare(conn, Queries.IS_PLAYER_SUBSCRIBED, playerId, idAnnounce)) {
                var resultSet = stmt.executeQuery();
                if (resultSet.next()) {
                    return true;
                }
                return false;
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }

        /**
         * gives the stats of a specified player.
         * @param conn
         * @param playerId
         * @return a map in which infos are labeled by the key:
         * "name" -> name, "lastname" -> lastname, "elo" -> score,
         * "rival" -> rival player, "favOp" -> favorite opener,
         * "favDef" -> favorite defence
         */
        public static Map<String, String> getStats(final Connection conn, final int playerId) {
            var parser = new MoveParser();
            var ret = new HashMap<String, String>();
            try (var name = DAOUtils.prepare(conn, Queries.GET_NAME, playerId);
                    var lastname = DAOUtils.prepare(conn, Queries.GET_LASTNAME, playerId);
                    var favOpener = DAOUtils.prepare(conn, Queries.OPENER_WHITE, playerId);
                    var favDef = DAOUtils.prepare(conn, Queries.OPENER_BLACK, playerId);
                    var elo = DAOUtils.prepare(conn, Queries.GET_ELO, playerId);
                    var rival = DAOUtils.prepare(conn, Queries.GET_RIVAL, playerId);) {
                var res = name.executeQuery();
                if (res.next()) {
                    ret.put("name", res.getString("nome"));
                }
                res = lastname.executeQuery();
                if (res.next()) {
                    ret.put("lastname", res.getString("cognome"));
                }
                res = favOpener.executeQuery();
                if (res.next()) {
                    parser.parse(res.getString("stringamossa"));
                    ret.put("favOp", parser.getAttacker() + " " + parser.getArrivalCoord());
                } else {
                    ret.put("favOp", "---");
                }
                res = favDef.executeQuery();
                if (res.next()) {
                    parser.parse(res.getString("stringamossa"));
                    ret.put("favDef", parser.getAttacker() + " " + parser.getArrivalCoord());
                } else {
                    ret.put("favDef", "---");
                }
                res = elo.executeQuery();
                if (res.next()) {
                    ret.put("elo", Integer.toString(res.getInt("punteggio")));
                }
                res = rival.executeQuery();
                if (res.next()) {
                    ret.put("rival", res.getString("nome") + " " + res.getString("cognome") +
                            " (" + res.getInt("numPartite") + " games)");
                } else {
                    ret.put("rival", "---");
                }
                if (ret.entrySet().size() == 6) {
                    return ret;
                } else {
                    throw new SQLException();
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }

        /**
         * gets the trend for the last 10 games
         * @param conn the connection to the db
         * @param playerId the id of the player
         * @return elo trend for last 10 games
         */
        public static List<Pair<Integer, Date>> getTrend(final Connection conn, final int playerId, final int elo) {
            try (var stmt = DAOUtils.prepare(conn, Queries.GET_ELO_TREND, playerId, java.sql.Date.valueOf(LocalDate.now()))) {
                var resultSet = stmt.executeQuery();
                List<Pair<Integer, Date>> list = new ArrayList<>();
                list.add(new Pair<>(elo, java.sql.Date.valueOf(LocalDate.now())));
                int tmpElo = elo;
                while(resultSet.next()) {
                	tmpElo = tmpElo + resultSet.getInt("risultato") * 25;
                	list.add(new Pair<Integer, Date>(tmpElo, resultSet.getDate("data")));
                }
                return list;
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }

        /**
         * gets player's heat map information.
         * @param conn the connection to the db
         * @param playerId the id of the player
         * @return list of heat map values
         */
        public static List<Double> getHeatMap(final Connection conn, final int playerId) {
            try (var total = DAOUtils.prepare(conn, Queries.GET_TOT_MOVES, playerId);
            		var square = DAOUtils.prepare(conn, Queries.GET_SQUARES_NUM_MOVES, playerId)) {
            	List<Double> list = new ArrayList<>();
                var num_moves = total.executeQuery();
                var square_moves = square.executeQuery();
                int tot = num_moves.next() ? num_moves.getInt("numero_mosse") : 0;
                while(square_moves.next()) {
                	list.add(tot != 0 ? square_moves.getInt("numero_mosse")/tot : 0.0);
                }
                return list;
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }
}
