package hr.fer.zemris.java.blog.web.servlets.forms;

import hr.fer.zemris.java.blog.model.BlogEntry;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Represents the model corresponding to web-representation of domain object {@link BlogEntry}.
 */
public class BlogEntryForm extends Form {

    /**
     * The title of blog entry.
     */
    private String title;

    /**
     * The message.
     */
    private String message;


    @Override
    public void populateFromRequest(HttpServletRequest req) {
        this.title = process(req.getParameter("title"));
        this.message = process(req.getParameter("message"));
    }


    @Override
    public void validate() {
        errors.clear();
        if (title.isEmpty()) {
            errors.put("title", "Title must not be empty!");
        }

        if (message.isEmpty()) {
            errors.put("message", "Message must not be empty!");
        }
    }

    /**
     * Returns a new {@link BlogEntry}
     *
     * @return a new instance of {@link BlogEntry}.
     */
    public BlogEntry getBlogEntry() {
        BlogEntry blogEntry = new BlogEntry();
        blogEntry.setCreatedAt(new Date());
        blogEntry.setLastModifiedAt(blogEntry.getCreatedAt());
        blogEntry.setTitle(title);
        blogEntry.setText(message);

        return blogEntry;
    }

    /**
     * Populates the data from the {@link BlogEntry} object.
     *
     * @param entry the {@link BlogEntry} object.
     */
    public void populateFromBlogEntry(BlogEntry entry) {
        this.title = entry.getTitle();
        this.message = entry.getText();
    }

    /**
     * Returns the title of blog entry.
     *
     * @return the title of blog entry.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of blog entry.
     *
     * @param title the title of blog entry.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the message.
     *
     * @return the message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message.
     *
     * @param message the message.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
