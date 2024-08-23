package data;

/**
 * this class contains all the queries to launch in the DB as public Strings.
 */
public class Queries {

	/**
     * query to check if a player is present in the db.
     */
    public static final String PLAYER_EXISTS =
            "select * "
            + "from giocatori "
            + "where username = ? "
            + "and password = ? ";

    /**
     * query to check if an admin is present in the db.
     */
    public static final String ADMIN_EXISTS =
            "select * "
            + "from organizzatori "
            + "where username = ? "
            + "and password = ? ";

    /**
     * query to check if a referee is present in the db.
     */
    public static final String REFEREE_EXISTS = 
            "select * "
            + "from arbitri "
            + "where username = ? "
            + "and password = ? "
            + "and numtessera = ?";

    /**
     * registers a new player into the db.
     */
    public static final String PLAYER_REGISTER = 
            "insert into giocatori(idgiocatore, punteggio, username, password, cf, nome, cognome) "
            + "values (null, 0, ?, ?, ?, ?, ?)";

    /**
     * query which searches in the database for all the
     * games played by a certain player.
     */
    public static final String GAMES_FOR_PLAYER = 
            "select * "
            + "from partite p, partecipanti pa, iscrizioni i "
            + "where p.idpartita = pa.idpartita "
            + "and pa.idiscrizione = i.idiscrizione "
            + "and i.idgiocatore = ?";
    // could be all in one, needs optimization - - -

    /**
     * number of games played by a certain player.
     */
    public static final String GAMES_PLAYED = 
            "select count distinct * "
            + "from partecipanti p, iscrizioni i "
            + "where p.idiscrizione = i.idiscrizione "
            + "and i.idgiocatore = ?";

    /**
     * games played as white by a certain player.
     */
    public static final String GAMES_AS_WHITE = 
            "select count distinct * "
            + "from partecipanti p, iscrizioni i "
            + "where p.idiscrizione = i.idiscrizione "
            + "and p.fazione = 'Bianco' "
            + "and i.idgiocatore = ?";

    /**
     * games played as black by a certain player.
     */
    public static final String GAMES_AS_BLACK = 
            "select count distinct * "
            + "from partecipanti p, iscrizioni i "
            + "where p.idiscrizione = i.idiscrizione "
            + "and p.fazione = 'Nero' "
            + "and i.idgiocatore = ?";
    // now i need wins in general, or wins as white/black
    // - - - - - - - - - - - - - - - - - - - - - -

    /**
     * adds a new tournament announce to the db.
     */
    public static final String POST_ANNOUNCE = 
            "insert into annunci (idannuncio, nome, indirizzo, scadenza,"
            + "maxiscrizioni, miniscrizioni, idadmin)"
            + "values (NULL, ?, ?, ?, ?, ?, ?)";

    /**
     * adds a new location into the db.
     */
    public static final String ADD_LOCATION =
            "insert into sedi (indirizzo, indicazioni) "
            + "values (?, ?)";

    /**
     * checks whether a location exists in the db or not.
     */
    public static final String LOCATION_EXISTS = 
            "select * "
            + "from sedi "
            + "where indirizzo = ?";

    /**
     * gets all addresses present in the db.
     */
    public static final String GET_ADDRESSES = 
            "select indirizzo "
            + "from sedi";

    /**
     * gets the number of players subscribed to a certain announce.
     */
    public static final String SUBS_PER_ANNOUNCE =
            "select count(*) as total "
            + "from iscrizioni i "
            + "where i.idannuncio = ?";

    /**
     * gets all the announces not expired to this day.
     */
    public static final String GET_ANNOUNCES =
            "select * "
            + "from annunci "
            + "where DATE(scadenza) > ?";

    /**
     * subscribes a player to an announce.
     */
    public static final String SUBSCRIPTION =
            "insert into iscrizioni (idiscrizione, idannuncio,"
            + " idgiocatore, data) "
            + "values (NULL, ?, ?, ?)";

