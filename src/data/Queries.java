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
	public static final String REFEREE_EXISTS = 
			"select *"
			+ "from arbitri"
			+ "where username = ?"
			+ "and password = ?"
			+ "and numtessera = ?";
	public static final String PLAYER_REGISTER = 
			"insert into giocatori(idgiocatore, punteggio, username, password, cf, nome, cognome)"
			+ "values (null, 0, ?, ?, ?, ?, ?)";
	public static final String GAMES_FOR_PLAYER = 
			"select *"
			+ "from partite p, partecipanti pa, iscrizioni i"
			+ "where p.idpartita = pa.idpartita"
			+ "and pa.idiscrizione = i.idiscrizione"
			+ "and i.idgiocatore = ?";
}