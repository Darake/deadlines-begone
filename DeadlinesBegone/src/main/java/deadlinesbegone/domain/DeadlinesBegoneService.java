
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
    
    public Course newCourse(String name) throws SQLException {
        Course course = new Course(null, name);
        return (Course) courseDao.create(course);
    }
    
    public void deleteCourse(Course course) throws SQLException {
        courseDao.delete(course.getId());
    }
    
    public Course getCourse(Integer id) throws SQLException {
        return (Course) courseDao.get(id);
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

    public void markAssignmentDone(Assignment assignment) throws SQLException {
        assignment.setCompleted(true);
        assigmentDao.update(assignment);
    }

    public void deleteAssignment(Assignment assignment) throws SQLException {
        assigmentDao.delete(assignment.getId());
    }
}
