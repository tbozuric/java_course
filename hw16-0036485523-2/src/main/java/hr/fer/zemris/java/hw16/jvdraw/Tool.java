package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * This interface represents {@link Tool}.
 */
public interface Tool {

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    void mousePressed(MouseEvent e);

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    void mouseReleased(MouseEvent e);

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     */
    void mouseClicked(MouseEvent e);

    /**
     * Invoked when the mouse cursor has been moved onto a component
     * but no buttons have been pushed.
     *
     * @param e the event to be processed
     */
    void mouseMoved(MouseEvent e);

    /**
     * Invoked when a mouse button is pressed on a component and then
     * dragged.
     *
     * @param e the event to be processed
     */
    void mouseDragged(MouseEvent e);

    /**
     * Invoked when to draw an object.
     *
     * @param g2d the graphics.
     */
    void paint(Graphics2D g2d);
}
