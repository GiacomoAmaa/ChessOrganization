package controller.api;

import java.sql.Date;

import data.Tournament;

/** 
 * The controller of an Admin-type user, each admin must be able to create new tournaments,
 * see games and tournaments and search for players/games
 */
public interface AdminController {
	
	/**
	 * @param input, an input string to search for players names
	 * Lets the admin search for players through a searchbar
	 */
	public void searchPlayers(String input);
	
	/**
	 * @param input, an input string to search for games names
	 * Lets the admin search for players through a searchbar
	 */
	public void searchGames(String input);
	
	/**
	 * @param newTour new tournament announce to post
	 * Lets the admin post a tournament announce
	 */
	public boolean postAnnounce(String name, String address, Date expiringDate, int maxSubs, int minSubs);
}
