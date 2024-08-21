package controller.api;

import java.util.List;

/**
 * The controller of the User profile, a user must be able to see his games, his stats,
 * to sign in for available tournaments and also search for other users
 */
public interface PlayerController {

	/**
	 * @param input the input string to search occurencies in the database
	 * has no output because this method just displays visually
	 * what has been found by the query.
	 * The search is done in the Games table.
	 */
	public void searchGames(String input);
	
	/**
	 * @param input the input string to search occurencies in the database
	 * has no output because this method just displays visually
	 * what has been found by the query.
	 * The search is done in the Players table.
	 */
	public void searchPlayers(String input);
	
	/**
	 * Shows all the tournaments where the user can subscribe to or is already signed to.
	 * @return 
	 */
	public List<List<Object>> tournaments();
	
	/**
	 * Shows to the player all of his/her stats:
	 * - games played
	 * - w/l ratio
	 * - tournaments won
	 */
	public void stats();
	
	public void games();
	
}
