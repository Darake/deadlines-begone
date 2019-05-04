package deadlinesbegone.dao;

import deadlinesbegone.dao.Database;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

public class DatabaseTest {
    
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    
    @Test
    public void databaseCreatedIfDoesntExist() throws Exception {
        File tempFolder = testFolder.newFolder("folder");
        File file = new File(tempFolder.getAbsolutePath()+"/testDB.db");
        Database db = new Database();
        db.setupDatabase(tempFolder.getAbsolutePath()+"/testDB.db");
        
        assertEquals(true, file.exists());
    }
    
    @Test
    public void getConnectionReturnsValidConnection() throws IOException, ClassNotFoundException, SQLException {
        File tempFolder = testFolder.newFolder("folder");
        Database db = new Database();
        db.setupDatabase(tempFolder.getAbsolutePath()+"/testDB.db");
        Connection connection = db.getConnection();
        
        assertEquals(true, connection != null);
    }

}
