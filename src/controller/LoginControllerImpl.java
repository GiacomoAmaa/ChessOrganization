package controller;

import java.util.Map;

import GUI.LoginForm;
import controller.api.LoginController;
import model.Login;
import util.UserType;

public class LoginControllerImpl implements LoginController {
	
	private static final Login model = new Login();
	private static final LoginForm view = new LoginForm();
	
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
		switch(type) {
			case ADMIN:
				if (!data.entrySet().isEmpty()) {
					var admin = LoginControllerImpl.model.adminAttempt(data.get("username"), data.get("password"));
					if(admin.isPresent()) {
						view.close();
						new AdminControllerImpl(admin.get());
						break;
					}
				}
				LoginControllerImpl.view.Error();
				break;
			case PLAYER:
				if (!data.entrySet().isEmpty()) {
					var player = LoginControllerImpl.model.playerAttempt(data.get("username"), data.get("password"));
					if(player.isPresent()) {
						view.close();
						new PlayerControllerImpl(player.get());
						break;
					}
				}
				LoginControllerImpl.view.Error();
				break;
			case REFEREE:
				if (!data.entrySet().isEmpty()) {
					var referee = LoginControllerImpl.model.refereeAttempt(data.get("username"), data.get("password"), data.get("cardNumber"));
					if (referee.isPresent()) {
						view.close();
						new RefereeControllerImpl(referee.get());
						break;
					}
				}
				LoginControllerImpl.view.Error();
				break;
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
				var p = LoginControllerImpl.model.playerAttempt(data.get("username"), data.get("password"));
				new PlayerControllerImpl(p.get());
				return;
			}
		}
		LoginControllerImpl.view.missingData();
	}
}
