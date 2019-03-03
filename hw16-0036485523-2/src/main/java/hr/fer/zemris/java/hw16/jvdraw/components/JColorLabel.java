package hr.fer.zemris.java.hw16.jvdraw.components;

import hr.fer.zemris.java.hw16.jvdraw.ColorChangeListener;
import hr.fer.zemris.java.hw16.jvdraw.IColorProvider;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Represents a label to display the current background and foreground colors.
 */
public class JColorLabel extends JLabel implements ColorChangeListener {

    /**
     *The default serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The error message.
     */
    private static final String ERROR_MESSAGE = "Color provider must not be null";

    /**
     * The text that represents the foreground color.
     */
    private static final String FG_COLOR_TEXT = "Foreground color: ";

    /**
     * The text that represents the background color.
     */
    private static final String BG_COLOR_TEXT = "background color: ";

    /**
     * The RGB representation of foreground color.
     */
    private String fgRGBColor;

    /**
     * The RGB representation of background color.
     */
    private String bgRGBColor;

    /**
     * The foreground color provider.
     */
    private IColorProvider fgColorProvider;

    /**
     * The background color provider.
     */
    private IColorProvider bgColorProvider;

    /**
     * Creates an instance of {@link JColorLabel}.
     *
     * @param fgColorProvider the foreground color provider.
     * @param bgColorProvider the background color provider.
     */
    public JColorLabel(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
        Objects.requireNonNull(fgColorProvider, ERROR_MESSAGE);
        Objects.requireNonNull(bgColorProvider, ERROR_MESSAGE);

        this.fgColorProvider = fgColorProvider;
        this.bgColorProvider = bgColorProvider;

        fgColorProvider.addColorChangeListener(this);
        bgColorProvider.addColorChangeListener(this);

        fgRGBColor = getColor(FG_COLOR_TEXT, fgColorProvider.getCurrentColor());
        bgRGBColor = getColor(BG_COLOR_TEXT, bgColorProvider.getCurrentColor());
        setText(fgRGBColor + ", " + bgRGBColor);
    }

    @Override
    public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
        if (!oldColor.equals(newColor)) {
            if (source.equals(fgColorProvider)) {
                fgRGBColor = getColor(FG_COLOR_TEXT, newColor);
            } else if (source.equals(bgColorProvider)) {
                bgRGBColor = getColor(BG_COLOR_TEXT, newColor);
            }
            setText(fgRGBColor + ", " + bgRGBColor);
        }
    }

    /**
     * Returns the string representation of the color.
     *
     * @param color    the text associated with the color(foreground color / background color).
     * @param newColor the new color.
     * @return the string representation of the color.
     */
    private String getColor(String color, Color newColor) {
        return String.format(color + "(%d, %d, %d)", newColor.getRed(), newColor.getGreen(), newColor.getBlue());
    }
}
