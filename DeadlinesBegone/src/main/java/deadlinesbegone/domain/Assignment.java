
package deadlinesbegone.domain;

public class Assignment extends AbstractNamedObject {
    
    private String deadline;
    private boolean completed;
    private Course course;

    public Assignment(Integer id, String name, String date, Course course, boolean completed) {
        super(id, name);
        this.deadline = date;
        this.course = course;
        this.completed = completed;
    }
    
    public String getDeadline() {
        return deadline;
    }
    
    public Course getCourse() {
        return course;
    }
    
    public boolean getCompleted() {
        return completed;
    }
    
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

}
