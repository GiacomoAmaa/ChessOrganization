package controller.api;

import java.util.Map;
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
	public void loginAttempt(UserType type, Map<String, String> data);
	
	/**
	 * handles registration (by players only)
	 * @param usr the username
	 * @param pwd the password
	 * @return true if it's successful, false otherwise
	 */
	public void registrationAttempt(Map<String, String> data);
	
}
