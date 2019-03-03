package hr.fer.zemris.java.hw16.jvdraw.geomobjects;

import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.components.JColorArea;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the {@link FilledCircle} editor.
 */
public class FilledCircleEditor extends GeometricalObjectEditor {

    /**
     *The default serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * +
     * The filled circle.
     */
    private FilledCircle filledCircle;

    /**
     * Represents a spinner for editing x coordinate of the {@link FilledCircle}.
     */
    private SpinnerNumberModel modelX;

    /**
     * Represents a spinner for editing y coordinate of the {@link FilledCircle}.
     */
    private SpinnerNumberModel modelY;

    /**
     * Represents a spinner for editing radius of the {@link FilledCircle}.
     */
    private SpinnerNumberModel radius;

    /**
     * Represents color area ("button") for changing the border color of the filled circle.
     */
    private JColorArea changeBorderColor;

    /**
     * Represents color area ("button") for changing the area color of the filled circle.
     */
    private JColorArea changeAreaColor;

    /**
     * Creates an instance of {@link FilledCircleEditor}.
     *
     * @param filledCircle the filled circle.
     */
    public FilledCircleEditor(FilledCircle filledCircle) {
        this.filledCircle = filledCircle;
        constructDialog();

    }

    @Override
    public void checkEditing() {
        //already checked , see SpinnerNumberModel
    }

    @Override
    public void acceptEditing() {
        filledCircle.setX(modelX.getNumber().intValue());
        filledCircle.setY(modelY.getNumber().intValue());
        filledCircle.setRadius(radius.getNumber().intValue());
        filledCircle.setFgColor(changeBorderColor.getCurrentColor());
        filledCircle.setBgColor(changeAreaColor.getCurrentColor());
        filledCircle.notifyListeners();
    }

    /**
     * Constructs a {@link FilledCircle} edit dialog.
     */
    private void constructDialog() {
        setLayout(new GridLayout(0, 2));

        //effectively we can make the coordinates free, without limitation
        modelX = new SpinnerNumberModel(filledCircle.getX(), 0, Integer.MAX_VALUE, 1);
        modelY = new SpinnerNumberModel(filledCircle.getY(), 0, Integer.MAX_VALUE, 1);
        radius = new SpinnerNumberModel(filledCircle.getRadius(), 0, Integer.MAX_VALUE, 1);
        changeBorderColor = new JColorArea(filledCircle.getFgColor(), this, "Change border color");
        changeAreaColor = new JColorArea(filledCircle.getBgColor(), this, "Change area color");

        add(new JLabel("Change x coordinate"));
        add(new JSpinner(modelX));
        add(new JLabel("Change y coordinate"));
        add(new JSpinner(modelY));
        add(new JLabel("Change radius"));
        add(new JSpinner(radius));
        add(new JLabel("Change border color"));
        add(changeBorderColor);
        add(new JLabel("Change area color"));
        add(changeAreaColor);
    }
}
