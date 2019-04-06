
package deadlinesBegone.dao;

import deadlinesBegone.domain.Course;
import java.sql.*;

public class SQLCourseDao extends AbstractNamedObjectDao<Course> {
    
    public SQLCourseDao(Database database, String tableName) {
        super(database, tableName);
    }

    @Override
    public Course create(Course course) throws SQLException {
        try (Connection conn = database.getConnection()) {
            String sql = "INSERT INTO Course (name) VALUES (?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, course.getName());
            stmt.executeUpdate();
        }
        
        return findByName(course.getName());
    }

    @Override
    public Course createFromRow(ResultSet rs) throws SQLException{
        return new Course(rs.getInt("id"), rs.getString("name"));
    }

}
