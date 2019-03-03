package hr.fer.zemris.java.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This class represents one comment within the blog entry.
 */
@Entity
@Table(name = "blog_comments")
public class BlogComment {

    /**
     * The blog comment id.
     */
    private Long id;

    /**
     * The associated {@link BlogEntry}.
     */
    private BlogEntry blogEntry;

    /**
     * The user's email address.
     */
    private String userEMail;

    /**
     * The message/comment.
     */
    private String message;

    /**
     * The date when the comment was added.
     */
    private Date postedOn;

    /**
     * Returns the blog comment id.
     *
     * @return the blog comment id.
     */
    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    /**
     * Sets the blog id.
     *
     * @param id the blog id.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns a blog entry the comment belongs to.
     *
     * @return a blog entry the comment belongs to.
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    public BlogEntry getBlogEntry() {
        return blogEntry;
    }

    /**
     * Sets a blog entry the comment belongs to.
     *
     * @param blogEntry the blog entry.
     */
    public void setBlogEntry(BlogEntry blogEntry) {
        this.blogEntry = blogEntry;
    }

    /**
     * Returns the user's email address.
     *
     * @return the user's email address.
     */
    @Column(length = 100, nullable = false)
    public String getUserEMail() {
        return userEMail;
    }

    /**
     * Sets the user's email address.
     *
     * @param usersEMail the user's email address.
     */
    public void setUserEMail(String usersEMail) {
        this.userEMail = usersEMail;
    }

    /**
     * Returns the message.
     *
     * @return the message.
     */
    @Column(length = 4096, nullable = false)
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

    /**
     * Returns the date when the comment was added.
     *
     * @return the date when the comment was added.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Date getPostedOn() {
        return postedOn;
    }

    /**
     * Sets the date when the comment was added.
     *
     * @param postedOn the date when the comment was added.
     */
    public void setPostedOn(Date postedOn) {
        this.postedOn = postedOn;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BlogComment other = (BlogComment) obj;
        if (id == null) {
            return other.id == null;
        } else return id.equals(other.id);
    }
}