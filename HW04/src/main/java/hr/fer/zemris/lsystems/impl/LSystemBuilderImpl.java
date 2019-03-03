package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.*;
import hr.fer.zemris.math.Vector2D;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.pow;
import static java.lang.Math.toRadians;


import java.awt.*;

/**
 * Models objects that can be configured and then invokes the build() method.
 * which returns a specific Lindenmayer system according to the default configuration.
 */
public class LSystemBuilderImpl implements LSystemBuilder {

    /**
     * Pop command pattern.
     */
    private static final String POP_COMMAND = "pop";

    /**
     * Push command pattern.
     */
    private static final String PUSH_COMMAND = "push";

    /**
     * Space pattern.
     */
    private static final String SPACE = " ";

    private static final double ZERO_THRESHOLD = 1E-6;

    /**
     * Dictionary of registered commands.
     */
    private Dictionary registeredCommands;

    /**
     * Dictionary of registered actions.
     */
    private Dictionary registeredActions;

    /**
     * The point from which the turtle moves.
     */
    private Vector2D origin;

    /**
     * Angle to the positive axis-x direction in which the turtle is looking.
     */
    private double angle;

    /**
     * The size of the turtle's unit displacement.
     */
    private double unitLength;

    /**
     * Unit length scaler.
     */
    private double unitLengthDegreeScaler;

    /**
     * The color with which the turtle draws.
     */
    private Color color;

    /**
     * An axiom.
     */
    private String axiom;

    /**
     *
     */
    private SimplePatternConfig pattern;

    /**
     * Creates an instance of Lindenmayer's system with default values.
     */
    public LSystemBuilderImpl() {
        registeredCommands = new Dictionary();
        registeredActions = new Dictionary();
        unitLength = 0.1;
        unitLengthDegreeScaler = 1;
        origin = new Vector2D(0, 0);
        angle = 0;
        axiom = "";
        color = Color.BLACK;
        pattern = SimplePatternConfig.getInstance();

    }

    /**
     * Sets a unit length.
     *
     * @param v unit length.
     * @return this instance.
     */
    @Override
    public LSystemBuilder setUnitLength(double v) {
        unitLength = v;
        return this;
    }

    /**
     * Sets the point from which the turtle moves.
     *
     * @param v  x-coordinate.
     * @param v1 y- coordinate.
     * @return this instance.
     */
    @Override
    public LSystemBuilder setOrigin(double v, double v1) {
        origin = new Vector2D(v, v1);
        return this;
    }

    /**
     * Sets the angle to the positive axis-x direction in which the turtle is looking.
     *
     * @param v angle.
     * @return this instance.
     */
    @Override
    public LSystemBuilder setAngle(double v) {
        angle = v;
        return this;
    }

    /**
     * Sets the initial sequence(axiom).
     *
     * @param s axiom.
     * @return this instance
     */
    @Override
    public LSystemBuilder setAxiom(String s) {
        axiom = s.trim();
        return this;
    }

    /**
     * Sets the scaler value.
     *
     * @param v unit length degree scaler.
     * @return this instance.
     */
    @Override
    public LSystemBuilder setUnitLengthDegreeScaler(double v) {
        unitLengthDegreeScaler = v;
        return this;
    }

    /**
     * Adds a new production to the dictionary.
     *
     * @param c symbol.
     * @param s the sequences that replaces symbol.
     * @return this instance.
     */
    @Override
    public LSystemBuilder registerProduction(char c, String s) {
        if (registeredActions.get(c) != null) {
            throw new IllegalArgumentException("The configuration can only contain directives for different symbols");
        }
        registeredActions.put(c, s);
        return this;
    }

    /**
     * Adds a new command to the dictionary.
     *
     * @param c symbol.
     * @param s for the symbol defines the action that the turtle must make.
     * @return this instance.
     */
    @Override
    public LSystemBuilder registerCommand(char c, String s) {
        s = s.trim().replaceAll("\\s{2,}", SPACE);
        if (registerCommand(c, s, false)) {
            return this;
        }
        throw new IllegalArgumentException("Command is in invalid format!");
    }

    /**
     * Adds a new command to the command dictionary(registeredCommands) if it is correct.
     *
     * @param c              representing the command.
     * @param command        some command.
     * @param fromConfigFile whether the configuration of the object is from the configuration data.
     * @return whether the command has been successfully added to the dictionary.
     */
    private boolean registerCommand(char c, String command, boolean fromConfigFile) {
        if (fromConfigFile) {
            String[] elements = command.split(SPACE);
            if (elements.length < 3) {
                return false;
            }
            if (!elements[0].equals("command")) {
                return false;
            }
            c = elements[1].charAt(0);

            StringBuilder builder = new StringBuilder();
            for (int i = 2; i < elements.length; i++) {
                builder.append(elements[i]).append(SPACE);
            }
            command = builder.toString().trim();
        }

        Command cmd = null;
        if (pattern.getDrawCommand().matcher(command).matches()) {
            cmd = new DrawCommand(Double.parseDouble(command.split(SPACE)[1]));

        } else if (pattern.getSkipCommand().matcher(command).matches()) {
            cmd = new SkipCommand(Double.parseDouble(command.split(SPACE)[1]));

        } else if (pattern.getScaleCommand().matcher(command).matches()) {
            cmd = new ScaleCommand(Double.parseDouble(command.split(SPACE)[1]));

        } else if (pattern.getRotateCommand().matcher(command).matches()) {
            cmd = new RotateCommand(Double.parseDouble(command.split(SPACE)[1]));

        } else if (command.equals(PUSH_COMMAND)) {
            cmd = new PushCommand();

        } else if (command.equals(POP_COMMAND)) {
            cmd = new PopCommand();

        } else if (pattern.getColorCommand().matcher(command).matches()) {
            cmd = new ColorCommand(Color.decode("#" + command.split(SPACE)[1]));
        }

        if (cmd != null) {
            registeredCommands.put(c, cmd);
            return true;
        }

        return false;
    }

