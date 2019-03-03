package hr.fer.zemris.lsystems.impl;

import java.util.regex.Pattern;

/**
 * Represents the configuration of all patterns in our program.
 * Class is primarily used to make pattern compile only once, at the beginning of the program.
 */
public class SimplePatternConfig {

    /**
     * Scale command pattern.
     */
    private static final String COLOR_COMMAND = "color [0-9a-fA-F]{6}";

    /**
     * Rotate command pattern.
     */
    private static final String ROTATE_COMMAND = "rotate [-]?[0-9]+(\\.[0-9][0-9]*)?";

    /**
     * Scale command pattern.
     */
    private static final String SCALE_COMMAND = "scale [0-9]+(\\.[0-9][0-9]*)?";

    /**
     * Skip command pattern.
     */
    private static final String SKIP_COMMAND = "skip [0-9]+(\\.[0-9][0-9]*)?";

    /**
     * Draw command pattern.
     */
    private static final String DRAW_COMMAND = "draw [0-9]+(\\.[0-9][0-9]*)?";

    /**
     * Origin directive pattern.
     */
    private static final String ORIGIN_DIRECTIVE = "origin ([0-9]+(\\.[0-9][0-9]*)?) ([0-9]+(\\.[0-9][0-9]*)?)";

    /**
     * Angle directive pattern.
     */
    private static final String ANGLE_DIRECTIVE = "angle -?[0-9]+(\\.[0-9][0-9]*)?";

    /**
     * Unit length pattern.
     */
    private static final String UNIT_LENGTH_DIRECTIVE = "unitLength [0-9]+(\\.[0-9][0-9]*)?";

    /**
     * Unit degree scaler first format pattern.
     */
    private static final String UNIT_LENGTH_DEGREE_SCALER_FIRST_FORMAT = "unitLengthDegreeScaler ([0-9]+(\\.[0-9][0-9]*)?)";

    /**
     * Unit length degree scaler second format pattern.
     */
    private static final String UNIT_LENGTH_DEGREE_SCALER_SECOND_FORMAT = "unitLengthDegreeScaler [0-9]+(\\.[0-9][0-9]*)?( )?/( )?[0-9]+(\\.[0-9][0-9]*)?";

    /**
     * Axiom directive pattern.
     */
    private static final String AXIOM_DIRECTIVE = "axiom .+";

    /**
     * Production directive pattern.
     */
    private static final String PRODUCTION_DIRECTIVE = "production [A-Z] (.*)";

    /**
     * Pattern that we use to find the correct color commands.
     */
    private static Pattern colorCommand;
    /**
     * Pattern that we use to find the correct rotate commands.
     */
    private static Pattern rotateCommand;
    /**
     * Pattern that we use to find the correct scale commands.
     */
    private static Pattern scaleCommand;
    /**
     * Pattern that we use to find the correct skip commands.
     */
    private static Pattern skipCommand;
    /**
     * Pattern that we use to find the correct draw commands.
     */
    private static Pattern drawCommand;
    /**
     * Pattern that we use to find the correct origin directives.
     */
    private static Pattern originDirective;
    /**
     * Pattern that we use to find the correct angle directives.
     */
    private static Pattern angleDirective;
    /**
     * Pattern that we use to find the correct unit length directives.
     */
    private static Pattern unitLengthDirective;
    /**
     * Pattern that we use to find the correct unit length degree scaler directives.
     */
    private static Pattern unitLengthDegreeScalerFirstFormat;
    /**
     * Pattern that we use to find the correct unit length degree scaler directives..
     */
    private static Pattern unitLengthDegreeScalerSecondFormat;
    /**
     * Pattern that we use to find the correct axiom directives.
     */
    private static Pattern axiomDirective;
    /**
     * Pattern that we use to find the correct production directives.
     */
    private static Pattern productionDirective;

    /**
     * Reference to a instance of this class. (SINGELTON)
     */
    private static SimplePatternConfig simplePatternConfig;

    /**
     * Configures all patterns.
     */
    private SimplePatternConfig() {
        colorCommand = Pattern.compile(COLOR_COMMAND);
        rotateCommand = Pattern.compile(ROTATE_COMMAND);
        scaleCommand = Pattern.compile(SCALE_COMMAND);
        skipCommand = Pattern.compile(SKIP_COMMAND);
        drawCommand = Pattern.compile(DRAW_COMMAND);
        originDirective = Pattern.compile(ORIGIN_DIRECTIVE);
        angleDirective = Pattern.compile(ANGLE_DIRECTIVE);
        unitLengthDirective = Pattern.compile(UNIT_LENGTH_DIRECTIVE);
        unitLengthDegreeScalerFirstFormat = Pattern.compile(UNIT_LENGTH_DEGREE_SCALER_FIRST_FORMAT);
        unitLengthDegreeScalerSecondFormat = Pattern.compile(UNIT_LENGTH_DEGREE_SCALER_SECOND_FORMAT);
        axiomDirective = Pattern.compile(AXIOM_DIRECTIVE);
        productionDirective = Pattern.compile(PRODUCTION_DIRECTIVE);
    }

    /**
     * Returns new or existing instance.
     *
     * @return instance of this class.
     */
    public static SimplePatternConfig getInstance() {
        if (simplePatternConfig == null) {
            simplePatternConfig = new SimplePatternConfig();
        }
        return simplePatternConfig;
    }

    /**
     * Returns color command pattern.
     *
     * @return color command pattern.
     */
    public Pattern getColorCommand() {
        return colorCommand;
    }

    /**
     * Returns rotate command pattern.
     *
     * @return rotate command pattern.
     */

    public Pattern getRotateCommand() {
        return rotateCommand;
    }

    /**
     * Returns scale command pattern.
     *
     * @return scale command pattern.
     */

    public Pattern getScaleCommand() {
        return scaleCommand;
    }

    /**
     * Returns skip command pattern.
     *
     * @return skip command pattern.
     */

    public Pattern getSkipCommand() {
        return skipCommand;
    }

    /**
     * Returns draw command pattern.
     *
     * @return draw command pattern.
     */

    public Pattern getDrawCommand() {
        return drawCommand;
    }

    /**
     * Returns origin directive pattern.
     *
     * @return origin directive pattern.
     */

    public Pattern getOriginDirective() {
        return originDirective;
    }

    /**
     * Returns angle directive pattern.
     *
     * @return angle directive pattern.
     */

    public Pattern getAngleDirective() {
        return angleDirective;
    }

    /**
     * Returns unit length pattern.
     *
     * @return unit length pattern.
     */

    public Pattern getUnitLengthDirective() {
        return unitLengthDirective;
    }

    /**
     * Returns unit length degree scaler pattern(format x.x).
     *
     * @return unit length degree scaler pattern.
     */

    public Pattern getUnitLengthDegreeScalerFirstFormat() {
        return unitLengthDegreeScalerFirstFormat;
    }

    /**
     * Returns unit length degree scaler pattern(format x.x / y.y).
     *
     * @return unit length degree scaler pattern.
     */

    public Pattern getUnitLengthDegreeScalerSecondFormat() {
        return unitLengthDegreeScalerSecondFormat;
    }

    /**
     * Returns axiom directive pattern.
     *
     * @return axiom directive pattern.
     */

    public Pattern getAxiomDirective() {
        return axiomDirective;
    }

    /**
     * Returns production directive pattern.
     *
     * @return production directive pattern.
     */

    public Pattern getProductionDirective() {
        return productionDirective;
    }
}
