package deadlinesbegone.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Database access object interface
 * 
 * @param <T>
 * @param <K> 
 */
public interface Dao<T, K> {
    
    /**
     * Inserts a row created from parameter object into database 
     * table and finds the newly created row from database, creating a new
     * object
     * 
     * @param object to be inserted in database
     * @return object with id
     * @throws SQLException 
     */
    T create(T object) throws SQLException;
    
    /**
     * Selects all from a SQL table and returns a list of objects made out of
     * query's rows.
     * 
     * @return list of objects
     * @throws SQLException 
     */
    List<T> getAll() throws SQLException;
    
    /**
     * Gets row with specific id from table and makes an object out of it.
     * 
     * @param object's id
     * @return Object created from table row
     * @throws SQLException 
     */
    T get(K key) throws SQLException;
    
    /**
     * Gets row with specific name from table and makes an object
     * out of it.
     * 
     * @param Object's name
     * @return Found object
     * @throws SQLException 
     */
    T findByName(String name) throws SQLException;
    
    /**
     * Updates a row with matching id to the parameter object's id in database 
     * table with parameter object's values.
     * 
     * @param object to be updated in database
     * @throws SQLException 
     */
    void update(T object) throws SQLException;
    
    /**
     * Deletes a row with specific id from table.
     * 
     * @param row's id
     * @throws SQLException 
     */
    void delete(K key) throws SQLException;
}
