package controller;

import java.util.Map;

import GUI.LoginForm;
import GUI.UserUI;
import controller.api.LoginController;
import util.UserType;

public class LoginControllerImpl implements LoginController {
	
	// private static final "LoginLogics" logic
	private static final LoginForm view = new LoginForm();
	
	public LoginControllerImpl() {
		view.setRegisterHandler(data -> registrationAttempt(data));
		view.setLogInHandler(pair -> loginAttempt(pair.getX(), pair.getY()));
	}

	@Override
	public void loginAttempt(UserType type, Map<String, String> data) {
		var username = data.get("username");
		var password = data.get("password");
		/*
		 * dummy database query
		 */
		if(type.equals(UserType.ADMIN) && username.equals("admin") &&
				password.hashCode() == String.valueOf("root").hashCode()) {
			var ui = new UserUI();
			LoginControllerImpl.view.setVisible(false);
		}
		else {
			LoginControllerImpl.view.Error();
		}
	}

	@Override
	public void registrationAttempt(Map<String, String> data) {
		/* use the logic to insert in the database the new user, if not already present
		 * returns true if the new user is added successfully, returns false if the
		 * user name is already existing.
		 */
	}
}
