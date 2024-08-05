package GUI;


import util.loaders.FontLoader;
import util.loaders.SoundManager;

public class App {
	public static final FontLoader FONT_LOADER = new FontLoader();
	public static final SoundManager SOUND = new SoundManager();

	public static void main(String[] args) {
		//var login = new LoginControllerImpl();
		var test = new PlayerUI();
	}
}
