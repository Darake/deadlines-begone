package deadlinesBegone.dao;

import deadlinesbegone.dao.SQLCourseDao;
import deadlinesbegone.dao.Database;
import deadlinesbegone.dao.Dao;
import deadlinesbegone.domain.Course;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;


public class SQLCourseDaoTest {
    
    public Database database;
    public Dao dao;
    
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws IOException, ClassNotFoundException, SQLException {
        File tempFolder = testFolder.newFolder("folder");
        database = new Database(tempFolder.getAbsolutePath()+"/testDB.db");
        dao = new SQLCourseDao(database, "course");
        dao.create(new Course(null, "first"));
        dao.create(new Course(null, "second"));
        dao.create(new Course(null, "third"));
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void getReturnsRightCourse() throws SQLException {
        Course course = (Course)dao.get(2);
        
        assertEquals(course.getName(), "second");
    }
}
