
package deadlinesbegone.domain;

import java.util.List;
import deadlinesbegone.dao.Dao;
import java.sql.SQLException;

public class DeadlinesBegoneService {
    private Dao courseDao;
    private Dao assigmentDao;
    
    public DeadlinesBegoneService(Dao courseDao, Dao assigmentDao) {
        this.courseDao = courseDao;
        this.assigmentDao = assigmentDao;
    }
    
    public List<Course> getCourses() throws SQLException {
        return courseDao.getAll();
    }
    
    public Course newCourse(Course course) throws SQLException {
        return (Course) courseDao.create(course);
    }
    
    public Course getCourseByName(String name) throws SQLException {
        return (Course) courseDao.findByName(name);
    }
    
    public Assignment newAssignment(Assignment assignment) throws SQLException {
        return (Assignment) assigmentDao.create(assignment);
    }
    
    public List<Assignment> getAssignments() throws SQLException {
        return assigmentDao.getAll();
    }
}
