package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Represents the localization  provider. Object which are instances of classes that
 * implement this interface will be able to give us the translations for given keys.
 * Each {@link ILocalizationProvider} will be the Subject that will notify all registered
 * listeners when a selected language has changed.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Observer_pattern">Observer pattern</a>
 */
public interface ILocalizationProvider {

    /**
     * Adds the localization listener.
     *
     * @param l the localization listener.
     */
    void addLocalizationListener(ILocalizationListener l);

    /**
     * Removes the localization listener.
     *
     * @param l the localization listener.
     */
    void removeLocalizationListener(ILocalizationListener l);

    /**
     * Returns a translation that is associated with the given key. It takes a key and gives back the localization.
     *
     * @param key the key.
     * @return a translation that is associated with the given key.
     */
    String getString(String key);

    /**
     * Returns the current language.
     *
     * @return the current language.
     */
    String getCurrentLanguage();
}
