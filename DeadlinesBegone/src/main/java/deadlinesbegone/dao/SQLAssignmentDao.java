
package deadlinesbegone.dao;

import deadlinesbegone.domain.Assignment;
import deadlinesbegone.domain.Course;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLAssignmentDao extends AbstractNamedObjectDao<Assignment> {
    
    private Dao courseDao;

    public SQLAssignmentDao(Database database, String tableName, Dao courseDao) {
        super(database, tableName);
        this.courseDao = courseDao;
    }

    @Override
    public Assignment create(Assignment assignment) throws SQLException {
        try (Connection conn = database.getConnection()) {
            String sql = "INSERT INTO Assignment (name, deadline, course_id) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, assignment.getName());
            stmt.setString(2, assignment.getDate());
            stmt.setInt(3, assignment.getCourse().getId());
            stmt.executeUpdate();
        }
        return findByName(assignment.getName());
    }

    @Override
    public Assignment createFromRow(ResultSet rs) throws SQLException {
        Course course = (Course) courseDao.get(rs.getInt("course_id"));
        return new Assignment(rs.getInt("id"), rs.getString("name"), rs.getString("deadline"), course);
    }

}
