package data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

import model.DBModel;

/**
 * a class representing a data instance of an announce in the database.
 */
public class Announce {

    private int idAnnounce;
    private String address;
    private Date expiringDate;
    private int maxSubs;
    private int minSubs;
    private int idAdmin;

    /**
     * the constructor.
     * @param idAnnounce
     * @param address
     * @param expiringDate
     * @param maxSubs
     * @param minSubs
     * @param idAdmin
     */
    public Announce(final int idAnnounce, final String address, final Date expiringDate,
            final int maxSubs, final int minSubs, final int idAdmin) {
        this.idAnnounce = idAnnounce;
        this.address = address;
        this.expiringDate = expiringDate;
        this.maxSubs = maxSubs;
        this.minSubs = minSubs;
        this.idAdmin = idAdmin;
    }

    /**
     * constructor option to search an announce directly from the database.
     * @param idAnnounce
     */
    public Announce(final int idAnnounce) {
        var map = Announce.DAO.getData(DBModel.getConnection(), idAnnounce);
        this.idAnnounce = idAnnounce;
        this.address = map.get("address").toString();
        this.expiringDate = (Date) map.get("date");
        this.maxSubs = (Integer) map.get("maxSubs");
        this.minSubs = (Integer) map.get("minSubs");
        this.idAdmin = (Integer) map.get("idadmin");
    }

    /**
     * getter of the id.
     * @return the id
     */
    public int getIdAnnounce() {
        return idAnnounce;
    }

    /**
     * getter for the location.
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * getter for the expiration date.
     * @return the Date in sql format
     */
    public Date getExpiringDate() {
        return expiringDate;
    }

    /**
     * getter for the maximum amount of subscribers.
     * @return the amount
     */
    public int getMaxSubs() {
        return maxSubs;
    }

    /**
     * getter for the minimum amount of subscribers.
     * @return the amount
     */
    public int getMinSubs() {
        return minSubs;
    }

    /**
     * getter for the author of the announce.
     * @return the id
     */
    public int getIdAdmin() {
        return idAdmin;
    }

    /**
     * Utility class for the interations with the database.
     */
    public static final class DAO {

        /**
         * gets all the fields of an announce from the id.
         * @param conn the connection to the db
         * @param idAnnounce the key to search
         * @return a map in which each key is the name of the column in the db,
         * and the corresponding value is the actual value for this instance
         */
        public static Map<String, Object> getData(final Connection conn, final int idAnnounce) {
            try (var stmt = DAOUtils.prepare(conn, Queries.GET_ANNOUNCE_DATA, idAnnounce)) {
                var resultSet = stmt.executeQuery();
                if (resultSet.next()) {
                    return Map.of("address", resultSet.getString("indirizzo"),
                            "date", resultSet.getDate("scadenza"),
                            "minSubs", resultSet.getInt("miniscrizioni"),
                            "maxSubs", resultSet.getInt("maxiscrizioni"),
                            "idadmin", resultSet.getInt("idadmin"));
                } else {
                    throw new SQLException();
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }

        /**
         * gets, from the db, the number of players subscribed to a certain announce.
         * @param conn the connection to the db
         * @param idAnnounce id of the announce
         * @return the number of subscribers
         */
        public static int subsPerAnnounce(final Connection conn, final int idAnnounce) {
            try (var stmt = DAOUtils.prepare(conn, Queries.SUBS_PER_ANNOUNCE, idAnnounce)) {
                var resultSet = stmt.executeQuery();
                // it's a count-type query, so there's only a record
                resultSet.next();
                return resultSet.getInt("total");
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }

        /**
         * subscribes a certain player to a certain announce.
         * @param conn the connection to the db
         * @param idAnnounce the id of the announce
         * @param playerId the id of the player
         * @return true if the player succefully subscribes,
         * false otherwise
         */
        public static boolean subscription(final Connection conn, final int idAnnounce, final int playerId) {
            try (var stmt = DAOUtils.prepare(conn, Queries.SUBSCRIPTION, idAnnounce, playerId, Date.valueOf(LocalDate.now()))) {
                stmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * deletes the instance of a subscription from a certain player, to a certain announce.
         * @param conn the connection to the db
         * @param idAnnounce the id of the announce
         * @param playerId the id of the player to unsubscribe
         */
        public static void unsubscribe(final Connection conn, final int idAnnounce, final int playerId) {
            try (var stmt = DAOUtils.prepare(conn, Queries.UNSUBSCRIBE, idAnnounce, playerId)) {
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }
}
