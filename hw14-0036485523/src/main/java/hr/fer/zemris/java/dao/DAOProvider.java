package hr.fer.zemris.java.dao;

import hr.fer.zemris.java.dao.sql.SQLDAO;

/**
 * Represents a singleton class who knows whom to return as an access provider.
 */
public class DAOProvider {

    /**
     * The singleton instance.
     */
    private static DAO dao = new SQLDAO();

    /**
     * Returns the instance of {@link DAOProvider}.
     *
     * @return the instance of {@link DAOProvider}.
     */
    public static DAO getDao() {
        return dao;
    }

}