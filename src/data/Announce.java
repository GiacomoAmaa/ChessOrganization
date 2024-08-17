package data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

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

	public static int getIdAnnounce() {
		return idAnnounce;
	}

	public static String getAddress() {
		return address;
	}

	public static Date getExpiringDate() {
		return expiringDate;
	}

	public static int getMaxSubs() {
		return maxSubs;
	}

	public static int getMinSubs() {
		return minSubs;
	}

	public static int getIdAdmin() {
		return idAdmin;
	}
	
	public static final class DAO {
		
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
