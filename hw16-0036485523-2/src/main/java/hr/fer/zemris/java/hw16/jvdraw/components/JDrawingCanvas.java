package hr.fer.zemris.java.hw16.jvdraw.components;

import hr.fer.zemris.java.hw16.jvdraw.*;
import hr.fer.zemris.java.hw16.jvdraw.visitor.GeometricalObjectPainter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Represents a canvas on which {@link GeometricalObject} can be drawn.
 */
public class JDrawingCanvas extends JComponent implements MouseListener, MouseMotionListener, DrawingModelListener {

    /**
     *The default serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The drawing model.
     */
    private DrawingModel model;

    /**
     * The current {@link Tool}.
     */
    private Tool currentTool;

    /**
     * The {@link GeometricalObject} painter.
     */
    private GeometricalObjectVisitor painter;

    /**
     * Creates an instance of {@link JDrawingCanvas}.
     *
     * @param model       the drawing model.
     * @param currentTool the current {@link Tool}.
     */
    public JDrawingCanvas(DrawingModel model, Tool currentTool) {
        this.model = model;
        this.currentTool = currentTool;
        this.painter = new GeometricalObjectPainter(null);
        addMouseListener(this);
        addMouseMotionListener(this);
        model.addDrawingModelListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        currentTool.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        currentTool.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        currentTool.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void objectsAdded(DrawingModel source, int index0, int index1) {
        repaint();
    }

    @Override
    public void objectsRemoved(DrawingModel source, int index0, int index1) {
        repaint();
    }

    @Override
    public void objectsChanged(DrawingModel source, int index0, int index1) {
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        currentTool.mouseDragged(e);
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        currentTool.mouseMoved(e);
        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());

        int size = model.getSize();
        ((GeometricalObjectPainter) painter).setGraphics2D((Graphics2D) g);
        for (int i = 0; i < size; i++) {
            model.getObject(i).accept(painter);
        }
        currentTool.paint((Graphics2D) g);
    }

    /**
     * Sets the current tool.
     *
     * @param tool the tool.
     */
    public void setState(Tool tool) {
        currentTool = tool;
    }
}

