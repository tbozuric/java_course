package hr.fer.zemris.java.hw16.jvdraw.tools;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.geomobjects.FilledCircle;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * This class represents a line drawing tool.
 */
public class DrawFilledCircle extends AbstractDrawCircle {

    /**
     * Creates an instance of {@link DrawCircle}.
     *
     * @param model           the drawing model.
     * @param fgColorProvider the foreground color provider.
     * @param bgColorProvider the background color provider.
     */
    public DrawFilledCircle(DrawingModel model, IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
        super(model, fgColorProvider, bgColorProvider);
    }

    @Override
    public void paint(Graphics2D g2d) {
        if (counter != 0) {
            int borderRadius = radius + 2;
            g2d.setColor(fgColorProvider.getCurrentColor());
            g2d.fillOval(x - borderRadius / 2, y - borderRadius / 2, borderRadius, borderRadius);

            g2d.setColor(bgColorProvider.getCurrentColor());
            g2d.fillOval(x - radius / 2, y - radius / 2, radius, radius);
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        if (counter == 2) {
            radius = getRadius(e.getX(), e.getY(), x, y);
            model.add(new FilledCircle(x, y, radius, fgColorProvider.getCurrentColor(), bgColorProvider.getCurrentColor()));
            counter = 0;
        }
    }
}
