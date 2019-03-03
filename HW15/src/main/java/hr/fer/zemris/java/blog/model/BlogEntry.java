package hr.fer.zemris.java.blog.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Represents the blog entry. Each blog entry consists of a comment, a person who created it,
 * the date of creation, the date of the last edit, the title and the text.
 */
@NamedQueries({
        @NamedQuery(name = "BlogEntry.upit1", query = "select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when"),
        @NamedQuery(name = "BlogEntry.getEntriesFromUser", query = "select b from BlogEntry as b  where b.creator=:user")
})
@Entity
@Table(name = "blog_entries")
public class BlogEntry {

    /**
     * The blog entry id.
     */
    private Long id;

    /**
     * The list od associated comments.
     */
    private List<BlogComment> comments = new ArrayList<>();

    /**
     * The blog creator.
     */
    private BlogUser creator;

    /**
     * The date when the blog was created.
     */
    private Date createdAt;

    /**
     * The date when the blog was last time modified.
     */
    private Date lastModifiedAt;

    /**
     * The title of blog entry.
     */
    private String title;

    /**
     * The text of blog entry.
     */
    private String text;

    /**
     * Returns the id of blog entry.
     *
     * @return the id of blog entry.
     */
    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    /**
     * Sets the id of blog entry.
     *
     * @param id the id of blog entry.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the associated comments (blog comments).
     *
     * @return the associated comments (blog comments).
     */
    @OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    @OrderBy("postedOn")
    public List<BlogComment> getComments() {
        return comments;
    }

    /**
     * Sets the associated comments (blog comments).
     *
     * @param comments the comments.
     */
    public void setComments(List<BlogComment> comments) {
        this.comments = comments;
    }

    /**
     * Returns the creator of the blog.
     *
     * @return the creator of the blog.
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    public BlogUser getCreator() {
        return creator;
    }

    /**
     * Sets the creator of the blog.
     *
     * @param creator the creator of the blog.
     */
    public void setCreator(BlogUser creator) {
        this.creator = creator;
    }

    /**
     * Returns the date when the blog entry was created.
     *
     * @return the date when the blog entry was created.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the date when the blog entry was created.
     *
     * @param createdAt the date when the blog entry was created.
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Returns the date when the blog entry was last time modified.
     *
     * @return the date when the blog entry was last time modified.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    public Date getLastModifiedAt() {
        return lastModifiedAt;
    }

    /**
     * Sets the date when the blog entry was last time modified.
     *
     * @param lastModifiedAt the date when the blog entry was last time modified.
     */
    public void setLastModifiedAt(Date lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    /**
     * Returns the title of the blog entry.
     *
     * @return the title of the blog entry.
     */
    @Column(length = 200, nullable = false)
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the blog entry.
     *
     * @param title the title of the blog entry.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the text of blog entry.
     *
     * @return the text of blog entry.
     */
    @Column(length = 4096, nullable = false)
    public String getText() {
        return text;
    }

    /**
     * Sets the text of blog entry.
     *
     * @param text the text of blog entry.
     */
    public void setText(String text) {
        this.text = text;
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
        BlogEntry other = (BlogEntry) obj;
        if (id == null) {
            return other.id == null;
        } else return id.equals(other.id);
    }
}