    /**
     * checks whether a player is subscribed to an announce or not.
     */
    static final String IS_PLAYER_SUBSCRIBED =
            "select * "
            + "from iscrizioni "
            + "where idgiocatore = ? "
            + "and idannuncio = ?";

    /**
     * adds a new referee into the db.
     */
    public static final String ADD_REFEREE =
            "insert into arbitri (numtessera, nome, cognome, cf, "
            + "username, password) "
            + "values (NULL, ?, ?, ?, ?, ?)";

    /**
     * gets the id of a ref from username and password.
     */
    public static final String REF_GET_ID =
            "select numtessera "
            + "from arbitri "
            + "where username = ? "
            + "and password = ?";

    /**
     * assigns a ref to a certain location.
     */
    public static final String ASSIGN_REF =
            "insert into gestione "
            + "(indirizzo, numtessera) "
            + "values (?, ?)";

    /**
     * unsubscribes a player who's already subscribed to an announce.
     */
    public static final String UNSUBSCRIBE =
            "delete from iscrizioni "
            + "where idannuncio = ? "
            + "and idgiocatore = ?";

    /**
     * gets all the expired announces from the db.
     */
    public static final String GET_EXP_ANNOUNCES =
            "SELECT * "
            + "FROM annunci a "
            + "WHERE DATE(a.scadenza) <= ? "
            + "and not exists (select * from tornei "
            + "where idannuncio = a.idannuncio)";
    /**
     * adds a new tournament into the db.
     */
    public static final String CREATE_TOURNAMENT =
            "insert into tornei(codtorneo, indirizzo, nome, "
            + "datainizio, numpartecipanti, idannuncio) values "
            + "(NULL, ?, ?, ?, ?, ?)";

    /**
     * deletes an announce from the db.
     */
    public static final String DELETE_ANNOUNCE =
            "delete from annunci "
            + "where idannuncio = ?";

    /**
     * deletes all the subscription to a certain announce.
     */
    public static final String DELETE_SUBS =
            "delete from iscrizioni "
            + "where idannuncio = ?";

    /**
     * gets the participant who played a certain match as white.
     */
    public static final String GET_GAME_WHITE =
            "select idiscrizione "
            + "from partecipanti "
            + "where codpartita = ? and fazione = 'Bianco'";

    /**
     * gets the participant who played a certain match as black.
     */
    public static final String GET_GAME_BLACK =
            "select idiscrizione "
            + "from partecipanti "
            + "where codpartita = ? and fazione = 'Nero'";

    /**
     * adds a new move to a game.
     */
    public static final String GAME_ADD_MOVE =
            "insert into mosse ( idiscrizione, codpartita, pezzo, nuovopezzo, pezzocatturato, "
            + "matto, rigaarrivo, colonnaarrivo, rigapartenza, colonnapartenza, stringamossa ) "
            + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * adds a new turn to a match.
     */
    public static final String GAME_ADD_TURN =
            "insert into turni (codpartita, mossabianca, mossanera, numturno) "
            + "values (?, ?, ?, ?)";

    /**
     * get all the subscribers to an announce.
     */
    public static final String GET_ALL_SUBS = 
            "select * "
            + "from iscrizioni "
            + "where idannuncio = ?";

    /**
     * adds a new participant to a match.
     */
    public static final String ADD_PARTICIPANT =
            "insert into partecipanti "
            + "(codpartita, idiscrizione, fazione) "
            + "values (?, ?, ?)";
    /**
     * adds a new game to the db.
     */
    public static final String CREATE_GAME =
            "insert into partite "
            + "(codpartita, codtorneo, vincitore, data) "
            + "values (NULL, ?, '', ?)";

    /**
     * searches for all the players who's name matches a certain pattern.
     */
    public static final String GET_PLAYERS =
			"select idgiocatore nome cognome punteggio "
			+ "from giocatori "
			+ "where nome like concat('%', ?, '%')"
			+ "and cognome like concat('%', ?, '%')";

