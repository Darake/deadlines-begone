
package deadlinesbegone.dao;

import java.io.File;
import java.sql.*;
import org.sqlite.SQLiteConfig;

/**
 * Class to handle SQLite database creation and connection
 */
public class Database {
    
    private String databaseAddress;
    private String databaseDriver;
    
    /**
     * Creates a new database if database with the parameter name doesn't exist.
     * Otherwise saves the database address for future use.
     * 
     * @param Database's name
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public Database(String databaseName) throws ClassNotFoundException, SQLException {
        this.databaseAddress = "jdbc:sqlite:" + databaseName;
        this.databaseDriver = "org.sqlite.JDBC";
        Class.forName(databaseDriver);
        File file = new File(databaseName);
        if (!file.exists()) {
            createNewDatabase();
        }
    }
    
    /**
     * Returns a connection to the database with foreign keys enforced.
     * 
     * @return Database connection
     * @throws SQLException 
     */
    public Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            connection = DriverManager.getConnection(databaseAddress, config.toProperties());
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return connection;
    }

    private void createNewDatabase() throws SQLException {
        System.out.println(this.databaseAddress);
        Connection conn = DriverManager.getConnection(this.databaseAddress);
        if (conn != null) {
            DatabaseMetaData meta = conn.getMetaData();
            System.out.println("The driver name is " + meta.getDriverName());
            System.out.println("A new database has been created");
        }
        
        createTables(conn);
    }

    private void createTables(Connection conn) throws SQLException {
        String courseTable = "CREATE TABLE Course (\n"
                + " id integer PRIMARY KEY,\n"
                + " name varchar(50) NOT NULL\n"
                + " );";
        String assignmentTable = "CREATE TABLE Assignment (\n"
                + " id integer PRIMARY KEY,\n"
                + " name varchar(100) NOT NULL,\n"
                + " deadline varchar(10) NOT NULL,\n"
                + " completed boolean NOT NULL,\n"
                + " course_id integer NOT NULL,\n"
                + " CONSTRAINT fk_course\n"
                + "   FOREIGN KEY (course_id)\n"
                + "   REFERENCES Course(id)\n"
                + "   ON DELETE CASCADE\n"
                + " );";
        
        conn.prepareStatement(courseTable).execute();
        conn.prepareStatement(assignmentTable).execute();
  
        conn.close();
    }
}
