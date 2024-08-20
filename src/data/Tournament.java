package data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Tournament {
	
	private static int tournId;
	private static String address;
	private static String date;
	private static int numParticipants;
	
	public Tournament(int tournId, String address, String date, int numParticipants) {
		Tournament.tournId = tournId;
		Tournament.address = address;
		Tournament.date = date;
		Tournament.numParticipants = numParticipants;
	}
	
	public int getTournId() {
		return tournId;
	}
	public String getAddress() {
		return address;
	}
	public String getDate() {
		return date;
	}
	public int getNumParticipants() {
		return numParticipants;
	}
	
	public final static class DAO {
		
		public static boolean ufficialize(Connection conn, int announceId) {
			List<Integer> subIds = new ArrayList<>(),
					gameIds = new ArrayList<>();
			var rnd = new Random();
			try(var stmt = DAOUtils.prepare(conn, Queries.CREATE_TOURNAMENT, announceId);
					var stmt2 = DAOUtils.prepare(conn, Queries.GET_ALL_SUBS, announceId)) {
				stmt.executeUpdate();
				var tmp = stmt.getGeneratedKeys();
				tmp.next();
				int id = tmp.getInt("codtorneo");
				int gameId;
				var subs = stmt2.executeQuery();
				while(subs.next()) {
					subIds.add(subs.getInt("idiscrizione"));
				}
				for(int i = 0; i < (subIds.size() * (subIds.size()-1)); i++) {
					try(var stmt3 = DAOUtils.prepare(conn, Queries.CREATE_GAME, id, Date.valueOf(LocalDate.now()))) {
						stmt3.executeUpdate();
						tmp = stmt3.getGeneratedKeys();
						tmp.next();
						gameId = tmp.getInt("codpartita");
						gameIds.add(gameId);
					}
				}
				/*
				 * for each subId, get every different subId, create a game where it's black and one where it's white
				 * against the other subId, remove both the gameIds you used (picked randomly), remove the first subId,
				 * repeat.
				 */
				var done = new HashSet<Integer>();
				for(int sub : subIds) {
					for (int other : subIds) {
						if(sub != other && !done.contains(other)) {
							int index = rnd.nextInt(gameIds.size());
							var whiteGame = gameIds.get(index);
							// can't get the same game twice
							gameIds.remove(index);
							try(var participant1 = DAOUtils.prepare(conn, Queries.ADD_PARTICIPANT, whiteGame,
									sub, "Bianco");
									var participant2 = DAOUtils.prepare(conn, Queries.ADD_PARTICIPANT, whiteGame,
											other, "Nero")){
								participant1.executeUpdate();
								participant2.executeUpdate();
							}
							// get another game
							index = rnd.nextInt(gameIds.size());
							var blackGame = gameIds.get(index);
							// can't get the same game twice neither
							gameIds.remove(index);
							// do exactly the opposite
							try(var participant1 = DAOUtils.prepare(conn, Queries.ADD_PARTICIPANT, whiteGame,
									sub, "Nero");
									var participant2 = DAOUtils.prepare(conn, Queries.ADD_PARTICIPANT, whiteGame,
											other, "Bianco")){
								participant1.executeUpdate();
								participant2.executeUpdate();
							}
						}
					}
					// in this way subs will never be selected again in other
					done.add(sub);
				}
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
	}
}