    /**
     * searches for a game in the db with certain participants.
     */
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

    /**
     * selects the oldest game.
     */
    public static final String OLDEST_DATE =
            "select min(data) as data from partite ";

    /**
     * get's all the moves registered to a game.
     */
    public static final String GET_GAME_MOVES =
            "select t.numturno, mb.stringamossa as mossa_bianca, mn.stringamossa as mossa_nera "
            + "from turni t "
            + "join mosse mb on t.mossabianca = mb.idmossa "
            + "left join mosse mn on t.mossanera = mn.idmossa "
            + "where t.codpartita = ? "
            + "order by t.numturno asc ";

    /**
     * get's all the data from a certain anounce.
     */
    public static final String GET_ANNOUNCE_DATA = 
            "select * "
            + "from annunci "
            + "where idannuncio = ?";

    /**
     * chooses a certain number of refs, to be designated for a new tournament.
     */
    public static final String CHOOSE_REFS =
            "select a.numtessera, (select count(*) from designazioni d where d.numtessera = a.numtessera ) as design "
            + "from arbitri a, gestione g "
            + "where a.numtessera = g.numtessera "
            + "and g.indirizzo = ? "
            + "order by design desc "
            + "limit ?";

    /**
     * add a designation of a ref to a tournament.
     */
    public static final String ADD_DESIGNATION =
            "insert into designazioni (coddesignazione, numtessera, codtorneo) "
            + "values (NULL, ?, ?)";

    /**
     * get's a ordered list of players, based on the number of games
     * played in the last month.
     */
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

    /**
     * get's a ordered list of players, based on the elo score.
     */
    public static final String HIGHEST_RATED =
			"select g.idgiocatore, g.nome, g.cognome, g.punteggio, count(p.codpartita) as numero_partite "
			+ "from giocatori g "
			+ "left join iscrizioni i on g.idgiocatore = i.idgiocatore "
			+ "left join partecipanti pa on i.idiscrizione = pa.idiscrizione "
			+ "left join partite p on pa.codpartita = p.codpartita "
			+ "group by g.idgiocatore, g.username, g.punteggio "
			+ "order by g.punteggio desc "
			+ "limit 20 ";

    /**
     * get's a ordered list of players, based on the number of wins in games
     * played in the last month.
     */
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
    
    /**
     * gets list of games against a specified opponent in the specified period 
     */
	public static final String GET_GAME_VS_OPPONENT =
			"select p.codpartita, g_bianco.nome as nome_bianco, g_bianco.cognome as cognome_bianco, "
			+ "g_nero.nome as nome_nero, g_nero.cognome as cognome_nero, "
			+ "p.vincitore, p.data, t.nome as nome_torneo "
			+ "from partite p "
			+ "join partecipanti pa_bianco on p.codpartita = pa_bianco.codpartita "
			+ "join iscrizioni i_bianco on pa_bianco.idiscrizione = i_bianco.idiscrizione "
			+ "join giocatori g_bianco on i_bianco.idgiocatore = g_bianco.idgiocatore "
			+ "join partecipanti pa_nero on p.codpartita = pa_nero.codpartita "
			+ "join iscrizioni i_nero on pa_nero.idiscrizione = i_nero.idiscrizione "
			+ "join giocatori g_nero on i_nero.idgiocatore = g_nero.idgiocatore "
			+ "join tornei t on p.codtorneo = t.codtorneo "
			+ "where (g_bianco.idgiocatore = ? or g_nero.idgiocatore = ? ) "
			+ "and (( g_bianco.nome like concat('%', ?, '%') and g_bianco.cognome like concat('%', ?, '%') and g_nero.idgiocatore = ? ) "
			+ "or (g_nero.nome like concat('%', ?, '%') and g_nero.cognome like concat('%', ?, '%') and g_bianco.idgiocatore = ? )) "
			+ "and p.data < ? "
			+ "order by p.data ";

    private Queries() { }
}