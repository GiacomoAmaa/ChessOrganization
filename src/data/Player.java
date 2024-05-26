package data;

/**
 * A representation of a data instance of a player in the database
 */
public class Player {
	
	private static int id;
	private static int elo;
	private static String password;
	private static String cf;
	private static String name;
	private static String lastname;
	
	public Player(int id, int elo, String password, String cf, String name, String lastname) {
		Player.id = id;
		Player.elo = elo;
		Player.password = password;
		Player.cf = cf;
		Player.name = name;
		Player.lastname = lastname;
	}
	
	
	
	public static int getId() {
		return Player.id;
	}



	public static int getElo() {
		return Player.elo;
	}



	public static String getPassword() {
		return Player.password;
	}



	public static String getCf() {
		return Player.cf;
	}



	public static String getName() {
		return Player.name;
	}



	public static String getLastname() {
		return Player.lastname;
	}


/*
	@Override
	public boolean equals(Object other) {
		var p = (Player) other;
		return Player.id.equals(p);
		
	}
*/
}
