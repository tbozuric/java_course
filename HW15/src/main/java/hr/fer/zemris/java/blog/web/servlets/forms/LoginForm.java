package hr.fer.zemris.java.blog.web.servlets.forms;

import hr.fer.zemris.java.blog.crypto.Digester;
import hr.fer.zemris.java.blog.crypto.SHADigester;
import hr.fer.zemris.java.blog.crypto.Util;
import hr.fer.zemris.java.blog.dao.DAOProvider;
import hr.fer.zemris.java.blog.model.BlogUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * * Represents the model corresponding to web-representation of login form.
 */
public class LoginForm extends Form {

    /**
     * The username.
     */
    private String username;

    /**
     * The password of the user.
     */
    private String password;

    /**
     * The blog user {@link BlogUser}.
     */
    private BlogUser blogUser;

    /**
     * Populates the data from the {@link HttpServletRequest}.
     *
     * @param req the {@link HttpServletRequest}.
     */
    public void populateFromRequest(HttpServletRequest req) {
        this.username = process(req.getParameter("nick"));
        this.password = process(req.getParameter("password"));
    }

    @Override
    public void validate() {
        errors.clear();

        blogUser = DAOProvider.getDAO().getBlogUser(username);
        if (blogUser == null) {
            errors.put("notExist", "User does not exist. Please try again.");
            return;
        }

        Digester digester = SHADigester.getShaDigester();
        String pass = Util.bytesToHex(digester.digest(password));
        if (!blogUser.getPasswordHash().equals(pass)) {
            errors.put("invalidPassword", "Invalid password. Please try again");
        }
    }

    /**
     * Returns the username.
     *
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the password of the user.
     *
     * @return the password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the blog user {@link BlogUser}.
     *
     * @return the blog user {@link BlogUser}.
     */
    public BlogUser getBlogUser() {
        return blogUser;
    }
}
