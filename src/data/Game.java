package data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import util.Pair;

public class Game {

	public final static class DAO {

		public static List<List<Object>> getGameWithPlayers(Connection conn, String white, String black,
				Date since, Date to) {
			try (var stmt = DAOUtils.prepare(conn, Queries.GET_GAME_WITH_PLAYERS, white, white,
					black, black, new java.sql.Date(since.getTime()), new java.sql.Date(to.getTime()))) {
				List<List<Object>> data = new ArrayList<>();
				var resultSet = stmt.executeQuery();
				while (resultSet.next()) {
					List<Object> row = new ArrayList<>();
					row.add(List.of(resultSet.getInt("codpartita"),
							resultSet.getString("nome_bianco") + " " + resultSet.getString("cognome_bianco"),
							resultSet.getString("nome_nero") + " " + resultSet.getString("cognome_nero"),
							resultSet.getString("vincitore"),
							resultSet.getString("nome_torneo")
							));
				}
				return data;
			} catch (SQLException e) {
				System.out.println(e);
				throw new DAOException(e);
			}
		}

		public static List<Pair<String,String>> getGameMoves(Connection conn, int codPartita) {
			try (var stmt = DAOUtils.prepare(conn, Queries.GET_GAME_MOVES, codPartita )) {
				List<Pair<String,String>> list = new ArrayList<>();
				var resultSet = stmt.executeQuery();
				while (resultSet.next()) {
					list.add(new Pair<String,String>(resultSet.getString("mossa_bianca"),
							resultSet.getString("mossa_nera")));
				}
				return list;
			} catch (SQLException e) {
				System.out.println(e);
				throw new DAOException(e);
			}
		}

		public static Date getOldestGame(Connection conn) {
			try (var stmt = DAOUtils.prepare(conn, Queries.OLDEST_DATE )) {
				var resultSet = stmt.executeQuery();
				resultSet.next();
				return resultSet.getDate("date");
			} catch (SQLException e) {
				System.out.println(e);
				throw new DAOException(e);
			}
		}

		public static int getNumberofGames(Connection conn, int playerid) {
			try (var stmt = DAOUtils.prepare(conn, Queries.GAMES_PLAYED, playerid )) {
				var resultSet = stmt.executeQuery();
				resultSet.next();
				return resultSet.getInt(0);
			} catch (SQLException e) {
				System.out.println(e);
				throw new DAOException(e);
			}
		}

	}

}
