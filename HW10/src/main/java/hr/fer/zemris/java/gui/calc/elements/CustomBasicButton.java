package hr.fer.zemris.java.gui.calc.elements;

import hr.fer.zemris.java.gui.calc.CalcModel;

import javax.swing.*;
import java.awt.*;

/**
 * Represents a custom button with default graphics settings.
 * Default settings:
 * <code>
 * {@link #setOpaque(boolean)} : true
 * {@link #setBackground(Color)} : RGB(124, 151 , 204)
 * {@link #setForeground(Color)} : {@link Color#BLACK}.
 * </code>
 */
public class CustomBasicButton extends JButton {

    /**
     * The background color.
     */
    private static final Color BACKGROUND_COLOR = new Color(124, 151, 204);
    /**
     * Reference to {@link CalcModel}.
     */
    private CalcModel model;

    /**
     * Creates an instance of {@link CustomBasicButton}.
     *
     * @param text  the text of the button.
     * @param model reference to {@link CalcModel}
     */
    public CustomBasicButton(String text, CalcModel model) {
        super(text);
        this.model = model;
        initButton();
    }

    /**
     * Sets default button graphics settings.
     */
    private void initButton() {
        setOpaque(true);
        setBackground(BACKGROUND_COLOR);
        setForeground(Color.BLACK);
        setFont(new Font("default", Font.BOLD, 16));

    }
}
