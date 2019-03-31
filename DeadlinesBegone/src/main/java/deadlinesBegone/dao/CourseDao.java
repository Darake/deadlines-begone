package deadlinesBegone.dao;

import deadlinesBegone.domain.Course;
import java.util.List;

public interface CourseDao {
    
    void create(Course course) throws Exception;
    
    List<Course> getAll() throws Exception;
}
