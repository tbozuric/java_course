package hr.fer.zemris.java.blog.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Represents the blog user.
 */
@NamedQueries({
        @NamedQuery(name = "BlogUser.getByNick", query = "select b from BlogUser as b where b.nick=:nick"),
        @NamedQuery(name = "BlogUser.getAllUsers", query = "select u from BlogUser as u")
})
@Entity
@Table(name = "blog_users")
public class BlogUser {

    /**
     * The user id.
     */
    private Long id;

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
     * The hash user password.
     */
    private String passwordHash;

    /**
     * The list of blog entries created by the user.
     */
    private List<BlogEntry> entries;

    /**
     * Returns the id of user.
     *
     * @return the id of user.
     */
    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    /**
     * Sets the id of user.
     *
     * @param id the user id.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the first name of the user.
     *
     * @return the first name of the user.
     */
    @Column(nullable = false)
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstName the first name of the user.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Returns the last name of the user.
     *
     * @return the last name of the user.
     */
    @Column(nullable = false)
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastName the last name of the user.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns the username.
     *
     * @return the username.
     */
    @Column(nullable = false, unique = true)
    public String getNick() {
        return nick;
    }

    /**
     * Sets the username.
     *
     * @param nick the username/nick.
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * Returns the email address of the user.
     *
     * @return the email address of the user.
     */
    @Column(nullable = false)
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email the email address of the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the hash  of user password.
     *
     * @return the hash of user password.
     */
    @Column(nullable = false)
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Sets the hash of user password.
     *
     * @param passwordHash the password hash.
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Returns the list of blog entries created by the user.
     *
     * @return the list of blog entries created by the user.
     */
    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    @OrderBy("lastModifiedAt")
    public List<BlogEntry> getEntries() {
        return entries;
    }

    /**
     * Sets the list of blog entries created by the user.
     *
     * @param entries the list of blog entries created by the user.
     */
    public void setEntries(List<BlogEntry> entries) {
        this.entries = entries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlogUser user = (BlogUser) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(nick, user.nick);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nick);
    }
}
