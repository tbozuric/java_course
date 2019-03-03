package hr.fer.zemris.java.model;

import java.util.Objects;

/**
 * This class represents a poll model. Each poll consists of : id , title and message.
 */
public class Poll {

    /**
     * The poll id.
     */
    private long id;

    /**
     * The title of poll.
     */
    private String title;

    /**
     * The message.
     */
    private String message;

    /**
     * Creates an instance of {@link Poll}.
     */
    public Poll() {
    }

    /**
     * Returns the poll id.
     *
     * @return the poll id.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the poll id.
     *
     * @param id the poll id.
     */
    public void setId(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("Id must be greater or equal to 0");
        }
        this.id = id;
    }

    /**
     * Returns the title of poll.
     *
     * @return the title of poll.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of poll.
     *
     * @param title the title of poll.
     * @throws NullPointerException if poll title is a null reference.
     */
    public void setTitle(String title) {
        Objects.requireNonNull(title, "Poll title must not be null!");
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
        Objects.requireNonNull(message, "Poll message must not be null!");
        this.message = message;
    }
}
