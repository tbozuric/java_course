package hr.fer.zemris.java.dao.sql;

import java.sql.Connection;

/**
 * Store the connection to the database in the ThreadLocal object. ThreadLocal is actually a map  whose keys are a thread
 * identifier that operates mapping.
 */
public class SQLConnectionProvider {

    /**
     * The map of connections.
     */
    private static ThreadLocal<Connection> connections = new ThreadLocal<>();

    /**
     * Sets the connection to the current thread
     * (or delete the entry from the map if the argument is <code> null </ code>).
     *
     * @param con the connection to the database.
     */
    public static void setConnection(Connection con) {
        if (con == null) {
            connections.remove();
        } else {
            connections.set(con);
        }
    }

    /**
     * Get the connection that the current thread (caller) can use.
     *
     * @return the connection to the database.
     */
    public static Connection getConnection() {
        return connections.get();
    }

}