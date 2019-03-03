package hr.fer.zemris.java.blog.dao;

import hr.fer.zemris.java.blog.dao.jpa.JPADAOImpl;

/**
 * Represents a singleton class who knows whom to return as an access provider.
 */
public class DAOProvider {

    /**
     * The singleton instance.
     */
    private static DAO dao = new JPADAOImpl();

    /**
     * Returns the instance of {@link DAOProvider}.
     *
     * @return the instance of {@link DAOProvider}.
     */
    public static DAO getDAO() {
        return dao;
    }
}