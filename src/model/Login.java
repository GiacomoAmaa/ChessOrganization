package model;

import data.Admin;
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
			break;
		
		}
		return false;
	}
	
	public void close() {
		DBModel.closeConnection();
	}
}
