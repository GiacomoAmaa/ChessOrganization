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
			"SELECT * "
			+ "FROM annunci a "
			+ "WHERE DATE(a.scadenza) <= ? "
			+ "and not exists (select * from tornei "
			+ "where idannuncio = a.idannuncio)";
	// should also create participants and games;
	public static final String CREATE_TOURNAMENT =
			"insert into tornei(codtorneo, indirizzo, nome, "
			+ "datainizio, numpartecipanti, idannuncio) values "
			+ "(NULL, ?, ?, ?, ?, ?)";
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
	public static final String GET_ALL_SUBS = 
			"select * "
			+ "from iscrizioni "
			+ "where idannuncio = ?";
	public static final String ADD_PARTICIPANT =
			"insert into partecipanti "
			+ "(codpartita, idiscrizione, fazione) "
			+ "values (?, ?, ?)";
	public static final String CREATE_GAME =
			"insert into partite "
			+ "(codpartita, codtorneo, vincitore, data) "
			+ "values (NULL, ?, '', ?)";
	public static final String GET_PLAYERS =
			"select idgiocatore nome cognome punteggio "
			+ "from giocatori "
			+ "where nome like concat('%', ?, '%')"
			+ "and cognome like concat('%', ?, '%')";
	public static final String GET_GAME_WITH_PLAYERS = 
			"select p.codpartita, p.data, t.nome as nome_torneo, p.vincitore,	"
			+ " g1.nome as nome_bianco, g1.cognome as cognome_bianco, "
			+ " g2.nome as nome_nero, g2.cognome as cognome_nero "
			+ "from partite p "
			+ "join tornei t on p.codtorneo = t.codtorneo "
			+ "join partecipanti pb on p.codpartita = pb.codpartita and pb.fazione = 'Bianco' "
			+ "join iscrizioni isb on pb.idiscrizione = isb.idiscrizione "
			+ "join giocatori g1 on isb.idgiocatore = g1.idgiocatore "
			+ "join partecipanti pn on p.codpartita = pn.codpartita and pn.fazione = 'Nero' "
			+ "join iscrizioni isn on pn.idiscrizione = isn.idiscrizione "
			+ "join giocatori g2 on isn.idgiocatore = g2.idgiocatore "
			+ "where ( g1.nome like concat('%', ?, '%') "
			+ "or g1.cognome like concat('%', ?, '%') ) "
			+ "and ( g2.nome like concat('%', ?, '%') "
			+ "or g2.cognome like concat('%', ?, '%') ) "
			+ "and p.data between ? and ? "
			+ "and p.vincitore in ('Bianco', 'Nero', 'Pari') ";
	public static final String OLDEST_DATE =
			"select min(data) as data from partite ";
	public static final String GET_GAME_MOVES =
			"select t.numturno, mb.stringamossa as mossa_bianca, mn.stringamossa as mossa_nera "
			+ "from turni t "
			+ "join mosse mb on t.mossabianca = mb.idmossa "
			+ "left join mosse mn on t.mossanera = mn.idmossa "
			+ "where t.codpartita = ? "
			+ "order by t.numturno asc ";
	public static final String GET_ANNOUNCE_DATA = 
			"select * "
			+ "from annunci "
			+ "where idannuncio = ?";
	public static final String CHOOSE_REFS =
			"select a.numtessera, (select count(*) from designazioni d where d.numtessera = a.numtessera ) as design "
			+ "from arbitri a, gestione g "
			+ "where a.numtessera = g.numtessera "
			+ "and g.indirizzo = ? "
			+ "order by design desc "
			+ "limit ?";
	public static final String ADD_DESIGNATION =
			"insert into designazioni (coddesignazione, numtessera, codtorneo) "
			+ "values (NULL, ?, ?)";
	public static final String MOST_ACTIVE_PLAYERS =
			"select g.idgiocatore, g.nome, g.cognome, g.punteggio, count(p.codpartita) as numero_partite "
			+ "from giocatori g "
			+ "join iscrizioni i on g.idgiocatore = i.idgiocatore "
			+ "join partecipanti pt on i.idiscrizione = pt.idiscrizione "
			+ "join partite p on pt.codpartita = p.codpartita "
			+ "where p.data >= date_sub(curdate(), interval 1 month) "
			+ "group by g.idgiocatore "
			+ "order by numero_partite desc "
			+ "limit 20";
	public static final String HIGHEST_RATED =
			"select g.idgiocatore, g.nome, g.cognome, g.punteggio, count(p.codpartita) as numero_partite "
			+ "from giocatori g "
			+ "left join iscrizioni i on g.idgiocatore = i.idgiocatore "
			+ "left join partecipanti pa on i.idiscrizione = pa.idiscrizione "
			+ "left join partite p on pa.codpartita = p.codpartita "
			+ "group by g.idgiocatore, g.username, g.punteggio "
			+ "order by g.punteggio desc "
			+ "limit 20 ";
	public static final String BEST_CLIMBERS =
			"select g.idgiocatore, g.nome, g.cognome, g.punteggio, count(p.codpartita) as numero_vittorie "
			+ "from giocatori g "
			+ "join iscrizioni i on g.idgiocatore = i.idgiocatore "
			+ "join partecipanti pt on i.idiscrizione = pt.idiscrizione "
			+ "join partite p on pt.codpartita = p.codpartita "
			+ "where p.data >= date_sub(currdate(), interval 1 month) "
			+ "and ((pt.fazione = 'Bianco' and p.vincitore = 'Bianco') "
			+ "     or (pt.fazione = 'Nero' and p.vincitore = 'Nero')) "
			+ "group by g.idgiocatore "
			+ "order by numero_vittorie desc";
}