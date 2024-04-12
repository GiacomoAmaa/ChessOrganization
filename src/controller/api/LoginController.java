package controller.api;

import java.util.Optional;

import util.UserType;

/**
 * The controller of a simple log in form, to log in or register into Chess Org
 */
public interface LoginController {

	/**
	 * Handles log in attempts by the users
	 * @param type the type of user who tries to log in, could be administrator, referee or player
	 * @param usr the username
	 * @param pwd the password
	 * @param card the card number needed by referees to log in
	 * @return true if it's successful, false otherwise 
	 */
	public boolean loginAttempt(UserType type, String usr, String pwd, Optional<String> card);
	
	/**
	 * handles registration (by players only)
	 * @param usr the username
	 * @param pwd the password
	 * @return true if it's successful, false otherwise
	 */
	public boolean registrationAttempt(String usr, String pwd);
	
}
