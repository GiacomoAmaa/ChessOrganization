package data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

import model.DBModel;

public class Announce {
	
	private static int idAnnounce;
	private static String address;
	private static Date expiringDate;
	private static int maxSubs;
	private static int minSubs;
	private static int idAdmin;
	
	public Announce(int idAnnounce, String address, Date expiringDate,
			int maxSubs, int minSubs, int idAdmin) {
		Announce.idAnnounce = idAnnounce;
		Announce.address = address;
		Announce.expiringDate = expiringDate;
		Announce.maxSubs = maxSubs;
		Announce.minSubs = minSubs;
		Announce.idAdmin = idAdmin;
	}
	
	public Announce(int idAnnounce) {
		var map = Announce.DAO.getData(DBModel.getConnection(), idAnnounce);
		Announce.idAnnounce = idAnnounce;
		Announce.address = map.get("address").toString();
		Announce.expiringDate = (Date)map.get("date");
		Announce.maxSubs = (Integer)map.get("maxSubs");
		Announce.minSubs = (Integer)map.get("minSubs");
		Announce.idAdmin = (Integer)map.get("idadmin");
	}

	public static int getIdAnnounce() {
		return idAnnounce;
	}

	public String getAddress() {
		return address;
	}

	public Date getExpiringDate() {
		return expiringDate;
	}

	public int getMaxSubs() {
		return maxSubs;
	}

	public int getMinSubs() {
		return minSubs;
	}

	public int getIdAdmin() {
		return idAdmin;
	}
	
	public static final class DAO {
		
		public static Map<String, Object> getData(Connection conn, int idAnnounce) {
			try(var stmt = DAOUtils.prepare(conn, Queries.GET_ANNOUNCE_DATA, idAnnounce)) {
				var resultSet = stmt.executeQuery();
				if(resultSet.next())
					return Map.of("address", resultSet.getString("indirizzo"),
							"date", resultSet.getDate("scadenza"),
							"minSubs", resultSet.getInt("miniscrizioni"),
							"maxSubs", resultSet.getInt("maxiscrizioni"),
							"idadmin", resultSet.getInt("idadmin"));
				else
					throw new SQLException();
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}
		
		public static int subsPerAnnounce(Connection conn, int idAnnounce) {
			try(var stmt = DAOUtils.prepare(conn, Queries.SUBS_PER_ANNOUNCE, idAnnounce)) {
				var resultSet = stmt.executeQuery();
				// it's a count-type query, so there's only a record
				resultSet.next();
				return resultSet.getInt("total");
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}
		
		public static boolean subscription(Connection conn, int idAnnounce, int playerId) {
			try(var stmt = DAOUtils.prepare(conn, Queries.SUBSCRIPTION, idAnnounce, playerId, Date.valueOf(LocalDate.now()))) {
				stmt.executeUpdate();
				return true;
			} catch(SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		
		public static final void unsubscribe(Connection conn, int idAnnounce, int playerId) {
			try (var stmt = DAOUtils.prepare(conn, Queries.UNSUBSCRIBE, idAnnounce, playerId)) {
				stmt.executeUpdate();
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}
	}
	
}
