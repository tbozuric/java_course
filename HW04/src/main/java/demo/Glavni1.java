package demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * A simple program that checks the correctness of Lindermayer system..
 */
public class Glavni1 {

    /**
     * Method invoked when running the program.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        LSystemViewer.showLSystem(createKochCurve(LSystemBuilderImpl::new));
    }

    /**
     * Creates a koch curve from direct commands.
     *
     * @param provider modeling objects that can create configuration objects.
     * @return configured Lindermayer's system.
     */
    private static LSystem createKochCurve(LSystemBuilderProvider provider) {
        return provider.createLSystemBuilder()
                .registerCommand('F', "draw 1")
                .registerCommand('+', "rotate 60")
                .registerCommand('-', "rotate -60")
                .setOrigin(0.05, 0.4)
                .setAngle(0)
                .setUnitLength(0.9)
                .setUnitLengthDegreeScaler(1.0 / 3.0)
                .registerProduction('F', "F+F--F+F")
                .setAxiom("F")
                .build();
    }

}
