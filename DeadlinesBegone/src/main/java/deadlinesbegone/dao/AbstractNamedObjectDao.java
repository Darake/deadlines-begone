
package deadlinesbegone.dao;

import deadlinesbegone.domain.AbstractNamedObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class provides SQL actions for AbstractNamedObjects.
 *
 * @param <T> 
 */

public abstract class AbstractNamedObjectDao<T extends AbstractNamedObject>
        implements Dao<T, Integer> {
    
    protected Database database;
    protected String tableName;
    
    public AbstractNamedObjectDao(Database database, String tableName) {
        this.database = database;
        this.tableName = tableName;
    }
    
    
    @Override
    public List<T> getAll() throws SQLException {
        List<T> courses = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;
        
        try (Connection conn = database.getConnection();
            ResultSet rs = conn.prepareStatement(sql).executeQuery()) {
            
            while (rs.next()) {
                courses.add(createFromRow(rs));
            }
        }
                 
        return courses;
    }
    
    
    @Override
    public T get(Integer id) throws SQLException {
        try (Connection conn = database.getConnection()) {
            String sql = "SELECT * FROM " + tableName + " WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                return createFromRow(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error when looking for a row in " + tableName + " with id " + id);
            e.printStackTrace();
            return null;
        }
    }
    
    public T findByName(String name) throws SQLException {
        try (Connection conn = database.getConnection()) {
            String sql = "SELECT * FROM " + tableName + " WHERE name = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                return createFromRow(rs);
            }
        }   
    }
    
    public void delete(Integer id) throws SQLException {
        try (Connection conn = database.getConnection()) {
            String sql = "DELETE FROM " + tableName + " WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    
    @Override
    public abstract T create(T object) throws SQLException;
    
    @Override
    public abstract void update(T object) throws SQLException;

    protected abstract T createFromRow(ResultSet rs) throws SQLException;
}
