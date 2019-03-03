package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.calc.elements.CustomBasicButton;
import hr.fer.zemris.java.gui.calc.elements.CustomCheckBox;
import hr.fer.zemris.java.gui.calc.elements.Display;
import hr.fer.zemris.java.gui.calc.operations.DoubleBinaryOperation;
import hr.fer.zemris.java.gui.calc.operations.DoubleUnaryOperation;
import hr.fer.zemris.java.gui.calc.operations.Operation;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

/**
 * Represent a frame as an implementation of the simple calculator.
 * It has 30 buttons (components) and one display. Used layout manager is {@link CalcLayout}.
 */
public class Calculator extends JFrame {

    /**
     * A unique serial version identifier.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Represents the text for the push button.
     */
    private static final String PUSH_BUTTON = "push";

    /**
     * Represents the text for the reset button.
     */
    private static final String RESET_BUTTON = "res";

    /**
     * Represents the text for the clear button.
     */
    private static final String CLEAR_BUTTON = "clr";

    /**
     * Represents the text for the inert decimal point button.
     */
    private static final String DECIMAL_POINT_BUTTON = ".";

    /**
     * Represents the text for the swap sign button.
     */
    private static final String SWAP_SIGN_BUTTON = "+/-";

    /**
     * Represents the text for the calculate button.
     */
    private static final String CALCULATE_BUTTON = "=";

    /**
     * Represents the text for the inverse button.
     */
    private static final String INVERSE_BUTTON = "Inv";

    /**
     * Represents the text for the pop button.
     */
    private static final String POP_BUTTON = "pop";

    /**
     * Reference to {@link CalcModel}.
     */
    private CalcModel calcModel;

    /**
     * Map of all supported binary operators.
     * The key is the name of the binary operator, and the value is the operator itself.
     */
    private Map<String, DoubleBinaryOperator> binaryOperatorMap;

    /**
     * Map of all supported {@link Operation}s.
     * The key is the name of the unary operator, and the value is an instance of {@link Operation} class.
     */
    private Map<String, Operation> unaryOperatorMap;

    /**
     * The value storage stack. It is used to store values that appear on the calculator {@link Display}.
     */
    private Stack<String> stack;

    /**
     * Creates an instance of {@link Calculator}.
     */
    public Calculator() {
        super();
        calcModel = new CalcModelImpl();
        binaryOperatorMap = new HashMap<>();
        unaryOperatorMap = new HashMap<>();
        stack = new Stack<>();
        initOperatorsMap();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Calculator");
        setLocation(20, 20);
        setSize(1000, 500);
        setMinimumSize(new Dimension(500,500));
        initGUI();

    }

    /**
     * Initializes all supported operations in the calculator.
     * Supported binary operators are:
     * <p>+</p>
     * <p>-</p>
     * <p>*</p>
     * <p>/</p>.
     * <p>x^n</p>
     * <p>
     * Supported unary operators are:
     * <p>cos , arccos</p>
     * <p>sin , arcsin</p>
     * <p>tan , arctan</p>
     * <p>ctg , arcctg</p>
     * <p>log , 10^x</p>
     * <p>ln , e^x</p>
     * <p>1/x , x</p>
     */
    private void initOperatorsMap() {
        binaryOperatorMap.put("+", (a, b) -> a + b);
        binaryOperatorMap.put("-", (a, b) -> a - b);
        binaryOperatorMap.put("*", (a, b) -> a * b);
        binaryOperatorMap.put("/", (a, b) -> a / b);

        unaryOperatorMap.put("1/x", new DoubleUnaryOperation(x -> 1 / x, x -> x));
        unaryOperatorMap.put("sin", new DoubleUnaryOperation(Math::sin, Math::asin));
        unaryOperatorMap.put("cos", new DoubleUnaryOperation(Math::cos, Math::acos));
        unaryOperatorMap.put("tan", new DoubleUnaryOperation(Math::tan, Math::atan));
        unaryOperatorMap.put("ctg", new DoubleUnaryOperation(x -> 1 / Math.tan(x), x -> Math.PI / 2 - Math.atan(x)));
        unaryOperatorMap.put("log", new DoubleUnaryOperation(Math::log10, x -> Math.pow(10, x)));
        unaryOperatorMap.put("ln", new DoubleUnaryOperation(Math::log, Math::exp));
        unaryOperatorMap.put("x^n", new DoubleBinaryOperation(Math::pow, (x, n) -> Math.pow(x, 1.0 / n)));
    }

    /**
     * Initiates a graphical user interface with 30 "executable" buttons and one {@link Display}.
     */
    private void initGUI() {
        Container cp = getContentPane();
        JPanel p = new JPanel(new CalcLayout(2));

        JLabel display = new Display("");
        calcModel.addCalcValueListener((Display) display);
        p.add(display, new RCPosition(1, 1));

        CustomCheckBox inv = new CustomCheckBox(INVERSE_BUTTON);
        p.add(inv, new RCPosition(5, 7));


        addUnaryOperations(p, b -> performUnaryOperation(inv, b));
        addNumbers(p, b -> calcModel.insertDigit(Integer.parseInt(b.getText())));
        addBinaryOperations(p, this::binaryOperationAction);
        addToContainer(p, this::performCalculation, new RCPosition(1, 6), CALCULATE_BUTTON);
        addToContainer(p, b -> calcModel.swapSign(), new RCPosition(5, 4), SWAP_SIGN_BUTTON);
        addToContainer(p, b -> calcModel.insertDecimalPoint(), new RCPosition(5, 5), DECIMAL_POINT_BUTTON);
        addToContainer(p, b -> calcModel.clear(), new RCPosition(1, 7), CLEAR_BUTTON);
        addToContainer(p, b -> calcModel.clearAll(), new RCPosition(2, 7), RESET_BUTTON);
        addToContainer(p, b -> stack.push(calcModel.toString()), new RCPosition(3, 7), PUSH_BUTTON);
        addToContainer(p, this::popAction, new RCPosition(4, 7), POP_BUTTON);

        cp.add(p);
    }

