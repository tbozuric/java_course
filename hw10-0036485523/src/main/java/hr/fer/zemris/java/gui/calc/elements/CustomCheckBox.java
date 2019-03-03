package hr.fer.zemris.java.gui.calc.elements;

import javax.swing.*;
import java.awt.*;

/**
 * Represents a custom check box with default graphics settings.
 * Default settings:
 * <code>
 * {@link #setOpaque(boolean)} : true
 * {@link #setBackground(Color)} : RGB(124, 151 , 204)
 * {@link #setForeground(Color)} : {@link Color#BLACK}.
 * </code>
 */
public class CustomCheckBox extends JCheckBox {

    /**
     * The background color.
     */
    private static final Color BACKGROUND_COLOR = new Color(124, 151, 204);

    /**
     * Creates an instance of {@link CustomBasicButton}.
     *
     * @param text the text that will be displayed next to the check box.
     */
    public CustomCheckBox(String text) {
        super(text);
        initCheckBox();
    }


    /**
     * Sets default check box graphics settings.
     */
    private void initCheckBox() {
        setOpaque(true);
        setBackground(BACKGROUND_COLOR);
        setForeground(Color.BLACK);
    }
}
