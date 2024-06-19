package model;

import data.Admin;
import data.DAOException;
import data.Player;
import util.UserType;

public class Login {
	
	public boolean attempt(UserType type, String username, String password, String code) {
		switch(type) {
		case ADMIN:
			return Admin.DAO.exists(DBModel.getConnection(), username, password);
		case PLAYER:
			return Player.DAO.exists(DBModel.getConnection(), username, password);
		case REFEREE:
			// a method with the same template
			break;
		
		}
		return false;
	}
	
	public boolean registration(String name, String lastname, String cf, String username, String password) {
		if (!Player.DAO.exists(DBModel.getConnection(), username, password)) {
			try {
				Player.DAO.newInstance(DBModel.getConnection(), name, lastname, cf, username, password);
				// the return statement is executed only if the insert has positive result
				return true;
			} catch (DAOException e) {}
		}
		return false;
	}
	
	public void close() {
		DBModel.closeConnection();
	}
}