    /**
     * Represents an binary operation action.
     *
     * @param b a button that represents a binary operation.
     */
    private void binaryOperationAction(CustomBasicButton b) {
        performCalculation(b);
        calcModel.setActiveOperand(calcModel.getValue());
        calcModel.setPendingBinaryOperation(binaryOperatorMap.get(b.getText()));
        ((CalcModelImpl) (calcModel)).clearWithoutNotify();
    }

    /**
     * Represents the power action.
     *
     * @param b        a button that represents x^n operation.
     * @param selected flag indicating whether an inverse operation is required.
     */
    private void powerAction(CustomBasicButton b, boolean selected) {
        performCalculation(b);
        calcModel.setActiveOperand(calcModel.getValue());
        calcModel.setPendingBinaryOperation((DoubleBinaryOperator) unaryOperatorMap.get(b.getText()).getOperation(selected));
        ((CalcModelImpl) (calcModel)).clearWithoutNotify();
    }

    /**
     * Represents the pop action.
     *
     * @param b a button that represents  pop operation.
     */
    private void popAction(CustomBasicButton b) {
        if (stack.isEmpty()) {
            showMessageDialog(b);
            return;
        }
        calcModel.setValue(Double.parseDouble(stack.pop()));
    }

    /**
     * Performs  unary operation depending on the given flag.
     *
     * @param inv a check box that indicating whether an inverse operation is required.
     * @param b   a button that represents some unary operation.
     */
    private void performUnaryOperation(CustomCheckBox inv, CustomBasicButton b) {

        if (b.getText().equals("x^n")) {
            powerAction(b, inv.isSelected());
            return;
        }

        DoubleUnaryOperator operator = (DoubleUnaryOperator) unaryOperatorMap.get(b.getText()).getOperation(inv.isSelected());
        double value;

        value = operator.applyAsDouble(calcModel.getValue());
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            showMessageDialog(b);
        }
        calcModel.setValue(value);
    }

    /**
     * Shows the message dialog.
     *
     * @param b clicked button.
     */
    private void showMessageDialog(CustomBasicButton b) {
        JOptionPane.showMessageDialog(
                this,
                "It is not possible to perform the required ( " + b.getText() + " ) operation.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Adds unary operations to the given panel.
     *
     * @param p        panel.
     * @param consumer unary operation consumer.
     */
    private void addUnaryOperations(JPanel p, Consumer<CustomBasicButton> consumer) {
        String[] unaryOperators = new String[]{"1/x", "log", "ln", "x^n", "sin", "cos", "tan", "ctg"};
        int i = 2;
        int j = 1;

        for (String name : unaryOperators) {
            addToContainer(p, consumer, new RCPosition(i++, j), name);
            if (i > 5) {
                i = 2;
                j++;
            }
        }
    }

    /**
     * Performs calculation when an active operand is set and pending binary operation is not a null reference.
     *
     * @param b clicked button.
     */
    private void performCalculation(CustomBasicButton b) {
        if (calcModel.getPendingBinaryOperation() != null && calcModel.isActiveOperandSet()) {
            double activeOperand = calcModel.getActiveOperand();
            double value = calcModel.getValue();
            DoubleBinaryOperator binaryOperation = calcModel.getPendingBinaryOperation();
            calcModel.clearAll();

            double val = binaryOperation.applyAsDouble(activeOperand, value);
            if (Double.isNaN(val) || Double.isInfinite(val)) {
                showMessageDialog(b);
            }
            calcModel.setValue(val);
        }
    }

    /**
     * Adds binary operations to the given panel.
     *
     * @param p        panel.
     * @param consumer binary operation consumer.
     */
    private void addBinaryOperations(JPanel p, Consumer<CustomBasicButton> consumer) {
        String[] operators = new String[]{"+", "-", "*", "/"};
        int j = 6;
        int i = 5;
        for (String oper : operators) {
            addToContainer(p, consumer, new RCPosition(i--, j), oper);
        }
    }

    /**
     * Adds a new {@link CustomBasicButton} to the {@link Container}.
     *
     * @param p        panel.
     * @param consumer the consumer.
     * @param position the position to which the button wil be added.
     * @param text     the text of the button.
     */
    private void addToContainer(JPanel p, Consumer<CustomBasicButton> consumer, RCPosition position, String text) {
        CustomBasicButton button = new CustomBasicButton(text, calcModel);
        button.addActionListener(l -> consumer.accept(button));
        p.add(button, position);
    }

    /**
     * Adds numbers to the given panel.
     *
     * @param p        panel.
     * @param consumer number consumer.
     */
    private void addNumbers(JPanel p, Consumer<CustomBasicButton> consumer) {
        int currentNumber = 1;
        for (int i = 4; i > 1; i--) {
            for (int j = 3; j < 6; j++) {
                addToContainer(p, consumer, new RCPosition(i, j), String.valueOf(currentNumber));
                currentNumber++;
            }
        }
        addToContainer(p, consumer, new RCPosition(5, 3), "0");
    }

    /**
     * Method invoked when running the program.
     *
     * @param args command-line arguments. Arguments are not used.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator window = new Calculator();
            window.setVisible(true);
        });
    }
}
