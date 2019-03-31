package deadlinesBegone.dao;

import java.io.File;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

/**
 *
 * @author daraku
 */
public class DatabaseTest {
    
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void databaseCreatedIfDoesntExist() throws Exception {
        File tempFolder = testFolder.newFolder("folder");
        File file = new File(tempFolder.getAbsolutePath()+"/testDB.db");
        Database db = new Database(tempFolder.getAbsolutePath()+"/testDB.db");
        
        assertEquals(true, file.exists());
    }

}
