package hr.fer.zemris.java.blog.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * This class represents a provider for Java Persistence API  entity manager factory.
 *
 * @see EntityManagerFactory
 */
public class JPAEMFProvider {

    /**
     * The entity manager factory.
     */
    public static EntityManagerFactory emf;

    /**
     * Returns the entity manager factory.
        *
        * @return the entity manager factory.
        */
public static EntityManagerFactory getEmf() {
        return emf;
        }

/**
 * Sets the entity manager factory.
 *
 * @param emf the entity manager factory.
 */
public static void setEmf(EntityManagerFactory emf) {
        JPAEMFProvider.emf = emf;
        }
        }