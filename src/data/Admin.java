package data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
		
		public static boolean postAnnounce(Connection conn, String name, String address, Date expiringDate, int maxSubs, int minSubs, int idAdmin) {
			try (var stmt = DAOUtils.prepare(conn, Queries.POST_ANNOUNCE, name, address, expiringDate, maxSubs, minSubs, idAdmin)) {
				stmt.executeUpdate();
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
		
		public static int addReferee(Connection conn, String name, String lastname, String cf,
				String username, String password, String address) {
			try (var stmt = DAOUtils.prepare(conn, Queries.ADD_REFEREE, name, lastname, cf, username, password)) {
				stmt.executeUpdate();
				int id = Referee.DAO.getId(conn, username, password);
				var stmt1 = DAOUtils.prepare(conn, Queries.ASSIGN_REF, address, id);
				stmt1.executeUpdate();
				stmt1.close();
				return id;
			} catch (SQLException e) {
				return -1;
			} finally {
			}
		}
		
		public static List<List<Object>> getAnnounces(Connection conn) {
			var ret = new ArrayList<List<Object>>();
			try (var stmt = DAOUtils.prepare(conn, Queries.GET_EXP_ANNOUNCES, java.sql.Date.valueOf(LocalDate.now()))) {
				var resultSet = stmt.executeQuery();
				while(resultSet.next()) {
					var id = Integer.toString(resultSet.getInt("idannuncio"));
					var location = resultSet.getString("indirizzo");
					var name = resultSet.getString("nome");
					var date = ((Date)resultSet.getObject("scadenza")).toString();
					var capacity = "";
					try {
						int subs = Announce.DAO.subsPerAnnounce(conn, resultSet.getInt("idannuncio"));
						capacity += Integer.toString(subs)+"/";
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
		
		public static void deleteAnnounce(Connection conn, int idAnnounce) {
			try (var stmt = DAOUtils.prepare(conn, Queries.DELETE_ANNOUNCE, idAnnounce);
					var stmt1 = DAOUtils.prepare(conn, Queries.DELETE_SUBS, idAnnounce);) {
				stmt1.executeUpdate();
				stmt.executeUpdate();
			} catch (SQLException e) {
				throw new DAOException(e);
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
