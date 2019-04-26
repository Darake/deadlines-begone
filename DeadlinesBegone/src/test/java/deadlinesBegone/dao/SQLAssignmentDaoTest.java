package deadlinesBegone.dao;

import deadlinesbegone.dao.Dao;
import deadlinesbegone.dao.Database;
import deadlinesbegone.dao.SQLAssignmentDao;
import deadlinesbegone.dao.SQLCourseDao;
import deadlinesbegone.domain.Assignment;
import deadlinesbegone.domain.Course;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.rules.TemporaryFolder;

public class SQLAssignmentDaoTest {
    
    public Database database;
    public Dao assignmentDao;
    public Course course;
    
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    
    public SQLAssignmentDaoTest() {
    }
    
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
        Dao courseDao = new SQLCourseDao(database, "course");
        assignmentDao = new SQLAssignmentDao(database, "assignment", courseDao);
        course = (Course) courseDao.create(new Course(null, "firstCourse"));
        Assignment assignment = new Assignment(null, "first", "2019/05/01", course, false);
        assignmentDao.create(assignment);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void createReturnsAssignmentWithId() throws SQLException {
        Assignment assignment = new Assignment(null, "second", "2019/05/02", course, false);
        Assignment newAssignment = (Assignment) assignmentDao.create(assignment);
        
        assertEquals(newAssignment.getId(), (Integer) 2);
    }
    
    @Test
    public void createAddsAssignmentIntoDatabase() throws SQLException {
        Assignment assignment = new Assignment(null, "second", "2019/05/02", course, false);
        
        assertEquals(assignmentDao.getAll().size(), 2);
    }
}