    /**
     * Initiates the unit length degree scaler.
     *
     * @param expression unit degree scaler directive.
     * @param parts      of unit degree scaler directive.
     */
    private void parseUnitDegreeScalerExpression(String expression, String[] parts) {
        double denominator;
        double numerator;
        if (parts.length == 4) {
            numerator = Double.parseDouble(parts[1]);
            denominator = Double.parseDouble(parts[3]);
        } else {
            String expr = expression.substring(expression.lastIndexOf('r') + 1);
            expr = expr.replaceAll(" ", "");
            numerator = Double.parseDouble(expr.substring(0, expr.indexOf("/")).trim());
            denominator = Double.parseDouble(expr.substring(expr.indexOf("/") + 1).trim());
        }
        if (isZero(denominator)) {
            throw new IllegalArgumentException("Denominator must not be equal to zero.");
        }
        unitLengthDegreeScaler = numerator / denominator;
    }

    /**
     * Checks if the given number is zero.
     *
     * @param number that we check.
     * @return whether the number is equal to zero
     */
    private static boolean isZero(double number) {
        return number >= -ZERO_THRESHOLD && number <= ZERO_THRESHOLD;
    }

    /**
     * Configuration based on a series of lines.
     *
     * @param strings each string contains one directive (or is empty).
     * @return this instance.
     */

    @Override
    public LSystemBuilder configureFromText(String[] strings) {

        for (String expression : strings) {
            if (expression.length() == 0) {
                continue;
            }
            expression = expression.trim().replaceAll("\\s+", SPACE);
            String[] parts = expression.split(SPACE);

            if (pattern.getOriginDirective().matcher(expression).matches()) {
                origin = new Vector2D(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));

            } else if (pattern.getAngleDirective().matcher(expression).matches()) {
                angle = Double.parseDouble(parts[1]);

            } else if (pattern.getUnitLengthDirective().matcher(expression).matches()) {
                unitLength = Double.parseDouble(parts[1]);

            } else if (pattern.getUnitLengthDegreeScalerFirstFormat().matcher(expression).matches()) {
                unitLengthDegreeScaler = Double.parseDouble(parts[1]);

            } else if (pattern.getUnitLengthDegreeScalerSecondFormat().matcher(expression).matches()) {
                parseUnitDegreeScalerExpression(expression, parts);

            } else if (registerCommand(' ', expression, true)) {
                continue;

            } else if (pattern.getAxiomDirective().matcher(expression).matches()) {
                axiom = parts[1];

            } else if (pattern.getProductionDirective().matcher(expression).matches()) {
                char c = parts[1].charAt(0);
                String production = parts[2];
                registeredActions.put(c, production);

            } else {
                throw new IllegalArgumentException("The directive \"" + expression + "\" is not valid.");
            }

        }
        return this;

    }

    /**
     * Creates an instance of Lindermayer system.
     *
     * @return a concrete Lindermayer system according to the default configuration.
     */
    @Override
    public LSystem build() {
        return new LSystemImpl();
    }

    /**
     * Represents Lindenmayer's system.
     */
    private class LSystemImpl implements LSystem {
        /**
         * Returns the string corresponding to the generated sequence after applying a certain number of productions.
         *
         * @param i level.
         * @return generated string after applying a certain number of productions.
         */
        @Override
        public String generate(int i) {
            if (i == 0) {
                return axiom;
            }
            String s = String.valueOf(axiom);
            StringBuilder sb = new StringBuilder("");
            for (int depth = 0; depth < i; depth++) {
                char[] termAsArray = s.toCharArray();
                for (char item : termAsArray) {
                    String production = (String) registeredActions.get(item);
                    if (production == null) {
                        sb.append(item);
                        continue;
                    }
                    sb.append(production);
                }
                s = sb.toString();
                sb.setLength(0);
            }
            return s;
        }

        /**
         * Draws the resultant fractal using the received line drawing object.
         *
         * @param i       depth.
         * @param painter line drawing object.
         */
        @Override
        public void draw(int i, Painter painter) {
            Context context = new Context();
            TurtleState turtleState = new TurtleState(origin, new Vector2D(cos(toRadians(angle)), sin(toRadians(angle))), color, unitLength * (pow(unitLengthDegreeScaler, i)));
            context.pushState(turtleState);
            String term = generate(i);

            char[] array = term.toCharArray();
            for (char item : array) {
                Command command = (Command) registeredCommands.get(item);
                if (command == null) {
                    continue;
                }
                command.execute(context, painter);
            }
        }
    }
}
