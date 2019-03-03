package hr.fer.zemris.java.blog.web.servlets.forms;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Auxiliary class for mail validation.
 */
public class UtilMail {

    /**
     * The pattern for mail validation.
     */
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    /**
     * Validates the given email address.
     *
     * @param emailStr some email address.
     * @return true if the email is valid.
     */
    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}
