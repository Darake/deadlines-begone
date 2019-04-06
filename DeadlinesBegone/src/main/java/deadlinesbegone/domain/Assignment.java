
package deadlinesbegone.domain;

public class Assignment extends AbstractNamedObject {
    
    private String deadline;
    private Course course;

    public Assignment(Integer id, String name, String date, Course course) {
        super(id, name);
        this.deadline = date;
        this.course = course;
    }
    
    public String getDate() {
        return deadline;
    }
    
    public Course getCourse() {
        return course;
    }

}
