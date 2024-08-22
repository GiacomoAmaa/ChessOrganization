package data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A representation of a data instance of a player in the database
 */
public final class Player {
	
	private static int id;
	private static int elo;
	private static String username; 
	private static String password;
	private static String cf;
	private static String name;
	private static String lastname;
	
	public Player(int id, int elo, String username, String password, String cf, String name, String lastname) {
		Player.id = id;
		Player.elo = elo;
		Player.username = username;
		Player.password = password;
		Player.cf = cf;
		Player.name = name;
		Player.lastname = lastname;
	}
		
	public int getId() {
		return Player.id;
	}

	public int getElo() {
		return Player.elo;
	}

	public String getPassword() {
		return Player.password;
	}

	public String getCf() {
		return Player.cf;
	}

	public String getName() {
		return Player.name;
	}

	public String getLastname() {
		return Player.lastname;
	}
	
	public String getUsername() {
		return Player.username;
	}

	@Override
	public boolean equals(Object other) {
		var p = (Player) other;
		return Player.id == p.getId();
	}
	
	public final static class DAO {
		
		public static Map<String, Number> stats(Connection conn, int playerId) {
			// TODO implementation
			return null;
		}
		
		public static List<Game> gamesForPlayer(Connection conn, int playerId) {
			// TODO implementation
			return null;
		}
		
		public static ResultSet searchPlayer(Connection conn, String partialName, String partialSurname ) {
			try(var stmt = DAOUtils.prepare(conn, Queries.GET_PLAYERS, partialName, partialSurname)){
				var resultSet = stmt.executeQuery();
				resultSet.next();
				return resultSet;

			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}

		public static List<Game> mostActive(Connection conn, Date today) {
			// TODO implementation
			return null;
		}

		public static List<Game> highestRated(Connection conn) {
			// TODO implementation
			return null;
		}

		public static List<Game> bestClimber(Connection conn, Date today) {
			// TODO implementation
			return null;
		}
		
		public static Optional<Player> exists(Connection conn, String username, String password) {
			try (var stmt = DAOUtils.prepare(conn, Queries.PLAYER_EXISTS, username, password)) {
				var resultSet = stmt.executeQuery();
				while(resultSet.next()) {
					int id = resultSet.getInt("idgiocatore"), elo = resultSet.getInt("punteggio");
					String cf =	resultSet.getString("cf"),
						name = resultSet.getString("nome"),
						lastname = resultSet.getString("cognome");
					return Optional.of(new Player(id, elo, username, password, name, lastname, cf));
				}
				return Optional.empty();
				
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}
		
		public static void newInstance(Connection conn, String name, String lastname, String cf, String username, String password) {
			try (var stmt = DAOUtils.prepare(conn, Queries.PLAYER_REGISTER, username, password, cf, name, lastname)) {
				stmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e);
				throw new DAOException(e);
			}
		}
		
		public static List<List<Object>> getAnnounces(Connection conn) {
			var ret = new ArrayList<List<Object>>();
			try (var stmt = DAOUtils.prepare(conn, Queries.GET_ANNOUNCES, Date.valueOf(LocalDate.now()))) {
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
					ret.add(List.of(name, location, date, capacity, id));
				}
				return ret;
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}
		
		public static boolean isSubscribed(Connection conn, int playerId, int idAnnounce) {
			try (var stmt = DAOUtils.prepare(conn, Queries.IS_PLAYER_SUBSCRIBED, playerId, idAnnounce)) {
				var resultSet = stmt.executeQuery();
				if(resultSet.next()) {
					return true;
				}
				return false;
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}
	}
}
