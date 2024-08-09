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
		view.setPostHandler(map -> {
			return postAnnounce((String)map.get("address"),
					(Date)map.get("date"), (Integer)map.get("min"),
					(Integer)map.get("max"));
		});
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
	public boolean postAnnounce(String address, Date expiringDate, int maxSubs, int minSubs) {
		return Admin.DAO.postAnnounce(DBModel.getConnection(), address, expiringDate,
			maxSubs, minSubs, model.getIdAdmin());
	}

}
