package hr.fer.zemris.java.hw16.jvdraw.tools;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.Tool;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.Line;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Represents a line drawing tool.
 */
public class DrawLine extends AbstractDrawTool implements Tool {

    /**
     * The start x, start y , end x and end y coordinates of the line.
     */
    private int startX, startY, endX, endY;

    /**
     * The number of clicks.
     */
    private int counter = 0;

    /**
     * Creates an instance of {@link DrawCircle}.
     *
     * @param model           the drawing model.
     * @param fgColorProvider the foreground color provider.
     * @param bgColorProvider the background color provider.
     */
    public DrawLine(DrawingModel model, IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
        super(model, fgColorProvider, bgColorProvider);
    }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (counter == 0) {
            startX = e.getX();
            startY = e.getY();
            counter++;
            return;
        }
        endX = e.getX();
        endY = e.getY();
        model.add(new Line(startX, startY, endX, endY, fgColorProvider.getCurrentColor()));
        counter = 0;

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (counter != 0) {
            endX = e.getX();
            endY = e.getY();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) { }

    @Override
    public void paint(Graphics2D g2d) {
        if (counter != 0) {
            g2d.setColor(fgColorProvider.getCurrentColor());
            g2d.drawLine(startX, startY, endX, endY);
        }
    }
}
