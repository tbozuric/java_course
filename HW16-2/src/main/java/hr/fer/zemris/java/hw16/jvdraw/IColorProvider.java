package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.*;

/**
 * This interface represents color provider.
 */
public interface IColorProvider {

    /**
     * Returns the current color.
     *
     * @return the current color.
     */
    Color getCurrentColor();

    /**
     * Adds a new {@link ColorChangeListener}.
     *
     * @param l the color change listener.
     */
    void addColorChangeListener(ColorChangeListener l);

    /**
     * Removes the {@link ColorChangeListener}.
     *
     * @param l the color change listener.
     */
    void removeColorChangeListener(ColorChangeListener l);
}
