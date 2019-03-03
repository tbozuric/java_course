package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * This class is a class derived from {@link LocalizationProviderBridge}.In constructor it
 * registers itself as a {@link java.awt.event.WindowListener} to its {@link JFrame}; when frame is opened, it calls
 * {@link #connect()} and when
 * frame is closed, it calls {@link #disconnect()}.
 * <p>
 * When frame opens, {@link FormLocalizationProvider} object will register itself to decorated localization provider
 * automatically; when
 * {@link JFrame} closes, {@link FormLocalizationProvider} object will de-register itself from the decorated localization
 * provider automatically so that it
 * wont hold any reference to it and the garbage collector will be able to free frame and all of its resources
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {


    /**
     * Creates an instance of {@link FormLocalizationProvider}.
     *
     * @param provider the localization provider.
     * @param frame    the frame
     */
    public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
        super(provider);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                connect();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                disconnect();
            }
        });
    }
}
