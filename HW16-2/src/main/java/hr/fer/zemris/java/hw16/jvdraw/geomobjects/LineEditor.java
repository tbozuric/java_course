package hr.fer.zemris.java.hw16.jvdraw.geomobjects;

import hr.fer.zemris.java.hw16.jvdraw.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.components.JColorArea;

import javax.swing.*;
import java.awt.*;

/**
 *Represents a {@link Line} editor.
 */
public class LineEditor extends GeometricalObjectEditor {

    /**
     *The default serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The line.
     */
    private Line line;

    /**
     * Represents a spinner for editing  start x coordinate of the {@link Line}.
     */
    private SpinnerNumberModel modelStartX;

    /**
     * Represents a spinner for editing  start y coordinate of the {@link Line}.
     */
    private SpinnerNumberModel modelStartY;

    /**
     * Represents a spinner for editing  end x coordinate of the {@link Line}.
     */
    private SpinnerNumberModel modelEndX;

    /**
     * Represents a spinner for editing  end y coordinate of the {@link Line}.
     */
    private SpinnerNumberModel modelEndY;

    /**
     * Represents color area ("button") for changing the color of the {@link Line}.
     */
    private JColorArea changeColor;

    /**
     *Creates an instance of {@link LineEditor}
     * @param line the line.
     */
    public LineEditor(Line line ){
        this.line = line;
        constructDialog();
    }

    @Override
    public void checkEditing() {
        //already checked, see SpinnerNumberModel
    }

    @Override
    public void acceptEditing() {
        line.setStartX(modelStartX.getNumber().intValue());
        line.setStartY(modelStartY.getNumber().intValue());
        line.setEndX(modelEndX.getNumber().intValue());
        line.setEndY(modelEndY.getNumber().intValue());
        line.setColor(changeColor.getCurrentColor());
        line.notifyListeners();
    }

    /**
     * Constructs a {@link Line} edit dialog.
     */
    private void constructDialog(){
        setLayout(new GridLayout(0, 2));

        //effectively we can make the coordinates free, without limitation
        modelStartX = new SpinnerNumberModel(line.getStartX(), 0, Integer.MAX_VALUE, 1);
        modelStartY = new SpinnerNumberModel(line.getStartY(), 0, Integer.MAX_VALUE, 1);
        modelEndX = new SpinnerNumberModel(line.getEndX(), 0, Integer.MAX_VALUE, 1);
        modelEndY = new SpinnerNumberModel(line.getEndY(), 0, Integer.MAX_VALUE, 1);
        changeColor = new JColorArea(line.getColor(), this, "Change color");

        add(new JLabel("Change start x coordinate"));
        add(new JSpinner(modelStartX));
        add(new JLabel("Change start y coordinate"));
        add(new JSpinner(modelStartY));

        add(new JLabel("Change end x coordinate"));
        add(new JSpinner(modelEndX));
        add(new JLabel("Change end y coordinate"));
        add(new JSpinner(modelEndY));

        add(new JLabel("Change color"));
        add(changeColor);

    }
}
