package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.*;

/**
 * This class represents a localized {@link JLabel}.
 */
public class LJLabel extends JLabel {

    /**
     * The default serial version UID.
     */
    private static final long serialVersionUID = 2619931903473356332L;

    /**
     * The key for translating {@link JLabel} text.
     */
    private String key;

    /**
     * The localization provider.
     */
    private ILocalizationProvider provider;

    /**
     * Creates an instance of {@link LJLabel}.
     *
     * @param key      the key for translating {@link JLabel} text.
     * @param provider the localization provider.
     */
    public LJLabel(String key, ILocalizationProvider provider) {
        this.key = key;
        this.provider = provider;
        this.provider.addLocalizationListener(this::updateLabel);
        updateLabel();

    }

    /**
     * Updates the text that is written on the label.
     */
    private void updateLabel() {
        String number = "";
        if (getText().contains(":")) {
            number = getText().substring(getText().indexOf(":"));
        }
        setText(provider.getString(key) + number);
    }
}
