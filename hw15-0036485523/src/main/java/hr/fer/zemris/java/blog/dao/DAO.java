package hr.fer.zemris.java.blog.dao;

import hr.fer.zemris.java.blog.model.BlogComment;
import hr.fer.zemris.java.blog.model.BlogEntry;
import hr.fer.zemris.java.blog.model.BlogUser;

import java.util.List;

/**
 * Interface to data subsystem.
 */
public interface DAO {

    /**
     * Returns the blog entry associated with the given <code>id</code>.
     *
     * @param id the blog entry id.
     * @return the blog entry or <code>null</code> if there is no entry with the given key.
     * @throws DAOException if an error occurs while retrieving the data.
     */
    BlogEntry getBlogEntry(Long id) throws DAOException;

    /**
     * Returns the blog user associated with the given username.
     *
     * @param username the user username.
     * @return the blog user associated with the given username or <code>null</code>
     * @throws DAOException
     */
    BlogUser getBlogUser(String username) throws DAOException;

    /**
     * Returns the list of all registered users.
     *
     * @return the list of all users.
     * @throws DAOException if an error occurs while retrieving the data.
     */
    List<BlogUser> getUsers() throws DAOException;

    /**
     * Adds a new user to the database.
     *
     * @param user the user we want to add.
     * @throws DAOException if an error occurs while storing the data.
     */
    void createUser(BlogUser user) throws DAOException;

    /**
     * Returns the list of entries associated with the given user.
     *
     * @param username the user username.
     * @return the list of entries associated with the given user.
     * @throws DAOException if an error occurs while retrieving the data.
     */
    List<BlogEntry> getBlogEntries(String username) throws DAOException;

    /**
     * Adds a new blog entry to the database.
     *
     * @param blogEntry the blog entry.
     * @throws DAOException if an error occurs while storing the data.
     */
    void createBlogEntry(BlogEntry blogEntry) throws DAOException;

    /**
     * Adds a comment to the given blog entry. Every user who adds a comment must also have a valid email address.
     *
     * @param blogEntryId the id of entry.
     * @param comment the blog comment.
     * @throws DAOException if an error occurs while storing the data.
     */
    void addComment(Long blogEntryId, BlogComment comment) throws DAOException;

    /**
     * Updates a  blog entry int the database.
     *
     * @param blogEntry the blog entry.
     * @throws DAOException if an error occurs while storing the data.
     */
    void updateBlogEntry(BlogEntry blogEntry) throws DAOException;
}