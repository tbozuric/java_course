package hr.fer.zemris.java.blog.web.servlets.forms;

import hr.fer.zemris.java.blog.crypto.SHADigester;
import hr.fer.zemris.java.blog.crypto.Util;
import hr.fer.zemris.java.blog.dao.DAOProvider;
import hr.fer.zemris.java.blog.model.BlogUser;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents the model corresponding to web-representation of domain
 * object {@link hr.fer.zemris.java.blog.model.BlogUser}.
 */
public class RegistrationForm extends Form {

    /**
     * The first name of the user.
     */
    private String firstName;

    /**
     * The last name of the user.
     */
    private String lastName;

    /**
     * The username/nick.
     */
    private String nick;

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The user  password.
     */
    private String password;

    /**
     * Populates the data from the {@link HttpServletRequest}.
     *
     * @param req the {@link HttpServletRequest}.
     */
    public void populateFromRequest(HttpServletRequest req) {
        this.firstName = process(req.getParameter("firstName"));
        this.lastName = process(req.getParameter("lastName"));
        this.nick = process(req.getParameter("nick"));
        this.email = process(req.getParameter("email"));
        this.password = process(req.getParameter("password"));

    }


    public void validate() {
        errors.clear();

        if (firstName.isEmpty()) {
            errors.put("firstName", "First name is a mandatory field.");
        }
        if (lastName.isEmpty()) {
            errors.put("lastName", "Last name is a mandatory field.");
        }
        if (nick.isEmpty()) {
            errors.put("nick", "Username is a mandatory field.");
        }
        if (email.isEmpty()) {
            errors.put("email", "Email is a mandatory field.");
        } else if (!UtilMail.validateEmail(email)) {
            errors.put("email", "Email is in the wrong form.");
        }
        if (password.isEmpty()) {
            errors.put("password", "Password is a mandatory field.");
        }
        if (DAOProvider.getDAO().getBlogUser(nick) != null) {
            errors.put("nick", "Username already exists. Please enter another username.");
        }
    }

    /**
     * Returns a new instance of {@link BlogUser}.
     *
     * @return a new instance of {@link BlogUser}.
     */
    public BlogUser getUser() {
        BlogUser user = new BlogUser();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setNick(nick);
        user.setPasswordHash(Util.bytesToHex(SHADigester.getShaDigester().digest(password)));
        return user;
    }

    /**
     * The first name of the user.
     *
     * @return the first name of the user.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * The last name of the user.
     *
     * @return the last name of the user.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the username/nick.
     *
     * @return the username/nick.
     */
    public String getNick() {
        return nick;
    }

    /**
     * Returns the email address of the user.
     *
     * @return the email address of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the user password.
     *
     * @return the user password.
     */
    public String getPassword() {
        return password;
    }
}
