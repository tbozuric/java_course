package hr.fer.zemris.java;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

/**
 * This class performs initialization of the database. The database consists of two tables.
 * One table represents polls, and the other options for an individual poll.
 * The exact database name, host, port, user and password is provided in properties file
 * dbsettings.properties.
 */
@WebListener
public class Initialization implements ServletContextListener {

    /**
     * The maximum allowed port number.
     */
    private static final int MAX_PORT_NUMBER = 65635;
    /**
     * The SQL command for creating polls table in a database.
     */
    private static final String CREATE_POLLS_TABLE_SQL = "CREATE TABLE Polls (id BIGINT PRIMARY KEY" +
            " GENERATED ALWAYS AS IDENTITY , title VARCHAR(150)" +
            " NOT NULL, message CLOB(2048) NOT NULL )";
    /**
     * The SQL command for creating poll options table in a database.
     */
    private static final String CREATE_POLL_OPTIONS_TABLE_SQL = "CREATE TABLE PollOptions " +
            "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," +
            "optionTitle VARCHAR(100) NOT NULL," +
            "optionLink VARCHAR(150) NOT NULL," +
            "pollID BIGINT," +
            "votesCount BIGINT," +
            "FOREIGN KEY (pollID) REFERENCES Polls(id)" +
            ")";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String fileName = sce.getServletContext()
                .getRealPath("/WEB-INF/dbsettings.properties");

        String dbInitFile = sce.getServletContext().getRealPath("/WEB-INF/pollsDbInit");
        String pollsOptionsDbFile = sce.getServletContext().getRealPath("/WEB-INF/pollsOptionsDbInit");
        String connectionURL = getConnectionUrl(fileName);

