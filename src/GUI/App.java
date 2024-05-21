package GUI;

import controller.LoginControllerImpl;
import data.DAOUtils;

public class App {

	public static void main(String[] args) {
		//var login = new LoginControllerImpl();
		var test = new UserUI();
		// established a new connection to the db
		var connection = DAOUtils.localMySQLConnection("chessorg", "root", "");
	}
}
