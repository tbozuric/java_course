package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.*;

/**
 * Represents an color change listener.
 */
public interface ColorChangeListener {
    /**
     * The method that triggers when the color changes.
     *
     * @param source   the color provider.
     * @param oldColor the old color.
     * @param newColor the new color.
     */
    void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
