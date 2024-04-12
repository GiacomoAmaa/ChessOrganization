package controller;

import java.util.Optional;

import controller.api.LoginController;
import util.UserType;

public class LoginControllerImpl implements LoginController {
	
	// private static final "LoginLogics" logic

	@Override
	public boolean loginAttempt(UserType type, String usr, String pwd, Optional<String> card) {
		/* use the logic to query the database in order to know if the user exists.
		 * returns true if the user is registered, false if it doesn't.
		 */
		return false;
	}

	@Override
	public boolean registrationAttempt(String usr, String pwd) {
		/* use the logic to insert in the database the new user, if not already present
		 * returns true if the new user is added successfully, returns false if the
		 * user name is already existing.
		 */
		return false;
	}
}
