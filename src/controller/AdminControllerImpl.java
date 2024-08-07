package controller;

import java.sql.Date;

import GUI.AdminUI;
import controller.api.AdminController;
import data.Admin;
import model.DBModel;

public class AdminControllerImpl implements AdminController{
	
	private static Admin model;
	private static final AdminUI view = new AdminUI();

	public AdminControllerImpl(Admin a) {
		AdminControllerImpl.model = a;
	}

	@Override
	public void searchPlayers(String input) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void searchGames(String input) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postAnnounce(String address, Date expiringDate, int maxSubs, int minSubs) {
		Admin.DAO.postAnnounce(DBModel.getConnection(), address, expiringDate,
			maxSubs, minSubs, model.getIdAdmin());
	}

}
