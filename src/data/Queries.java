package data;

/**
 * this class contains all the queries to launch in the DB as public Strings
 */
public class Queries {
	
	public static final String PLAYER_EXISTS =
			"select * "
			+ "from giocatori "
			+ "where username = ? "
			+ "and password = ? ";
	
	public static final String ADMIN_EXISTS =
			"select * "
			+ "from organizzatori "
			+ "where username = ? "
			+ "and password = ? ";
	public static final String PLAYER_REGISTER = 
			"insert into giocatori(idgiocatore, punteggio, username, password, cf, nome, cognome)"
			+ "(null, 0, ?, ?, ?, ?, ?)";

}
