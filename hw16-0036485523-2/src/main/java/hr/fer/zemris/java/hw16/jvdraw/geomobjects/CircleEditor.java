package hr.fer.zemris.java.hw16.jvdraw.geomobjects;

import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.components.JColorArea;

import javax.swing.*;
import java.awt.*;

/**
 * Represent the circle editor.
 */
public class CircleEditor extends GeometricalObjectEditor {

    /**
     *The default serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The circle.
     */
    private Circle circle;

    /**
     * Represents a spinner for editing x coordinate of the {@link Circle}.
     */
    private SpinnerNumberModel modelX;

    /**
     * Represents a spinner for editing y coordinate of the {@link Circle}.
     */
    private SpinnerNumberModel modelY;

    /**
     * Represents a spinner for editing radius of the {@link Circle}.
     */
    private SpinnerNumberModel radius;

    /**
     * Represents color area for changing the color of the circle.
     */
    private JColorArea changeColor;

    /**
     * Creates an instance of {@link CircleEditor}.
     *
     * @param circle the circle.
     */
    public CircleEditor(Circle circle) {
        this.circle = circle;
        constructDialog();

    }

    @Override
    public void checkEditing() {
        //already checked in dialog, see SpinnerNumberModel
    }

    @Override
    public void acceptEditing() {
        circle.setX(modelX.getNumber().intValue());
        circle.setY(modelY.getNumber().intValue());
        circle.setRadius(radius.getNumber().intValue());
        circle.setColor(changeColor.getCurrentColor());
        circle.notifyListeners();
    }

    /**
     * Constructs a {@link Circle} edit dialog.
     */
    private void constructDialog() {
        setLayout(new GridLayout(0, 2));

        //effectively we can make the coordinates free, without limitation
        modelX = new SpinnerNumberModel(circle.getX(), 0, Integer.MAX_VALUE, 1);
        modelY = new SpinnerNumberModel(circle.getY(), 0, Integer.MAX_VALUE, 1);
        radius = new SpinnerNumberModel(circle.getRadius(), 0, Integer.MAX_VALUE, 1);
        changeColor = new JColorArea(circle.getColor(), this, "Change color");

        add(new JLabel("Change x coordinate"));
        add(new JSpinner(modelX));
        add(new JLabel("Change y coordinate"));
        add(new JSpinner(modelY));
        add(new JLabel("Change radius"));
        add(new JSpinner(radius));
        add(new JLabel("Change color"));
        add(changeColor);
    }
}
