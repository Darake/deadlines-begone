
package deadlinesBegone.domain;

import java.util.List;
import deadlinesBegone.dao.Dao;
import java.sql.SQLException;

public class DeadlinesBegoneService {
    private Dao courseDao;
    private Dao AssignmentDao;
    
    public DeadlinesBegoneService(Dao courseDao, Dao AssignmentDao) {
        this.courseDao = courseDao;
        this.AssignmentDao = AssignmentDao;
    }
    
    public List<Course> getCourses() throws SQLException {
        return courseDao.getAll();
    }
    
    public Course newCourse(Course course) throws SQLException {
        return (Course)courseDao.create(course);
    }
    
    public Course getCourseByName(String name) throws SQLException {
        return (Course)courseDao.findByName(name);
    }
    
    public Assignment newAssignment(Assignment assignment) throws SQLException {
        return (Assignment)AssignmentDao.create(assignment);
    }
    
    public List<Assignment> getAssignments() throws SQLException {
        return AssignmentDao.getAll();
    }
}
