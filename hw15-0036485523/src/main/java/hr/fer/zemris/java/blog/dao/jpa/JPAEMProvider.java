package hr.fer.zemris.java.blog.dao.jpa;

import hr.fer.zemris.java.blog.dao.DAOException;
import javax.persistence.EntityManager;

/**
 * Represents a provider for Java Persistence API entity manager.
 */
public class JPAEMProvider {

    /**
     * The map of entity managers.
     */
    private static ThreadLocal<EntityManager> locals = new ThreadLocal<>();

    /**
     * Returns the entity manager that the current thread can use.
     *
     * @return the {@link EntityManager} that the current thread can use.
     */
    public static EntityManager getEntityManager() {
        EntityManager em = locals.get();
        if (em == null) {
            em = JPAEMFProvider.getEmf().createEntityManager();
            em.getTransaction().begin();
            locals.set(em);
        }
        return em;
    }

    /**
     * Commits the pending action and closes the entity manager.
     *
     * @throws DAOException if it is not possible to complete the transaction or close the entity manager
     */
    public static void close() throws DAOException {
        EntityManager em = locals.get();
        if (em == null) {
            return;
        }
        DAOException dex = null;
        try {
            em.getTransaction().commit();
        } catch (Exception ex) {
            dex = new DAOException("Unable to commit transaction.", ex);
        }
        try {
            em.close();
        } catch (Exception ex) {
            if (dex != null) {
                dex = new DAOException("Unable to close entity manager.", ex);
            }
        }
        locals.remove();
        if (dex != null) throw dex;
    }
}