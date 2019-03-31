
package deadlinesBegone.domain;

public class Course {
    
    private int id;
    private String name;
    
    public Course(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public Course(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
}
