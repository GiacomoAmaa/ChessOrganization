package controller;

import GUI.PlayerUI;
import controller.api.PlayerController;
import data.Player;
import model.DBModel;

public class PlayerControllerImpl implements PlayerController{
	
	private static Player model;
	private static final PlayerUI view = new PlayerUI();
	
	public PlayerControllerImpl(Player p) {
		PlayerControllerImpl.model = p;
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
	public void tournaments() {
		// TODO Auto-generated method stub
		
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
