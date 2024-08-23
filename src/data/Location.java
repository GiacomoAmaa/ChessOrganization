package data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * a class representing a data instance of a location in the database.
 */
public class Location {

    private String address;
    private String description;

    /**
     * the constructor.
     * @param address
     * @param description direction for the location
     */
    public Location(final String address, final String description) {
        this.address = address;
        this.description = description;
    }

    /**
     * getter for the address.
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * getter for the description.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Utility class for the interations with the database.
     */
    public static final class DAO {

        /**
         * checks if the address already exists in the location table of th db.
         * @param conn the connection to the db
         * @param address the address of the location
         * @return true if the location exists, false otherwise
         */
        public static boolean exists(final Connection conn, final String address) {
            try (var stmt = DAOUtils.prepare(conn, Queries.LOCATION_EXISTS, address)) {
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
         * gets, through the db, all the address contained int the table.
         * @param conn the connection to the db
         * @return a list of addresses as strings
         */
        public static List<String> getAllAdresses(final Connection conn) {
            try (var stmt = DAOUtils.prepare(conn, Queries.GET_ADDRESSES)) {
                var resultSet = stmt.executeQuery();
                List<String> addresses = new ArrayList<>();
                while (resultSet.next()) {
                    addresses.add(resultSet.getString("indirizzo"));
                }
                return addresses;
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }
}
