
package deadlinesbegone.domain;

public abstract class AbstractNamedObject {
    
    private Integer id;
    private String name;
    
    public AbstractNamedObject(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public Integer getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
}
