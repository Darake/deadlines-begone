
package deadlinesbegone.domain;

import deadlinesbegone.dao.Dao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FakeCourseDao implements Dao<Course, Integer> {
    
    private List<Course> courses;
    
    public FakeCourseDao() {
        courses = new ArrayList<>();
        courses.add(new Course(1, "Ohjelmistotekniikka"));
        courses.add(new Course(2, "Fullstack"));
    }

    @Override
    public Course create(Course course) throws SQLException {
        course.setId(courses.size() + 1);
        courses.add(course);
        return course;
    }

    @Override
    public List<Course> getAll() throws SQLException {
        return courses;
    }

    @Override
    public Course get(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Course findByName(String name) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Course object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        courses = courses.stream()
                .filter(c -> c.getId() != key)
                .collect(Collectors.toList());
    }

}
