package controller.api;

import java.sql.Date;
import java.util.List;


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
	 * creates a new tournament announce in the db
	 * @param name name of the torunament
	 * @param address location of the tournament
	 * @param expiringDate expiration date
	 * @param maxSubs
	 * @param minSubs
	 * @return true if correctly added, false otherwise.
	 */
	public boolean postAnnounce(String name, String address, Date expiringDate, int maxSubs, int minSubs);
	
	/**
	 * adds a new referee to the db
	 * @param name name of the referee
	 * @param lastname
	 * @param cf
	 * @param username
	 * @param password
	 * @param address location to be bonded to the referee
	 * @return the card number of the new referee, -1 if not added.
	 */
	public int addReferee(String name, String lastname, String cf, String username, String password, String address);
	
	/**
	 * adds a new location to the db
	 * @param address street address of the location
	 * @param description
	 * @return true if added, false otherwise.
	 */
	public boolean addLocation(String address, String description);
	
	/**
	 * gets a list of all the addresses iin the db
	 * @return the addresses
	 */
	public List<String> getAddresses();
}
