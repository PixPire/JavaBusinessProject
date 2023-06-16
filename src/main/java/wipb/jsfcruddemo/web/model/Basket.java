package wipb.jsfcruddemo.web.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQuery(name = "Basket.findBasketByUser", query ="select b from Basket b where b.user=?1")
public class Basket extends AbstractModel{
    private String basketStatus;
    @OneToOne
    private User user;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "basket_product",
            joinColumns = @JoinColumn(name = "basket_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();

    public Basket() {}

    public Basket(String basketStatus, User user)
    {
        this.basketStatus = basketStatus;
        this.user = user;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Basket ")
                .append(user.toString())
                .append(" lista produktów: ");

        for (Product product : products) {
            sb.append(product.getName())
                    .append(", ");
        }

        // Remove the trailing comma and space if there are products
        if (!products.isEmpty()) {
            sb.delete(sb.length() - 2, sb.length());
        }

        return sb.toString();
    }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user;}

    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    public void addProduct(Product product) {
        products.add(product);
        product.getBaskets().add(this);
    }
    public void removeProduct(Product product) {
        products.remove(product);
        product.getBaskets().remove(this);
    }

    public String getBasketStatus() {
        return basketStatus;
    }

    public void setBasketStatus(String basketStatus) {
        this.basketStatus = basketStatus;
    }
}
