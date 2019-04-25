
package deadlinesbegone.domain;

import java.util.List;
import deadlinesbegone.dao.Dao;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class DeadlinesBegoneService {
    private Dao courseDao;
    private Dao assigmentDao;
    private List<Assignment> assignments;
    
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
        assignments = assignments.stream()
                .filter(a -> !a.getCourse().getId().equals(course.getId()))
                .collect(Collectors.toList());
        courseDao.delete(course.getId());
    }
    
    public Course getCourse(Integer id) throws SQLException {
        return (Course) courseDao.get(id);
    }
    
    public Course getCourseByName(String name) throws SQLException {
        return (Course) courseDao.findByName(name);
    }
    
    public Assignment newAssignment(Assignment assignment) throws SQLException {
        Assignment createdAssignment = (Assignment) assigmentDao.create(assignment);
        assignments.add(createdAssignment);
        return createdAssignment;
    }
    
    public List<Assignment> getAssignments() throws SQLException {
        assignments = assigmentDao.getAll();
        return assignments;
    }

    public void markAssignmentDone(Assignment assignment) throws SQLException {
        assignment.setCompleted(true);
        assigmentDao.update(assignment);
    }

    public void deleteAssignment(Assignment assignment) throws SQLException {
        assignments.remove(assignment);
        assigmentDao.delete(assignment.getId());
    }
    
    public List<Assignment> getUndoneAssignments() throws SQLException {
        return assignments.stream().filter(a -> !a.getCompleted()).collect(Collectors.toList());
    }
}