        ComboPooledDataSource cpds = new ComboPooledDataSource();
        try {
            cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
        } catch (PropertyVetoException e1) {
            throw new RuntimeException("An error occurred during initializing pool.", e1);
        }
        cpds.setJdbcUrl(connectionURL);
        sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
        initDatabase(cpds, dbInitFile, pollsOptionsDbFile);
    }

    /**
     * Reads properties from a file and creates a url to connect to a database.
     *
     * @param fileName the name of .properties file.
     * @return the connection URL.
     * @throws RuntimeException if an error occurs while creating a URL.
     */
    private String getConnectionUrl(String fileName) {
        Properties properties = new Properties();
        String host, dbName, user, password;
        int port;

        try {
            properties.load(Files.newInputStream(Paths.get(fileName)));
            host = properties.getProperty("host");
            dbName = properties.getProperty("name");
            user = properties.getProperty("user");
            password = properties.getProperty("password");
            port = Integer.parseInt(properties.getProperty("port"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while reading initialization file.");
        }

        if (host == null || dbName == null || user == null || password == null || port <= 0 || port > MAX_PORT_NUMBER) {
            throw new RuntimeException("Initialization file is invalid.");
        }

        return String.format("jdbc:derby://%s:%s/%s;user=%s;password=%s", host, port, dbName,
                user, password);

    }

    /**
     * Creates the necessary tables in the database and fills them with the initial data.
     *
     * @param cpds           the combo pooled data source.
     * @param pollsDb        the initialization file for polls.
     * @param pollsOptionsDb the initialization file for polls options.
     * @throws RuntimeException if an error occurs while initializing the database.
     */
    private void initDatabase(ComboPooledDataSource cpds, String pollsDb, String pollsOptionsDb) {
        Connection con = null;
        try {
            con = cpds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        try {
            createTables(con);
            long ids[] = new long[2];
            if (isTableEmpty(con, "Polls")) {
                ids = insertDataIntoPoolsTable(con, pollsDb);
            }

            if (isTableEmpty(con, "PollOptions")) {
                insertDataIntoPollOptionsTable(con, pollsOptionsDb, ids);
            }

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

        try {
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Creates two tables in the database, one for the polls, the other for the options related to the polls.
     * Each poll consists of : id, title , message.
     * Each poll option consists of : id, option title, option link , poll id and votes count.
     *
     * @param connection the connection to database.
     * @throws SQLException if an error occurs when creating a table.
     */
    private void createTables(Connection connection) throws SQLException {
        DatabaseMetaData dbm = connection.getMetaData();
        ResultSet tables = dbm.getTables(null, null, "POLLS", null);
        if (!tables.next()) {
            createTableIfNotExist(connection, CREATE_POLLS_TABLE_SQL);
        }

        tables = dbm.getTables(null, null, "POLLOPTIONS", null);
        if (!tables.next()) {
            createTableIfNotExist(connection, CREATE_POLL_OPTIONS_TABLE_SQL);
        }

    }

    /**
     * Creates a new table in the database if it does not already exist.
     *
     * @param con the connection to the database.
     * @param sql the sql command.
     * @throws SQLException if an error occurs when creating a table.
     */
    private void createTableIfNotExist(Connection con, String sql) throws SQLException {
        PreparedStatement pst = con.prepareStatement(sql);
        pst.executeUpdate();
        pst.close();
    }

    /**
     * Fills out a poll table with relevant data. The data is read from the given file.
     *
     * @param con          the connection to database.
     * @param databaseData the name of file with data for database.
     * @return the identifiers of the created polls.
     * @throws IOException  if an error occurs while reading the file.
     * @throws SQLException if an error occurs while inserting the data.
     */
    private long[] insertDataIntoPoolsTable(Connection con, String databaseData) throws IOException, SQLException {
        PreparedStatement pst;
        BufferedReader br = Files.newBufferedReader(Paths.get(databaseData));
        String line;

        boolean firstPoll = true;
        boolean secondPoll = true;
        long[] ids = new long[2];

        pst = con.prepareStatement(
                "INSERT INTO Polls (title, message) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
        while ((line = br.readLine()) != null) {
            String[] items = line.split("\\t");
            pst.setString(1, items[0]);
            pst.setString(2, items[1]);
            pst.executeUpdate();

            if (firstPoll && items[2].equals("1")) {
                ids[0] = getId(pst);
                firstPoll = false;
            } else if (secondPoll && items[2].equals("2")) {
                ids[1] = getId(pst);
                secondPoll = false;
            }
        }
        br.close();
        pst.close();
        return ids;

    }

    /**
     * Returns the id associated with the inserted poll.
     *
     * @param pst the prepared statement.
     * @return associate id.
     * @throws SQLException if an error occurs while reading id from the database.
     */
    private long getId(PreparedStatement pst) throws SQLException {
        ResultSet rset = pst.getGeneratedKeys();
        if (rset.next()) {
            return rset.getLong(1);
        }
        throw new SQLException("An error occurred.");
    }

    /**
     * Fills out a poll options table with relevant data. The data is read from the given file.
     *
     * @param con          the connection to database.
     * @param databaseData the name of file with data for database.
     * @throws IOException  if an error occurs while reading the file.
     * @throws SQLException if an error occurs while inserting the data.
     */

    private void insertDataIntoPollOptionsTable(Connection con, String databaseData, long[] ids) throws IOException, SQLException {
        BufferedReader br = Files.newBufferedReader(Paths.get(databaseData));
        PreparedStatement pst;
        String line;
        pst = con.prepareStatement(
                "INSERT INTO PollOptions ( optionTitle, optionLink,pollID , votesCount) VALUES (?,?,?,?)");
        while ((line = br.readLine()) != null) {
            String[] items = line.split("\\t");
            pst.setString(1, items[0]);
            pst.setString(2, items[1]);
            long id = ids[0];
            if (items[2].equals("2")) {
                id = ids[1];
            }
            pst.setLong(3, id);
            pst.setLong(4, 0);
            pst.executeUpdate();
        }
        br.close();
        pst.close();

    }

    /**
     * Returns true if the given table is empty(no records).
     *
     * @param con   the connection to the database.
     * @param table the name of the table in database.
     * @return true if the table is empty.
     * @throws SQLException if an error occurs when communicating with the database.
     */
    private boolean isTableEmpty(Connection con, String table) throws SQLException {
        PreparedStatement pst = con.prepareStatement(
                "SELECT COUNT(*) FROM " + table);
        ResultSet res = pst.executeQuery();
        boolean empty = false;
        if (res.next()) {
            empty = res.getLong(1) == 0;
        }
        pst.close();
        return empty;
    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext().getAttribute("hr.fer.zemris.dbpool");
        if (cpds != null) {
            try {
                DataSources.destroy(cpds);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}