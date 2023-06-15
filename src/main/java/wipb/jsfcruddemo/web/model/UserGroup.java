package wipb.jsfcruddemo.web.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class UserGroup extends AbstractModel {
    private String name;

    public UserGroup() {
    }

    public UserGroup(String name, User user) {
        this.name = name;
        this.user = user;
    }

    @ManyToOne
    private User user;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
