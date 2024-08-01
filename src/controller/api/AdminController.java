package controller.api;
/** 
 * The controller of an Admin-type user, each admin must be able to create new tournaments,
 * see games and tournaments and search for players/games
 */
public interface AdminController {
	
	public void tournaments();
	
	public void games();
	
	public void searchPlayers(String input);
	
	public void searchGames(String input);
	
	// public void newTournament(Tournament newTour);
}
