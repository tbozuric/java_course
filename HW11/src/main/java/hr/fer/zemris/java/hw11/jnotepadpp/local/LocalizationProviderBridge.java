package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * This class is  a decorator for some other {@link ILocalizationProvider}.This class offers two additional methods:
 * {@link #connect()} and {@link #disconnect()} , and it
 * manages a connection status (so that you can not connect if you are already connected).
 * This class is {@link ILocalizationProvider} which, when asked to resolve a key delegates this request to wrapped
 * (decorated) {@link ILocalizationProvider} object.
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

    /**
     * Connection status.
     */
    private boolean connected;

    /**
     * The localization provider, decorated object.
     */
    private ILocalizationProvider parent;

    /**
     * The listener.
     */
    private ILocalizationListener listener;

    /**
     * Creates an instance of {@link LocalizationProviderBridge}.
     *
     * @param provider the localization provider.
     */
    public LocalizationProviderBridge(ILocalizationProvider provider) {
        this.parent = provider;
        listener = this::fire;
    }

    /**
     * Disconnects this object from decorated object.
     */
    public void disconnect() {
        if (connected) {
            connected = false;
            parent.removeLocalizationListener(listener);
        }
    }

    /**
     * Connects an instance of anonymous {@link ILocalizationListener} on the decorated object.
     */
    public void connect() {
        if (!connected) {
            parent.addLocalizationListener(listener);
            connected = true;
        }
    }

    @Override
    public String getString(String key) {
        return parent.getString(key);
    }

    @Override
    public String getCurrentLanguage() {
        return parent.getCurrentLanguage();
    }
}
