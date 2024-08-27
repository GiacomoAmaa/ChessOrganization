ISTRUZIONI PER IL CORRETTO AVVIO DELL'APPLICAZIONE

1. avviare xampp con host MySQL sulla porta 3306.
2. navigare al link http://localhost/phpmyadmin/ e creare un nuovo database.
3. aprire il pannello SQL, copiare e incollare il contenuto del file "creazione_db.sql" situato nella cartella "db_init" nella root del progetto.
4. eseguire la query.
5. cancellare il pannello SQL, copiare e incollare il contenuto del file "inizializza_db.sql" situato nella cartella "db_init" nella root del progetto.
6. avviare con doppio click l'applicazione java "chessOrg.jar" situato nella cartella "bin" nella root di progetto.
7. Sarà possibile effettuare il log in con tre diversi utenti:
	- username: "player", password: "root" permetterà di accedere alla pagina del giocatore
	- username: "ref", password: "root", numero tessera: 1 permetterà di accedere alla pagina dell'arbitro
	- username: "admin", password: "root", permetterà di accedere alla pagina dell'organizzatore

<giocatore> la barra di navigazione presenta i seguenti menu: "Personal Area", "Leaderboard", "Announcements", "Search" e "Logout"
	(Personal Area -> Games) qui il giocatore potrà controllare lo storico delle sue partite, il form di ricerca permette di inserire:		- nome dell'avversario
		- cognome dell'avversario
		- data dalla quale si vuole limitare la ricerca
	Il pulsante "Search" avvia la ricerca, i record risultanti verranno mostrati sotto come forma di tabella
! NAVIGARE LE TABELLE DEI RISULTATI !
Per poter navigare le tabelle dei risultati è necessario cliccare sulla checkbox nella colonna "Select" alla riga del risultato interessato e, successivamente cliccare su "Confirm"; se il risultato era una partita, sarà possibile scorrerne le mosse tramite i due pulsanti "Next Move" e "Previous Move", se, invece, il risultato era un giocatore si aprirà la sua pagine di statistiche personali, dove sarà possibile visualizzare una heatmap delle caselle più utilizzate dal giocatore. Modificando la combo box, si potranno alternare i grafici dell'andamento del punteggio del giocatore.
Qual ora si desiderasse abbandonare la sezione partita o statistiche sarà sufficiente utilizzare la barra di navigazione.
	(Personal Area -> Statistics) mostrerà le statistiche dell'utente
	(Leaderboard) mostrerà la leaderboard, tramite la combo box sarà possibile visualizzare le in tre diversi ordinamenti:
		- most active player: giocatori che hanno giocato più partite nell'ultimo mese
		- highest rated: giocatori con il punteggio più alto
		- best climber: giocatori che hanno vinto più partite nell'ultimo mese
	(Search) mostrerà il form di ricerca, si può scegliere se cercare giocatori o partite tramite la combobox, le tabelle dei 	risultati si navigano in modo analogo
	(Announcements) mostrerà una tabella di annunci (non ancora scaduti) a cui è possibile iscriversi (o disiscriversi se si è già 	iscritti) tramite gli appositi pulsanti
N.B. tutte le sezione "Search" SONO UGUALI PER TUTTI GLI UTENTI!!!
<arbitro> la barra di navigazione differisce per il menu "My Designations"
	(My Designations) apre il form di ricerca delle partite per cui l'utente (arbitro) è registrato, è possibile cercare una partita 	per nome/cognome del giocatore bianco, nome/cognome del giocatore nero e limitare la data di ricerca
	selezionare un risultato aprirà la schermata di registrazione delle partite in cui, tramite il form in intestazione sarà possibile 	registrare le mosse riempiendo i campi.
N.B. è POSSIBILE ESEGUIRE MOSSE ILLEGALI, l'esecuzione di mosse illegali comporterà il mal funzionamento dell'interfaccia grafica, in quanto arbitro è l'utente ad assumersi la responsabilità di eseguire solo mosse legali!!!
	se si desidera abbandonare l'interfaccia è sufficiente usare la barra di navigazione.
<organizzatore> la barra di navigazione comprende 3 nuove sezioni: "Tournaments", "Add Location" e "Add Referees"
	(Tournaments -> Post Announce) permetterà all'utente di compilare un form con tutte le informazioni necessarie per pubblicare un 
	nuovo announcio, tutti i campi devono essere riempiti per poter pubblicare.
	(Tournaments -> Create Tournament) apre la sezione degli annunci scaduti/in scadenza, dove sarà possibile per l'utente selezionare 	l'annuncio e decidere se ufficializzarlo o cancellarlo tramite gli appositi pulsanti
	(Add Location) apre un form per aggiungere una nuova sede al database
	(Add referees) apre un form per inserire un nuovo arbitro e assegnarlo immediatamente ad una sede, se la creazione va a buon fine, 	verrà restituito il nuovo numero tessere dell'arbitro.
Infine il menu "Logout" in tutte le interfacce permetterà di chiudere l'applicazione dopo una conferma.
