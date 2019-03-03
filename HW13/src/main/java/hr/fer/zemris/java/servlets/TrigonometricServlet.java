package hr.fer.zemris.java.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.toRadians;

/**
 * This class allows the user to obtain a table of values of trigonometric functions sin(x) and cos(x)
 * for all integer angles (in degrees, not radians) in a range determined by URL parameters
 * <code>a</code> and <code>b</code> (if a is missing,we assume <code>a=0</code>;
 * if <code>b</code> is missing, we assume <code>b=360</code>; if <code>a > b</code>, a and b will swap the values;
 * if <code>b > a+720</code>, <code>b</code> wil be <code>a+720</code>).
 * This action is accessible on local URL /trigonometric.
 */
@WebServlet("/trigonometric")
public class TrigonometricServlet extends HttpServlet {

    /**
     * The default serial version UID.
     */
    private static final long serialVersionUID = -938035612886437765L;

    /**
     * The minimum angle.
     */
    private static final int MINIMUM_ANGLE = 0;

    /**
     * The maximum angle.
     */
    private static final int MAXIMUM_ANGLE = 360;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer a = getNumber(req.getParameter("a"), MINIMUM_ANGLE);
        Integer b = getNumber(req.getParameter("b"), MAXIMUM_ANGLE);

        if (a > b) {
            Integer temp = a;
            a = b;
            b = temp;
        }
        if (b > a + 720) {
            b = a + 720;
        }

        List<TrigonometricTuple> values = new ArrayList<>();
        for (int i = a; i <= b; i++) {
            values.add(new TrigonometricTuple(i));
        }
        req.setAttribute("trigonometricValues", values);
        req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
    }

    /**
     * Returns a parsed number or a default value.
     *
     * @param number       the number for parsing.
     * @param defaultValue the default value.
     * @return a parsed number or a default value.
     */
    private Integer getNumber(String number, int defaultValue) {
        try {
            if (number != null) {
                return Integer.valueOf(number);
            }
        } catch (NumberFormatException ignorable) {
        }
        return defaultValue;
    }

    /**
     * Class represents a tuple (value, sinus value, cosine value) used in rendering values in trigonometric.jsp.
     */
    public static class TrigonometricTuple {

        /**
         * The angle in degrees.
         */
        int angle;

        /**
         * The sinus value of angle.
         */
        double sinValue;

        /**
         * The cosine  value of angle.
         */
        double cosValue;

        /**
         * Creates an instance of {@link TrigonometricTuple}.
         *
         * @param angle the angle in degrees.
         */
        public TrigonometricTuple(int angle) {
            this.angle = angle;
            this.sinValue = sin(toRadians(this.angle));
            this.cosValue = cos(toRadians(this.angle));
        }

        /**
         * Returns the angle in degrees.
         *
         * @return the angle in degrees.
         */
        public int getAngle() {
            return angle;
        }

        /**
         * Returns the sinus of angle.
         *
         * @return the sinus of angle.
         */
        public double getSinValue() {
            return sinValue;
        }

        /**
         * Returns the cosine of angle.
         *
         * @return the cosine of angle.
         */
        public double getCosValue() {
            return cosValue;
        }
    }
}
