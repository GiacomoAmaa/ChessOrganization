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
		
		public static boolean ufficialize(Connection conn, String address, String name, int numSubs, int idAnnounce) {
			List<Integer> subIds = new ArrayList<>(),
					gameIds;
			var rnd = new Random();
			try(var stmt = DAOUtils.prepare(conn, Queries.CREATE_TOURNAMENT, address, name, Date.valueOf(LocalDate.now()),
					numSubs, idAnnounce);
					var stmt2 = DAOUtils.prepare(conn, Queries.GET_ALL_SUBS, idAnnounce)) {
				stmt.executeUpdate();
				var tmp = stmt.getGeneratedKeys();
				tmp.next();
				int id = tmp.getInt(1);
				var subs = stmt2.executeQuery();
				while(subs.next()) {
					subIds.add(subs.getInt("idiscrizione"));
				}
				// games creation
				gameIds = createNGames(conn, id, (subIds.size() * (subIds.size()-1)));
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
							var whiteGameId = gameIds.get(index);
							// can't get the same game twice
							gameIds.remove(index);
							createGame(conn, whiteGameId, sub, other, true);
							// get another game
							index = rnd.nextInt(gameIds.size());
							var blackGameId = gameIds.get(index);
							// can't get the same game twice neither
							gameIds.remove(index);
							// do exactly the opposite
							createGame(conn, blackGameId, sub, other, false);
						}
					}
					// in this way subs will never be selected again in other
					done.add(sub);
				}
				// designating the referees for the tournament, minimum 1
				int n = (numSubs/4) < 1 ? 1 : (numSubs/4);
				designate(conn, n, new Announce(idAnnounce).getAddress(), id);
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		
		private static void designate(Connection conn, int n, String address, int tournId) {
			var refs = new ArrayList<Integer>();
			try(var stmt = DAOUtils.prepare(conn, Queries.CHOOSE_REFS, address, n)) {
				var resultSet = stmt.executeQuery();
				while(resultSet.next()) {
					refs.add(resultSet.getInt("numtessera"));
				}
				refs.stream().forEach(ref -> {
					try(var des = DAOUtils.prepare(conn, Queries.ADD_DESIGNATION, ref, tournId)) {
						des.executeUpdate();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				});
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException(e);
			}
		}

		/**
		 * creates a game where Id1 and Id2 are playing
		 * @param conn connection to the db in order to launch queries
		 * @param gameId id of the game to create participants for
		 * @param Id1 first player
		 * @param Id2 second player
		 * @param white if true first player is white, otherwise first player is black
		 */
		private static void createGame(Connection conn, int gameId, int Id1, int Id2, boolean white) {
			try {
				if (white) {
					try(var participant1 = DAOUtils.prepare(conn, Queries.ADD_PARTICIPANT, gameId,
							Id1, "Bianco");
							var participant2 = DAOUtils.prepare(conn, Queries.ADD_PARTICIPANT, gameId,
									Id2, "Nero")){
						participant1.executeUpdate();
						participant2.executeUpdate();
					}
				} else {
					try(var participant1 = DAOUtils.prepare(conn, Queries.ADD_PARTICIPANT, gameId,
							Id1, "Nero");
							var participant2 = DAOUtils.prepare(conn, Queries.ADD_PARTICIPANT, gameId,
									Id2, "Bianco")){
						participant1.executeUpdate();
						participant2.executeUpdate();
					}
				}
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}
		
		/**
		 * creates n games for a  new tournament
		 * @param conn conn connection to the db in order to launch queries
		 * @param id Identificator of the tournament
		 * @param n number of games to be created
		 * @return a list containing all the ids of the new games
		 */
		private static List<Integer> createNGames(Connection conn, int id, int n) {
			var gameIds = new ArrayList<Integer>();
			int gameId;
			for(int i = 0; i < n; i++) {
				try(var stmt3 = DAOUtils.prepare(conn, Queries.CREATE_GAME, id, Date.valueOf(LocalDate.now()))) {
					stmt3.executeUpdate();
					var tmp = stmt3.getGeneratedKeys();
					tmp.next();
					gameId = tmp.getInt(1);
					gameIds.add(gameId);
				} catch (SQLException e) {
					throw new DAOException(e);
				}
			}
			return gameIds;
		}
	}
}
