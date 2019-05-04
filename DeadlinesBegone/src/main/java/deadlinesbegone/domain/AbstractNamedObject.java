
package deadlinesbegone.domain;

/**
 * Abstract class for objects with a name and an id.
 *
 */
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
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
}
