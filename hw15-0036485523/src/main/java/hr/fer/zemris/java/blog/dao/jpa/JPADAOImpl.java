package hr.fer.zemris.java.blog.dao.jpa;

import hr.fer.zemris.java.blog.dao.DAO;
import hr.fer.zemris.java.blog.dao.DAOException;
import hr.fer.zemris.java.blog.dao.DAOProvider;
import hr.fer.zemris.java.blog.model.BlogComment;
import hr.fer.zemris.java.blog.model.BlogEntry;
import hr.fer.zemris.java.blog.model.BlogUser;

import javax.persistence.NoResultException;
import java.util.Date;
import java.util.List;

/**
 * This is the implementation of the DAO subsystem using Java Persistence API.
 */
public class JPADAOImpl implements DAO {

    @Override
    public BlogEntry getBlogEntry(Long id) throws DAOException {
        return JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
    }

    @Override
    public BlogUser getBlogUser(String username) throws DAOException {
        try {
            return JPAEMProvider.getEntityManager().createNamedQuery("BlogUser.getByNick", BlogUser.class)
                    .setParameter("nick", username)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<BlogUser> getUsers() throws DAOException {
        return JPAEMProvider.getEntityManager()
                .createNamedQuery("BlogUser.getAllUsers", BlogUser.class).getResultList();
    }

    @Override
    public void createUser(BlogUser user) throws DAOException {
        JPAEMProvider.getEntityManager().persist(user);
    }

    @Override
    public List<BlogEntry> getBlogEntries(String username) throws DAOException {
        BlogUser user = getBlogUser(username);

        return JPAEMProvider.getEntityManager().createNamedQuery("BlogEntry.getEntriesFromUser", BlogEntry.class)
                .setParameter("user", user).getResultList();
    }

    @Override
    public void createBlogEntry(BlogEntry blogEntry) throws DAOException {
        JPAEMProvider.getEntityManager().persist(blogEntry);
    }

    @Override
    public void addComment(Long blogEntryId, BlogComment comment) throws DAOException {
        BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, blogEntryId);
        comment.setBlogEntry(blogEntry);
        JPAEMProvider.getEntityManager().persist(comment);
        blogEntry.getComments().add(comment);
    }

    @Override
    public void updateBlogEntry(BlogEntry entry) throws DAOException {
        BlogEntry entryDB =  DAOProvider.getDAO().getBlogEntry(entry.getId());
        entryDB.setLastModifiedAt(entry.getLastModifiedAt());
        entryDB.setTitle(entry.getTitle());
        entryDB.setText(entry.getText());
        JPAEMProvider.getEntityManager().merge(entryDB);
    }
}