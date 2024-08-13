package data;

import java.sql.Date;
import java.time.LocalDate;

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
			"select count distinct i.idgiocatore as 'total' "
			+ "from iscrizioni i"
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
}