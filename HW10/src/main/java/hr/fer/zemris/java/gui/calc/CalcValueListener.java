package hr.fer.zemris.java.gui.calc;

/**
 * An interface that serves to inform about the calculator value change.
 */
public interface CalcValueListener {

    /**
     * This method is called when the model value changes.
     *
     * @param model calculator model.
     */
    void valueChanged(CalcModel model);
}