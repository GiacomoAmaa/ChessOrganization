package controller;

import GUI.AdminUI;
import controller.api.AdminController;
import data.Admin;
import data.Player;

public class AdminControllerImpl implements AdminController{
	
	private static Admin model;
	private static final AdminUI view = new AdminUI();

	public AdminControllerImpl(Admin a) {
		AdminControllerImpl.model = a;
	}

	@Override
	public void tournaments() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void games() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void searchPlayers(String input) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void searchGames(String input) {
		// TODO Auto-generated method stub
		
	}

}
