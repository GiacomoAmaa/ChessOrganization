package data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * a class representing a data instance of an admin in the database.
 */
public class Admin {

    private int idAdmin;
    private String username;
    private String password;
    private String name;
    private String lastname;
    private String cf;

    /**
     * class constructor.
     * @param idAdmin the primary key
     * @param username
     * @param password
     * @param name
     * @param lastname
     * @param cf
     */
    public Admin(final int idAdmin, final String username, final String password,
            final String name, final String lastname, final String cf) {
        this.idAdmin = idAdmin;
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.cf = cf;
    }

    /**
     * getter for the id.
     * @return the id
     */
    public int getIdAdmin() {
        return idAdmin;
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
     * getter for the cf.
     * @return the cf
     */
    public String getCf() {
        return cf;
    }

    /**
     * Utility class for the interations with the database.
     */
    public static final class DAO {

        /**
         * publishes a new announce int the database.
         * @param conn the connection to the db
         * @param name name for the announce
         * @param address location for the tournament
         * @param expiringDate exipiration date
         * @param maxSubs maximum number of subscribers
         * @param minSubs minimum number of subscribers
         * @param idAdmin id of the admin whose posting this
         * @return true if the announce is succesfully published,
         * false otherwise
         */
        public static boolean postAnnounce(final Connection conn, final String name, final String address,
                final Date expiringDate, final int maxSubs, final int minSubs, final int idAdmin) {
            try (var stmt = DAOUtils.prepare(conn, Queries.POST_ANNOUNCE, name, address, expiringDate, maxSubs, minSubs, idAdmin)) {
                stmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                return false;
            }
        }

        /**
         * adds a new location to the database.
         * @param conn the connection to the db
         * @param address the address of the new location
         * @param description directions to the locations
         * @return true if succesfully added, false otherwise
         */
        public static boolean addLocation(final Connection conn, final String address, final String description) {
            try (var stmt = DAOUtils.prepare(conn, Queries.ADD_LOCATION, address, description)) {
                stmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                return false;
            }
        }

        /**
         * adds a new referee to the database.
         * @param conn the connection to the db
         * @param name name of the new referee
         * @param lastname lastname of the new referee
         * @param cf his/hers cf
         * @param username his/hers username
         * @param password the password
         * @param address the location to which the referee is assigned to
         * @return the referee id if the referee is succesfully added,
         * -1 otherwise
         */
        public static int addReferee(final Connection conn, final String name, final String lastname, final String cf,
                final String username, final String password, final String address) {
            try (var stmt = DAOUtils.prepare(conn, Queries.ADD_REFEREE, name, lastname, cf, username, password)) {
                stmt.executeUpdate();
                int id = Referee.DAO.getId(conn, username, password);
                var stmt1 = DAOUtils.prepare(conn, Queries.ASSIGN_REF, address, id);
                stmt1.executeUpdate();
                stmt1.close();
                return id;
            } catch (SQLException e) {
                return -1;
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
            try (var stmt = DAOUtils.prepare(conn, Queries.GET_EXP_ANNOUNCES, java.sql.Date.valueOf(LocalDate.now()))) {
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
                    ret.add(new ArrayList<Object>(List.of(name, location, date, capacity, id)));
                }
                return ret;
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }

        /**
         * deletes an announce from the database.
         * @param conn the connection to the db
         * @param idAnnounce the id of the announce to delete
         */
        public static void deleteAnnounce(final Connection conn, final int idAnnounce) {
            try (var stmt = DAOUtils.prepare(conn, Queries.DELETE_ANNOUNCE, idAnnounce);
                    var stmt1 = DAOUtils.prepare(conn, Queries.DELETE_SUBS, idAnnounce);) {
                stmt1.executeUpdate();
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        /**
         * checks if the combination of username and password exists in the admin table.
         * @param conn the connection to the db
         * @param username of the admin
         * @param password of the admin
         * @return an optional of an admin if he/she exists,
         * otherwise an empty optional is returned
         */
        public static Optional<Admin> exists(final Connection conn, final String username, final String password) {
            try (var stmt = DAOUtils.prepare(conn, Queries.ADMIN_EXISTS, username, password)) {
                var resultSet = stmt.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("idadmin");
                    String cf = resultSet.getString("cf"),
                        nome = resultSet.getString("nome"),
                        cognome  = resultSet.getString("cognome");
                    return Optional.of(new Admin(id, username, password, nome, cognome, cf));
                }
                return Optional.empty();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }
}
