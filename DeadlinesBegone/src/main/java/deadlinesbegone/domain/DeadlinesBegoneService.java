
package deadlinesbegone.domain;

import java.util.List;
import deadlinesbegone.dao.Dao;
import deadlinesbegone.dao.Database;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Contains main logic for the application. Also acts as a link between DAO and GUI.
 * 
 */
public class DeadlinesBegoneService {
    private Database db;
    private Properties properties;
    private Dao courseDao;
    private Dao assigmentDao;
    private List<Assignment> assignments;
    
    public DeadlinesBegoneService(Database db, Properties properties, Dao courseDao, Dao assigmentDao) {
        this.db = db;
        this.properties = properties;
        this.courseDao = courseDao;
        this.assigmentDao = assigmentDao;
    }
    
    public List<Course> getCourses() throws SQLException {
        return courseDao.getAll();
    }
    
    /**
     * Creates a new course and inserts it into database.
     * 
     * @param Name of the course to be created.
     * @return Newly created Course as object with id.
     * @throws SQLException 
     */
    public Course newCourse(String name) throws SQLException {
        Course course = new Course(null, name);
        return (Course) courseDao.create(course);
    }
    
    /**
     * Deletes course from database while also deleting course's assignments from
     * the assignment list.
     * 
     * @param Course to be deleted
     * @throws SQLException 
     */
    public void deleteCourse(Course course) throws SQLException {
        assignments = assignments.stream()
                .filter(a -> !a.getCourse().getId().equals(course.getId()))
                .collect(Collectors.toList());
        courseDao.delete(course.getId());
    }
    
    /**
     * Gets course from database.
     * 
     * @param id of the wanted course
     * @return Course object
     * @throws SQLException 
     */
    public Course getCourse(Integer id) throws SQLException {
        return (Course) courseDao.get(id);
    }
    
    /**
     * Gets course from database.
     * 
     * @param name of the wanted course
     * @return Course object
     * @throws SQLException 
     */
    public Course getCourseByName(String name) throws SQLException {
        return (Course) courseDao.findByName(name);
    }
    
    /**
     * Inserts assignment into database. Also creates a new assignment with id
     * and adds it into the classes assignment list.
     * 
     * @param assignment
     * @return Assignment with id
     * @throws SQLException 
     */
    public Assignment newAssignment(Assignment assignment) throws SQLException {
        Assignment createdAssignment = (Assignment) assigmentDao.create(assignment);
        assignments.add(createdAssignment);
        return createdAssignment;
    }
    
    /**
     * Gets all assignments from database and saves them in a list in the class.
     * 
     * @return List of Assignments
     * @throws SQLException 
     */
    public List<Assignment> getAssignments() throws SQLException {
        assignments = assigmentDao.getAll();
        return assignments;
    }
    
    /**
     * Sets Assignment's completed value to true and updates it in database.
     * 
     * @param assignment
     * @throws SQLException 
     */
    public void markAssignmentDone(Assignment assignment) throws SQLException {
        assignment.setCompleted(true);
        assigmentDao.update(assignment);
    }

    /**
     * Removes assignment from the database and classes list.
     * 
     * @param assignment
     * @throws SQLException 
     */
    public void deleteAssignment(Assignment assignment) throws SQLException {
        assignments.remove(assignment);
        assigmentDao.delete(assignment.getId());
    }
    
    /**
     * Filters assignments that have a completed value of false in the assignments list.
     * 
     * @return List of Assignments
     * @throws SQLException 
     */
    public List<Assignment> getUndoneAssignments() throws SQLException {
        return assignments.stream().filter(a -> !a.getCompleted()).collect(Collectors.toList());
    }

    /**
     * Adds a .period ending to the new database's name and saves it to the
     * config file. Sets up a new database.
     * 
     * @param Name of the database
     * @throws ClassNotFoundException@author daraku
     * @throws SQLException
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void newDatabase(String name) throws ClassNotFoundException, SQLException, FileNotFoundException, IOException {
        String databaseName = name + ".period";
        properties.setProperty("database", databaseName);
        properties.store(new FileOutputStream("config.properties"), null);
        db.setupDatabase(databaseName);
    }
    
    /**
     * Sets and saves the database path into config file while also changing
     * database's object information.
     * 
     * @param Path to database
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public void loadDatabase(String path) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
        properties.setProperty("database", path);
        properties.store(new FileOutputStream("config.properties"), null);
        db.setupDatabase(path);
    }
    
    /**
     * Checks if config file has a database property.
     * 
     * @return A Boolean value
     */
    public boolean databaseExists() {
        return !(properties.getProperty("database") == null);
    }
}
