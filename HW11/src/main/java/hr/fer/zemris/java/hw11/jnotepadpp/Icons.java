package hr.fer.zemris.java.hw11.jnotepadpp;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Auxiliary class to load the icons.
 */
public class Icons {

    /**
     * The icon size.
     */
    private static final int ICON_SIZE = 30;

    /**
     * The image name that represents the unmodified icon.
     */
    private static final String UNMODIFIED = "blueDisk.png";

    /**
     * The image name that represents the modified icon.
     */
    private static final String MODIFIED = "redDisk.png";

    /**
     * The location of icons.
     */
    private static final String ICONS_LOCATION = "/hr/fer/zemris/java/hw11/jnotepadpp/icons/";

    /**
     * A map that has an icon for each key.
     */
    private Map<String, Icon> icons;

    /**
     * The instance.
     */
    private static Icons instance;

    /**
     * Creates an instance of {@link Icons}.
     */
    private Icons() {
        icons = new HashMap<>();
        init();
    }

    /**
     * The {@link Icons} instance.
     *
     * @return the instance.
     */
    public static Icons getInstance() {
        if (instance == null) {
            instance = new Icons();
        }
        return instance;
    }

    /**
     * Auxiliary method used to link the keys to their icons.
     */
    private void init() {
        icons.put("unmodified", resizeImage(UNMODIFIED));
        icons.put("modified", resizeImage(MODIFIED));
        icons.put("close", resizeImage("close.png"));
        icons.put("copy", resizeImage("copy.png"));
        icons.put("createBlank", resizeImage("create_blank.png"));
        icons.put("cut", resizeImage("cut.png"));
        icons.put("paste", resizeImage("paste.png"));
        icons.put("exit", resizeImage("exit.png"));
        icons.put("open", resizeImage("open.png"));
        icons.put("save", resizeImage("save.png"));
        icons.put("saveAs", resizeImage("save_as.png"));
        icons.put("statistics", resizeImage("statistical_info.png"));
        icons.put("englishDescription", resizeImage("english.png"));
        icons.put("croatianDescription", resizeImage("croatian.png"));
        icons.put("germanDescription", resizeImage("german.png"));

    }

    /**
     * This method is used to change the image size.
     *
     * @param path the path to the image.
     * @return resized icon.
     */
    public Icon resizeImage(String path) {
        ImageIcon icon = loadImage(ICONS_LOCATION + path);
        Image image = icon.getImage();
        image = image.getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH);
        icon = new ImageIcon(image);
        return icon;
    }

    /**
     * Loads the image from disk and returns {@link ImageIcon}.
     *
     * @param path the path to the image.
     * @return the {@link ImageIcon}.
     */
    public ImageIcon loadImage(String path) {
        InputStream is = this.getClass().getResourceAsStream(path);
        if (is == null) {
            throw new IllegalArgumentException("Image does not exist.");
        }
        byte[] bytes = new byte[0];
        try {
            bytes = is.readAllBytes();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ImageIcon(bytes);
    }

    /**
     * Returns the icon associated with the given name.
     *
     * @param name the name of icon.
     * @return the icon associated with the given name.
     * @throws NullPointerException if the name is a null reference.
     */
    public Icon getIcon(String name) {
        return icons.get(Objects.requireNonNull(name));
    }

}
