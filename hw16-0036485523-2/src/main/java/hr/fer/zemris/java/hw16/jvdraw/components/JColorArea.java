package hr.fer.zemris.java.hw16.jvdraw.components;

import hr.fer.zemris.java.hw16.jvdraw.ColorChangeListener;
import hr.fer.zemris.java.hw16.jvdraw.IColorProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a square-shaped component used to select a color.
 */
public class JColorArea extends JComponent implements IColorProvider, MouseListener {

    /**
     *The default serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The  width of the component.
     */
    private static final int WIDTH = 15;

    /**
     * The height of the component.
     */
    private static final int HEIGHT = 15;

    /**
     * The currently selected color.
     */
    private Color selectedColor;

    /**
     * The list of listener to change color.
     */
    private List<ColorChangeListener> listeners;

    /**
     * The main frame.
     */
    private Component frame;

    /**
     * The message.
     */
    private String message;

    /**
     * Creates an instance of {@link JColorArea}
     *
     * @param selectedColor the selected color.
     * @param frame         the frame.
     * @param message       the message.
     */
    public JColorArea(Color selectedColor, Component frame, String message) {
        this.selectedColor = selectedColor;
        this.frame = frame;
        this.message = message;
        addMouseListener(this);
        listeners = new ArrayList<>();

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    @Override
    public Color getCurrentColor() {
        return selectedColor;
    }

    @Override
    public void addColorChangeListener(ColorChangeListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    @Override
    public void removeColorChangeListener(ColorChangeListener l) {
        if (listeners.contains(l)) {
            listeners.remove(l);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(selectedColor);
        g.fillRect(0, 0, getPreferredSize().width, getPreferredSize().height);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Color color = JColorChooser.showDialog(frame, message, selectedColor);
        if(color != null){
            setSelectedColor(color);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    /**
     * Sets the currently selected color to the given color.
     *
     * @param newColor the new color.
     */
    public void setSelectedColor(Color newColor) {
        if (!selectedColor.equals(newColor)) {
            Color oldColor = selectedColor;
            this.selectedColor = newColor;
            notifyListeners(oldColor);
            repaint();
        }
    }

    /**
     * Informs the listener that the color has changed.
     *
     * @param oldColor the old color.
     */
    public void notifyListeners(Color oldColor) {
        for (ColorChangeListener listener : listeners) {
            listener.newColorSelected(this, oldColor, selectedColor);
        }
    }
}
