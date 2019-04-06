package deadlinesBegone.dao;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T, K> {
    
    T create(T object) throws SQLException;
    
    List<T> getAll() throws SQLException;
    
    T get(K key) throws SQLException;
    
    T findByName(String name) throws SQLException;
}
