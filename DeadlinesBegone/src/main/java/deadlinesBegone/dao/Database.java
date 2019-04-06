
package deadlinesBegone.dao;

import java.io.File;
import java.sql.*;

public class Database {
    
    private String databaseAddress;
    
    public Database(String databaseName) throws ClassNotFoundException, SQLException {
        this.databaseAddress = "jdbc:sqlite:"+databaseName;
        File file = new File(databaseName);
        if (!file.exists()) {
            createNewDatabase();
        }
    }
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

    private void createNewDatabase() throws SQLException {
        System.out.println(this.databaseAddress);
        Connection conn = DriverManager.getConnection(this.databaseAddress);
        if (conn != null) {
            DatabaseMetaData meta = conn.getMetaData();
            System.out.println("The driver name is " +meta.getDriverName());
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
                + " course_id integer NOT NULL,\n"
                + " CONSTRAINT fk_course\n"
                + "   FOREIGN KEY (course_id)\n"
                + "   REFERENCES course(course_id)\n"
                + " );";
        
        PreparedStatement courseStmt = conn.prepareStatement(courseTable);
        PreparedStatement assignmentStmt = conn.prepareStatement(assignmentTable);
        courseStmt.execute();
        assignmentStmt.execute();
        
        courseStmt.close();
        assignmentStmt.close();
        
        conn.close();
    }
}
