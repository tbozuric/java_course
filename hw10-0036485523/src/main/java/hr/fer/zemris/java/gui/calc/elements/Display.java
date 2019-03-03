package hr.fer.zemris.java.gui.calc.elements;

import hr.fer.zemris.java.gui.calc.CalcModel;
import hr.fer.zemris.java.gui.calc.CalcValueListener;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the calculator display.
 * Default settings:
 * <code>
 * {@link #setOpaque(boolean)} : true
 * {@link #setBackground(Color)} :  RGB(253, 210 , 35)
 * {@link #setForeground(Color)} : {@link Color#BLACK}.
 * {@link #setHorizontalAlignment(int)} : {@link SwingConstants#RIGHT}.
 * </code>
 */
public class Display extends JLabel implements CalcValueListener {

    /**
     * The background color.
     */
    private static final Color BACKGROUND_COLOR = new Color(253, 210, 35);

    /**
     * Creates an instance of {@link Display}.
     *
     * @param text the text that will be displayed on the screen.
     */
    public Display(String text) {
        super(text);
        initDisplay();
    }

    /**
     * Sets default screen graphics settings.
     */
    private void initDisplay() {
        setOpaque(true);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBackground(BACKGROUND_COLOR);
        setForeground(Color.BLACK);
        setHorizontalAlignment(SwingConstants.RIGHT);
        setFont(new Font("default", Font.BOLD, 16));
    }


    @Override
    public void valueChanged(CalcModel model) {
        setText(model.toString());
    }
}
