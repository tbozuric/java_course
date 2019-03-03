package hr.fer.zemris.java.blog.web.servlets.forms;


import javax.servlet.http.HttpServletRequest;

/**
 * Represents the model corresponding to web-representation of domain
 * object {@link hr.fer.zemris.java.blog.model.BlogComment}.
 */
public class CommentEntryForm extends Form {

    /**
     * The email of the user who added the comment.
     */
    private String email;

    /**
     * The comment.
     */
    private String comment;

    @Override
    public void populateFromRequest(HttpServletRequest req) {
        this.email = process(req.getParameter("email"));
        this.comment = process(req.getParameter("comment"));
    }

    @Override
    public void validate() {
        errors.clear();

        if (email.isEmpty()) {
            errors.put("email", "Email is a mandatory field.");
        } else if (!UtilMail.validateEmail(email)) {
            errors.put("email", "Email is in the wrong form.");
        }
        if (comment.isEmpty()) {
            errors.put("comment", "Message is a mandatory field.");
        }
    }

    /**
     * Returns the email of the user who added the comment.
     *
     * @return the email of the user who added the comment.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user who added the comment.
     *
     * @param email the email of the user who added the comment.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the comment.
     *
     * @return the comment.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the comment.
     *
     * @param comment the comment.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
}
