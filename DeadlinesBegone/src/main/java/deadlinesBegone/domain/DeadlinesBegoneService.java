
package deadlinesBegone.domain;

import deadlinesBegone.dao.CourseDao;
import java.util.List;

public class DeadlinesBegoneService {
    private CourseDao courseDao;
    
    public DeadlinesBegoneService(CourseDao courseDao) {
        this.courseDao = courseDao;
    }
    
    public List<Course> getCourses() throws Exception {
        return this.courseDao.getAll();
    }
    
    public void newCourse(Course course) throws Exception {
        this.courseDao.create(course);
    }
}
