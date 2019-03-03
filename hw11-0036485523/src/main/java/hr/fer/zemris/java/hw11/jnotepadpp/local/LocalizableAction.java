package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Objects;

/**
 * This class represents a localized {@link Action}.
 */
public class LocalizableAction extends AbstractAction {

    /**
     * The default serial version UID.
     */
    private static final long serialVersionUID = -2674378332234754571L;

    /**
     * The key for translating action name.
     */
    private String keyName;

    /**
     * The key for translating action description.
     */
    private String keyDescription;

    /**
     * The key for "translating" mnemonic.
     */
    private String keyMnemonic;

    /**
     * The localization provider.
     */
    private ILocalizationProvider provider;

    /**
     * The listener.
     */
    private ILocalizationListener listener;


    /**
     * Creates an instance of {@link LocalizableAction}.
     *
     * @param keyName        the key for translating action name.
     * @param keyDescription the key for translating action description.
     * @param keyMnemonic    the key for "translating" mnemonic
     * @param lp             the localization provider.
     */
    public LocalizableAction(String keyName, String keyDescription, String keyMnemonic, ILocalizationProvider lp) {
        this.keyName = Objects.requireNonNull(keyName);
        this.keyDescription = Objects.requireNonNull(keyDescription);
        this.keyMnemonic = keyMnemonic;
        this.provider = lp;
        listener = this::updateNameAndDescriptionAndMnemonic;
        provider.addLocalizationListener(listener);
        updateNameAndDescriptionAndMnemonic();
    }

    /**
     * Creates an instance of {@link LocalizableAction}.
     *
     * @param keyName the key for translating action name.
     * @param lp      the localization provider.
     */
    public LocalizableAction(String keyName, ILocalizationProvider lp) {
        this.keyName = Objects.requireNonNull(keyName);
        this.provider = lp;
        listener = this::updateName;
        provider.addLocalizationListener(listener);
        updateName();
    }

    /**
     * Updates the action name.
     */
    private void updateName() {
        this.putValue(Action.NAME, provider.getString(keyName));
    }

    /**
     * Updates the action name and short description.
     */
    private void updateNameAndDescriptionAndMnemonic() {
        this.putValue(Action.NAME, provider.getString(keyName));
        this.putValue(Action.SHORT_DESCRIPTION, provider.getString(keyDescription));
        this.putValue(Action.MNEMONIC_KEY, KeyEvent.getExtendedKeyCodeForChar(provider.getString(keyMnemonic).charAt(0)));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
