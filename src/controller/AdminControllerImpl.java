package controller;

import java.sql.Date;
import java.util.List;

import GUI.admin.AdminUI;
import controller.api.AdminController;
import data.Admin;
import data.Location;
import data.Tournament;
import model.DBModel;

public class AdminControllerImpl implements AdminController{
	
	private Admin model;
	private static final AdminUI view = new AdminUI();

	public AdminControllerImpl(Admin a) {
		model = a;
		view.setPostHandler(map -> {
			return postAnnounce(map.get("name").toString(), map.get("address").toString(),
					(Date)map.get("date"), (Integer)map.get("max"),
					(Integer)map.get("min"));
		});
		view.setAddRefereeHandler(map -> {
			return addReferee(map.get("name"), map.get("lastname"), map.get("cf"),
					map.get("username"), map.get("password"), map.get("address"));
		});
		view.setLocationHandler((add, desc) -> {
			return addLocation(add, desc);
		});
		view.updateLocationHandler(() -> {
			return getAddresses();
		});
		view.setTournamentsHandler(map -> {
			createTournament(map.get("address").toString(), map.get("name").toString(),
					((Integer)map.get("numSubs")).intValue(), ((Integer)map.get("idannounce")).intValue());
		}, id -> {
			deleteAnnounce(id);
		}, () -> {
			return getAnnounces();
		});
	}


	@Override
	public boolean postAnnounce(String name, String address, Date expiringDate, int maxSubs, int minSubs) {
		return Admin.DAO.postAnnounce(DBModel.getConnection(), name, address, expiringDate,
			maxSubs, minSubs, model.getIdAdmin());
	}
	
	@Override
	public int addReferee(String name, String lastname, String cf,
				String username, String password, String address) {
		return Admin.DAO.addReferee(DBModel.getConnection(), name, lastname, cf, username, password, address);
	}
	
	@Override
	public boolean addLocation(String address, String description) {
		return Admin.DAO.addLocation(DBModel.getConnection(), address, description);
	}
	
	@Override
	public List<String> getAddresses() {
		return Location.DAO.getAllAdresses(DBModel.getConnection());
	}
	
	public List<List<Object>> getAnnounces() {
		return Admin.DAO.getAnnounces(DBModel.getConnection());
	}
	
	public void createTournament(String address, String name, int numSubs, int idAnnounce) {
		Tournament.DAO.ufficialize(DBModel.getConnection(), address, name, numSubs, idAnnounce);
	}
	
	public void deleteAnnounce(int idAnnounce) {
		Admin.DAO.deleteAnnounce(DBModel.getConnection(), idAnnounce);
	}

}
