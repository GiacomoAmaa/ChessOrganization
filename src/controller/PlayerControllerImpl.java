package controller;

import java.util.List;

import GUI.PlayerUI;
import controller.api.PlayerController;
import data.Player;
import model.DBModel;

public class PlayerControllerImpl implements PlayerController{
	
	private static Player model;
	private static final PlayerUI view = new PlayerUI();
	
	public PlayerControllerImpl(Player p) {
		PlayerControllerImpl.model = p;
		view.setTournamentsHandler(() -> {
			return tournaments();
		});
	}

	@Override
	public void searchGames(String input) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void searchPlayers(String input) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<List<String>> tournaments() {
		return Player.DAO.getAnnounces(DBModel.getConnection());
	}

	@Override
	public void stats() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void games() {
		// TODO Auto-generated method stub
		
	}

}
