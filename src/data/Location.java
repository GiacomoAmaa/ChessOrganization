package data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.DBModel;

public class Location {
	
	private static String address;
	private static String description;
	
	public Location(String address, String description) {
		Location.address = address;
		Location.description = description;
	}

	public String getAddress() {
		return address;
	}

	public String getDescription() {
		return description;
	}
	
	public final static class DAO {

		public static boolean exists(Connection conn, String address) {
			try (var stmt = DAOUtils.prepare(conn, Queries.LOCATION_EXISTS, address)) {
				var resultSet = stmt.executeQuery();
				if(resultSet.next()) {
					return true;
				}
				return false;
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}
		
		public static List<String> getAllAdresses(Connection conn) {
			try (var stmt = DAOUtils.prepare(conn, Queries.GET_ADDRESSES)) {
				var resultSet = stmt.executeQuery();
				List<String> addresses = new ArrayList<>();
				while(resultSet.next()) {
					addresses.add(resultSet.getString("indirizzo"));
				}
				return addresses;
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}
		
	}
	
}
