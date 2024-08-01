package data;

public class Tournament {
	
	private static int codTorneo;
	private static String indirizzo;
	private static String dataInizio;
	private static int numPartecipanti;
	
	public Tournament(int cod, String indirizzo, String dataInizio, int numPartecipanti) {
		Tournament.codTorneo = cod;
		Tournament.indirizzo = indirizzo;
		Tournament.dataInizio = dataInizio;
		Tournament.numPartecipanti = numPartecipanti;
	}
	
	public int getCodTorneo() {
		return codTorneo;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public String getDataInizio() {
		return dataInizio;
	}
	public int getNumPartecipanti() {
		return numPartecipanti;
	}
	
	
}
