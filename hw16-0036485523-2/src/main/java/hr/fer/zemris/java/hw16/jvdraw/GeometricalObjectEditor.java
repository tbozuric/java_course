package hr.fer.zemris.java.hw16.jvdraw;

import javax.swing.*;

/**
 * This class represents editor of {@link GeometricalObject}.
 */
public abstract class GeometricalObjectEditor extends JPanel {

    /**
     * The default serial version UID.
     */
    private static final long serialVersionUID = -69260126098089997L;

    /**
     * Checks if fields are correctly filled.
     */
    public abstract void checkEditing();

    /**
     * Values from all fields writes back into given {@link GeometricalObject}.
     */
    public abstract void acceptEditing();
}
