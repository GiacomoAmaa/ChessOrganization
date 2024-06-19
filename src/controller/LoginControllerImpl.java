package controller;

import java.util.Map;

import GUI.LoginForm;
import controller.api.LoginController;
import model.Login;
import util.UserType;

public class LoginControllerImpl implements LoginController {
	
	private static final Login model = new Login();
	private static final LoginForm view = new LoginForm(() -> {
		LoginControllerImpl.model.close();
	});
	
	public LoginControllerImpl() {
		view.setRegisterHandler(data -> registrationAttempt(data));
		view.setLogInHandler(pair -> loginAttempt(pair.getX(), pair.getY()));
	}

	/* data is a map with every String is associated with the field name:
	 * "username" -> actual username
	 * "passowrd" -> actual password
	 * "code" -> actual code/null
	 */
	@Override
	public void loginAttempt(UserType type, Map<String, String> data) {
		if (LoginControllerImpl.model.attempt(type, data.get("username"), data.get("password"), data.get("code"))) {
			LoginControllerImpl.view.setVisible(false);
			switch(type) {
			case ADMIN:
				//new AdminControllerImpl();
				break;
			case PLAYER:
				new PlayerControllerImpl();
				break;
			case REFEREE:
				//new RefereeControllerImpl();
				break;
			}
			LoginControllerImpl.model.close();
		} else {
			LoginControllerImpl.view.Error();
		}
		
	}

	@Override
	public void registrationAttempt(Map<String, String> data) {
		/* use the logic to insert in the database the new user, if not already present
		 * returns true if the new user is added successfully, returns false if the
		 * user name is already existing.
		 */
		// it means that there's no information missing
		if (data.entrySet().size() == 5) {
			System.out.println(data.get("password"));
			if (!LoginControllerImpl.model.registration(data.get("name"), data.get("lastname"), data.get("cf"),
					data.get("username"), data.get("password"))
			) {
				LoginControllerImpl.view.alreadyExist();
			}
			else {
				new PlayerControllerImpl();
			}
		}
		LoginControllerImpl.view.missingData();
	}
}
