package wipb.jsfcruddemo.web.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

@NamedQuery(name="User.findByLogin",query = "select u from User u where u.login=:login")
@Entity
public class User extends AbstractModel {
    @Column(unique = true)
    private String login;
    private String password;
    private String email;
    @ManyToOne
    private UserGroup userGroup;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Basket basket;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArchiveOrder> archiveOrders;

    private boolean isVip=false;

    public User() {
    }

    public User(String login, String password, String email, Boolean isVip) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.isVip = isVip;
    }
    public User(String login, String password, String email, Boolean isVip, UserGroup userGroup) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.isVip = isVip;
        userGroup.addUser(this);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", userGroup='" + userGroup.getName() + '\'' +
                ", basket='" + basket.getId() + '\'' +
                '}';
    }

    public boolean getIsVip() {
        return isVip;
    }

    public void setIsVip(boolean vip) {
        isVip = vip;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public List<ArchiveOrder> getArchiveOrders() {
        return archiveOrders;
    }

    public void setArchiveOrders(List<ArchiveOrder> archiveOrders) {
        this.archiveOrders = archiveOrders;
    }
}
