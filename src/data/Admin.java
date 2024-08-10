package data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Optional;

public class Admin {
	
	private static int idAdmin;
	private static String username;
	private static String password;
	private static String name;
	private static String lastname;
	private static String cf;
	
	public Admin(int idAdmin, String username, String password, String name, String lastname, String cf) {
		Admin.idAdmin = idAdmin;
		Admin.username = username;
		Admin.password = password;
		Admin.name = name;
		Admin.lastname = lastname;
		Admin.cf = cf;
	}

	public int getIdAdmin() {
		return idAdmin;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public String getLastname() {
		return lastname;
	}

	public String getCf() {
		return cf;
	}

	@Override
	public boolean equals(Object obj) {
		var other = (Admin)obj;
		return Admin.idAdmin == other.getIdAdmin();
	}
	
	public final static class DAO {
		
		public static boolean postAnnounce(Connection conn, String address, Date expiringDate, int maxSubs, int minSubs, int idAdmin) {
			try (var stmt = DAOUtils.prepare(conn, Queries.POST_ANNOUNCE, address, expiringDate, maxSubs, minSubs, idAdmin)) {
				stmt.executeQuery();
				return true;
			} catch (SQLException e) {
				return false;
			}
		}
		
		public static boolean addLocation(Connection conn, String address, String description) {
			try (var stmt = DAOUtils.prepare(conn, Queries.ADD_LOCATION, address, description)) {
				stmt.executeUpdate();
				return true;
			} catch (SQLException e) {
				return false;
				
			}
		}
		
		public static Optional<Admin> exists(Connection conn, String username, String password) {
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
