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
			"select * "
			+ "from arbitri "
			+ "where username = ? "
			+ "and password = ? "
			+ "and numtessera = ?";
	public static final String PLAYER_REGISTER = 
			"insert into giocatori(idgiocatore, punteggio, username, password, cf, nome, cognome) "
			+ "values (null, 0, ?, ?, ?, ?, ?)";
	public static final String GAMES_FOR_PLAYER = 
			"select * "
			+ "from partite p, partecipanti pa, iscrizioni i "
			+ "where p.idpartita = pa.idpartita "
			+ "and pa.idiscrizione = i.idiscrizione "
			+ "and i.idgiocatore = ?";
	// could be all in one, needs optimization - - -
	public static final String GAMES_PLAYED = 
			"select count distinct * "
			+ "from partecipanti p, iscrizioni i "
			+ "where p.idiscrizione = i.idiscrizione "
			+ "and i.idgiocatore = ?";
	public static final String GAMES_AS_WHITE = 
			"select count distinct * "
			+ "from partecipanti p, iscrizioni i "
			+ "where p.idiscrizione = i.idiscrizione "
			+ "and p.fazione = 'Bianco' "
			+ "and i.idgiocatore = ?";
	public static final String GAMES_AS_BLACK = 
			"select count distinct * "
			+ "from partecipanti p, iscrizioni i "
			+ "where p.idiscrizione = i.idiscrizione "
			+ "and p.fazione = 'Nero' "
			+ "and i.idgiocatore = ?";
	// now i need wins in general, or wins as white/black
	// - - - - - - - - - - - - - - - - - - - - - -
	public static final String POST_ANNOUNCE = 
			"insert into annunci (idannuncio, nome, indirizzo, scadenza,"
			+ "maxiscrizioni, miniscrizioni, idadmin)"
			+ "values (NULL, ?, ?, ?, ?, ?, ?)";
	public static final String ADD_LOCATION =
			"insert into sedi (indirizzo, indicazioni) "
			+ "values (?, ?)";
	public static final String LOCATION_EXISTS = 
			"select * "
			+ "from sedi "
			+ "where indirizzo = ?";
	public static final String GET_ADDRESSES = 
			"select indirizzo "
			+ "from sedi";
	public static final String SUBS_PER_ANNOUNCE =
			"select count(*) as total "
			+ "from iscrizioni i "
			+ "where i.idannuncio = ?";
	public static final String GET_ANNOUNCES =
			"select * "
			+ "from annunci "
			+ "where DATE(scadenza) > ?";
	public static final String SUBSCRIPTION =
			"insert into iscrizioni (idiscrizione, idannuncio,"
			+ " idgiocatore, data) "
			+ "values (NULL, ?, ?, ?)";
	public static final String IS_PLAYER_SUBSCRIBED =
			"select * "
			+ "from iscrizioni "
			+ "where idgiocatore = ? "
			+ "and idannuncio = ?";
	public static final String ADD_REFEREE =
			"insert into arbitri (numtessera, nome, cognome, cf, "
			+ "username, password) "
			+ "values (NULL, ?, ?, ?, ?, ?)";
	public static final String REF_GET_ID =
			"select numtessera "
			+ "from arbitri "
			+ "where username = ? "
			+ "and password = ?";
	public static final String ASSIGN_REF =
			"insert into gestione "
			+ "(indirizzo, numtessera) "
			+ "values (?, ?)";
	public static final String UNSUBSCRIBE =
			"delete from iscrizioni "
			+ "where idannuncio = ? "
			+ "and idgiocatore = ?";
	public static final String 	GET_EXP_ANNOUNCES =
			"select * "
			+ "from annunci "
			+ "where DATE(scadenza) <= ?";
	// should also create participants and games;
	public static final String CREATE_TOURNAMENT =
			"insert into tornei(codtorneo, indirizzo, nome, "
			+ "datainizio, numpartecipanti, idannuncio) values "
			+ "(NULL, ?, ?, ?, ?)";
	public static String DELETE_ANNOUNCE =
			"delete from annunci "
			+ "where idannuncio = ?";
	public static final String DELETE_SUBS =
			"delete from iscrizioni "
			+ "where idannuncio = ?";
	public static final String GET_GAME_WHITE =
			"select idiscrizione "
			+ "from partecipanti "
			+ "where codpartita = ? and fazione = 'Bianco'";
	public static final String GET_GAME_BLACK =
			"select idiscrizione "
			+ "from partecipanti "
			+ "where codpartita = ? and fazione = 'Nero'";
	public static final String GAME_ADD_MOVE =
			"insert into mosse ( idiscrizione, codpartita, pezzo, nuovopezzo, pezzocatturato, "
			+ "matto, rigaarrivo, colonnaarrivo, rigapartenza, colonnapartenza, stringamossa ) "
			+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String GAME_ADD_TURN =
			"insert into turni (codpartita, mossabianca, mossanera, numturno) "
			+ "values (?, ?, ?, ?)";
}