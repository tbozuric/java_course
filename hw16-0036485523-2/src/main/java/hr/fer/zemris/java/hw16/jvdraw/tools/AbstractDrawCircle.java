package hr.fer.zemris.java.hw16.jvdraw.tools;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.Tool;

import java.awt.event.MouseEvent;

/**
 * Represents abstract draw circle tool.
 */
public abstract class AbstractDrawCircle extends AbstractDrawTool implements Tool {

    /**
     * The coordinates of the center of the circle and radius.
     */
    int x, y, radius;

    /**
     * The number of clicks.
     */
    int counter = 0;

    /**
     * Creates an instance of {@link AbstractDrawCircle}.
     *
     * @param model           the drawing model.
     * @param fgColorProvider the foreground color provider.
     * @param bgColorProvider the background color provider.
     */
    public AbstractDrawCircle(DrawingModel model, IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
        super(model, fgColorProvider, bgColorProvider);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (counter != 0) {
            radius = getRadius(e.getX(), e.getY(), x, y);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (counter == 0) {
            x = e.getX();
            y = e.getY();
            radius = 0;
            counter++;
            return;
        }
        if (counter == 1) {
            counter++;
        }
    }

    /**
     * Returns the radius calculated from the given points.
     *
     * @param x1 x1 coordinate.
     * @param y1 y1 coordinate.
     * @param x2 x2 coordinate.
     * @param y2 y2 coordinate.
     * @return the radius calculated from the given points.
     */
    protected int getRadius(int x1, int y1, int x2, int y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return 2 * (int) Math.sqrt(dx * dx + dy * dy);
    }
}
