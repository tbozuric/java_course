package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Represents a concrete localization provider that is singleton.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Singleton_pattern">Singleton design pattern</a>
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

    /**
     * The current language.
     */
    private String language;

    /**
     * The resource bundle for the current language.
     */
    private ResourceBundle bundle;

    /**
     * The localization provider instance.
     */
    private static LocalizationProvider localizationProvider = new LocalizationProvider();

    /**
     * Creates an instance of {@link LocalizationProvider} and sets the language to “en” by default.
     */
    private LocalizationProvider() {
        language = "en";
        bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.translates", Locale.forLanguageTag(language));
    }

    /**
     * Returns the instance of {@link LocalizationProvider}.
     *
     * @return the instance of {@link LocalizationProvider}.
     */
    public static LocalizationProvider getInstance() {
        return localizationProvider;
    }

    @Override
    public String getString(String key) {
        return bundle.getString(key);
    }

    @Override
    public String getCurrentLanguage() {
        return language;
    }

    /**
     * Sets the language to the given language.
     *
     * @param language the desired language.
     */
    public void setLanguage(String language) {
        if (!language.equals(this.language)) {
            this.language = language;
            bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.translates", Locale.forLanguageTag(language));
            fire();
        }
    }
}
