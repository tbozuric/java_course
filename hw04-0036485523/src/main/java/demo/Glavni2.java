package demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * A simple program that checks the correctness of Lindermayer system..
 */
public class Glavni2 {
    /**
     * Method invoked when running the program.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        LSystemViewer.showLSystem(createKochCurve2(LSystemBuilderImpl::new));

    }

    /**
     * Creates a koch curve from config file.
     *
     * @param provider modeling objects that can create configuration objects.
     * @return configured Lindermayer's system.
     */
    private static LSystem createKochCurve2(LSystemBuilderProvider provider) {
        String[] data = new String[]{
                "origin 0.05 0.4",
                "angle 0",
                "unitLength 0.9",
                "unitLengthDegreeScaler 1.0 / 3.0",
                "",
                "command F draw 1",
                "command + rotate 60",
                "command - rotate -60",
                "",
                "axiom F",
                "",
                "production F F+F--F+F"
        };
        return provider.createLSystemBuilder().configureFromText(data).build();
    }
}
