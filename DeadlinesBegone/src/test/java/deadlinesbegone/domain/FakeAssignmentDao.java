
package deadlinesbegone.domain;

import deadlinesbegone.dao.Dao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FakeAssignmentDao implements Dao<Assignment, Integer> {
    
    private List<Assignment> assignments;
    
    public FakeAssignmentDao() {
        assignments = new ArrayList<>();
        Course course1 = new Course(1, "Ohjelmistotekniikka");
        Course course2 = new Course(2, "Fullstack");
        assignments.add(new Assignment(1, "Viikko6", "date", course1, false));
        assignments.add(new Assignment(2, "Viikko7", "date", course1, false));
        assignments.add(new Assignment(3, "React", "date", course2, true));        
    }

    @Override
    public Assignment create(Assignment object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Assignment> getAll() throws SQLException {
        return assignments;
    }

    @Override
    public Assignment get(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Assignment findByName(String name) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Assignment object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
