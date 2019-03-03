package hr.fer.zemris.java.model;

import java.util.Objects;

/**
 * This class represents a poll option model. Each poll option  consists of : id ,option title, option link ,
 * the poll to which it belongs and votes count.
 * The option link represents an interesting content on the Internet related to this option.
 */
public class PollOption {

    /**
     * The poll option id.
     */
    private long id;

    /**
     * The option title.
     */
    private String optionTitle;

    /**
     * The option link. Represents an interesting content on the Internet related to this option.
     */
    private String optionLink;

    /**
     * The poll to which {@link PollOption} belongs.
     */
    private Poll poll;

    /**
     * The votes count.
     */
    private long votesCount;

    /**
     * Creates an instance of {@link PollOption}.
     */
    public PollOption() {
    }

    /**
     * Returns the poll option id.
     *
     * @return the poll option id.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the poll option id.
     *
     * @param id the poll option id.
     */
    public void setId(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("Id must be greater or equal to 0");
        }
        this.id = id;
    }

    /**
     * Returns the option title.
     *
     * @return the option title.
     */
    public String getOptionTitle() {
        return optionTitle;
    }

    /**
     * Sets the option title.
     *
     * @param optionTitle the option title.
     * @throws NullPointerException if option title is a null reference.
     */
    public void setOptionTitle(String optionTitle) {
        Objects.requireNonNull(optionTitle, "Option title must not be null!");
        this.optionTitle = optionTitle;
    }

    /**
     * Returns the option link on the Internet.
     *
     * @return the option link on the Internet
     */
    public String getOptionLink() {
        return optionLink;
    }

    /**
     * Sets the option link.
     *
     * @param optionLink the option link.
     * @throws NullPointerException if option link is a null reference.
     */
    public void setOptionLink(String optionLink) {
        Objects.requireNonNull(optionLink, "Option link must not be null!");
        this.optionLink = optionLink;
    }

    /**
     * Returns the poll to which {@link PollOption} belongs.
     *
     * @return the poll to which {@link PollOption} belongs.
     */
    public Poll getPoll() {
        return poll;
    }

    /**
     * Sets the poll to which {@link PollOption} belongs.
     *
     * @param poll the poll to which {@link PollOption} belongs.
     * @throws NullPointerException if poll is a null reference.
     */
    public void setPoll(Poll poll) {
        Objects.requireNonNull(poll, "Poll must not be null!");
        this.poll = poll;
    }

    /**
     * Returns the votes count.
     *
     * @return the votes count.
     */
    public long getVotesCount() {
        return votesCount;
    }

    /**
     * Sets the votes count.
     *
     * @param votesCount the votes count.
     */
    public void setVotesCount(long votesCount) {
        this.votesCount = votesCount;
    }
}
