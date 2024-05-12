package util.loaders;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

/**
 * {@link FontLoader} class is used to load and gain access to all the game's different fonts.
 */
public class FontLoader {
    private static final String SEPARATOR = System.getProperty("file.separator");
    /**
     * absolute path of the directory containing all game's fonts.
     */
    public static final String DEFAULT_DIR = System.getProperty("user.dir") + SEPARATOR
            + "resources" + SEPARATOR + "fonts" + SEPARATOR;
    private final Font titleFont;
    private final Font textFont;

    /**
     * Loads and initializes all the game's different {@link Font}.
     */
    public FontLoader() {
        this.titleFont = this.loadFont(DEFAULT_DIR + "titleFont.otf");
        this.textFont = this.loadFont(DEFAULT_DIR + "textFont.ttf");
    }

    private Font loadFont(final String name) {
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File(name));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        return font;
    }

    /**
     * @return the default title's font
     */
    public Font getTitleFont() {
        return titleFont;
    }

    /**
     * @return the default text's font
     */
    public Font getTextFont() {
        return textFont;
    }

}
