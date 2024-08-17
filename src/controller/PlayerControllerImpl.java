package controller;

import java.util.List;

import GUI.player.PlayerUI;
import controller.api.PlayerController;
import data.Announce;
import data.Player;
import model.DBModel;

public class PlayerControllerImpl implements PlayerController{
	
	private static Player model;
	private static final PlayerUI view = new PlayerUI();
	
	public PlayerControllerImpl(Player p) {
		PlayerControllerImpl.model = p;
		view.setTournamentsHandler(() -> {
			return tournaments();
		}, id -> {
			return isSub(id);
		}, id -> {
			return subscribe(id);
		});
	}
	
	/**
	 * @param idAnnounce the id of the announce to search for
	 * the player subscription in
	 * @return true if the player is subscribed, false if not
	 */
	private boolean isSub(int idAnnounce) {
		return Player.DAO.isSubscribed(DBModel.getConnection(), model.getId(), idAnnounce);
	}
	
	/**
	 * @param idAnnounce the announce the player wants to subscribe to
	 * @return true if the player has been succesfully subscribed,
	 * false if an error occurred
	 */
	private boolean subscribe(int idAnnounce) {
		return Announce.DAO.subscription(DBModel.getConnection(), idAnnounce, model.getId());
		
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
