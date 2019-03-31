
package deadlinesBegone.dao;

import deadlinesBegone.domain.Course;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

public class SQLCourseDao implements CourseDao{
    
    private Database database;
    
    public SQLCourseDao(Database database) {
        this.database = database;
    }

    @Override
    public void create(Course course) throws SQLException {
        Connection conn = this.database.getConnection();
        String sql = "INSERT INTO Course (name) VALUES (?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, course.getName());
        
        stmt.execute();
        stmt.close();
        conn.close();
    }

    @Override
    public List<Course> getAll() throws SQLException {
        Connection conn = this.database.getConnection();
        String sql = "SELECT * FROM Course";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        
        List<Course> courses = new ArrayList<>();
        
        while (rs.next()) {
            Course c = new Course(rs.getInt("id"), rs.getString("name"));
            courses.add(c);
        }
        
        stmt.close();
        rs.close();
        conn.close();
        
        return courses;
    }

}
