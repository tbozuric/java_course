package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements {@link ILocalizationProvider}  and adds it the ability to register, de-register and inform
 * (fire() method) listeners.
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

    /**
     * List of observers listening to change localization.
     */
    private List<ILocalizationListener> listeners;

    /**
     * Initiates a list of observers.
     */
    public AbstractLocalizationProvider() {
        listeners = new ArrayList<>();
    }

    @Override
    public void addLocalizationListener(ILocalizationListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    @Override
    public void removeLocalizationListener(ILocalizationListener l) {
        if (listeners.contains(l)) {
            listeners.remove(l);
        }
    }

    /**
     * Informs the observers that the localization has been changed.
     */
    public void fire() {
        for (ILocalizationListener listener : listeners) {
            listener.localizationChanged();
        }
    }
}
