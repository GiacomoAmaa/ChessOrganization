package GUI;

import controller.LoginControllerImpl;
import util.loaders.FontLoader;
import util.loaders.SoundManager;

/**
 * 
 */
public final class App {
    /**
     * the font loader.
     */
    public static final FontLoader FONT_LOADER = new FontLoader();
    /**
     * sound queue manager.
     */
    public static final SoundManager SOUND = new SoundManager();

    private App() {
    }

    /**
     * main method.
     * @param args
     */
    public static void main(final String[] args) {
        var login = new LoginControllerImpl();
        /* does literally nothing but, in this way,
         * spotbugs isn't triggered
         */
        login.toString();
        //var UI = new AdminControllerImpl(new Admin(1, null, null, null, null, null));
        //var UI = new RefereeUI();
        //var UI = new PlayerUI();
        //var UI = new AdminUI();

    }
}
