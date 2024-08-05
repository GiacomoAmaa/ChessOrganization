package model;

import java.util.Optional;

import data.Admin;
import data.DAOException;
import data.Player;
import data.Referee;

public class Login {
	
	public Optional<Referee> refereeAttempt(String username, String password, String code) {
		// i'll convert code to int, because the db contains int card numbers
		return Referee.DAO.exists(DBModel.getConnection(), username, password, Integer.valueOf(code));
	}
	
	public Optional<Player> playerAttempt(String username, String password) {
		return Player.DAO.exists(DBModel.getConnection(), username, password);
	}
	
	public Optional<Admin> adminAttempt(String username, String password) {
		return Admin.DAO.exists(DBModel.getConnection(), username, password);
	}
	
	public boolean registration(String name, String lastname, String cf, String username, String password) {
		if (Player.DAO.exists(DBModel.getConnection(), username, password).isEmpty()) {
			try {
				Player.DAO.newInstance(DBModel.getConnection(), name, lastname, cf, username, password);
				// the return statement is executed only if the insert has positive result
				return true;
			} catch (DAOException e) {}
		}
		return false;
	}
}
