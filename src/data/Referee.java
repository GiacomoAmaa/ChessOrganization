package data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class Referee {
	private static int cardNumber;
	private static String username; 
	private static String password;
	private static String cf;
	private static String name;
	private static String lastname;
	
	public Referee(int cardNumber, String username, String password, String cf, String name, String lastname) {
		Referee.cardNumber = cardNumber;
		Referee.username = username;
		Referee.password = password;
		Referee.cf = cf;
		Referee.name = name;
		Referee.lastname = lastname;
	}
	
	public static int getCardNumber() {
		return cardNumber;
	}
	public static String getUsername() {
		return username;
	}
	public static String getPassword() {
		return password;
	}
	public static String getCf() {
		return cf;
	}
	public static String getName() {
		return name;
	}
	public static String getLastname() {
		return lastname;
	}
	
public final static class DAO {
	
		public static List<Player> searchPlayer(Connection conn, String input) {
			// TODO implementation
			return null;
		}
		
		public static List<Game> searchGames(Connection conn, String input) {
			// TODO implementation
			return null;
		}
		
		public static int getId(Connection conn, String username, String password) {
			try (var stmt = DAOUtils.prepare(conn,  Queries.REF_GET_ID, username, password)) {
				var resultSet = stmt.executeQuery();
				if (resultSet.next()) {
					return resultSet.getInt("numtessera");
				}
				return -1;
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}
		
		public static Optional<Referee> exists(Connection conn, String username, String password, int cardNumber) {
			try (var stmt = DAOUtils.prepare(conn, Queries.REFEREE_EXISTS, username, password, cardNumber)) {
				var resultSet = stmt.executeQuery();
				while(resultSet.next()) {
					String name = resultSet.getString("nome"),
						lastname = resultSet.getString("cognome"),
						cf = resultSet.getString("cf");
					return Optional.of(new Referee(cardNumber, username, password, name, lastname, cf));
				}
				return Optional.empty();
				
			} catch (SQLException e) {
				throw new DAOException(e);
			}
		}
	}
}
