package deadlinesbegone.dao;

import deadlinesbegone.dao.SQLCourseDao;
import deadlinesbegone.dao.Database;
import deadlinesbegone.dao.Dao;
import deadlinesbegone.domain.AbstractNamedObject;
import deadlinesbegone.domain.Course;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;


public class AbstractNamedObjectDaoTest {
    
    public Database database;
    public Dao dao;
    
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    
    @Before
    public void setUp() throws IOException, ClassNotFoundException, SQLException {
        File tempFolder = testFolder.newFolder("folder");
        database = new Database();
        database.setupDatabase(tempFolder.getAbsolutePath()+"/testDB.db");
        dao = new SQLCourseDao(database, "course");
        dao.create(new Course(null, "first"));
        dao.create(new Course(null, "second"));
        dao.create(new Course(null, "third"));
    }

    @Test
    public void getReturnsRightCourse() throws SQLException {
        Course course = (Course) dao.get(2);
        
        assertEquals("second", course.getName());
    }
    
    @Test
    public void deleteRemovesRightObjectFromDatabase() throws SQLException {
        dao.delete(2);
        
        assertEquals(null, dao.get(2));
    }
    
    @Test
    public void getAllReturnsAllObjects() throws SQLException {
        List <AbstractNamedObject> objects = dao.getAll();
        
        assertEquals(3, objects.size());
    }
    
    @Test
    public void findByNameReturnsNullIfDoesNotExist() throws SQLException {
        assertEquals(null, dao.findByName("fifth"));
    }
}
