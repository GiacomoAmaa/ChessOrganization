package controller;

import java.sql.Date;
import java.util.List;

import GUI.AdminUI;
import controller.api.AdminController;
import data.Admin;
import data.Location;
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
		view.setLocationHandler((add, desc) -> {
			return addLocation(add, desc);
		});
		view.updateLocationHandler(() -> {
			return getAddresses();
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
	
	public boolean addLocation(String address, String description) {
		return Admin.DAO.addLocation(DBModel.getConnection(), address, description);
	}
	
	public List<String> getAddresses() {
		return Location.DAO.getAllAdresses();
	}

}
