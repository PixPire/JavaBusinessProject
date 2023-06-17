package wipb.jsfcruddemo.web.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@NamedQuery(name = "Basket.findBasketByUser", query ="select b from Basket b where b.user=?1")
public class Basket extends AbstractModel{
    private String basketStatus;
    @OneToOne
    private User user;
    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BasketProduct> basketProducts = new ArrayList<>();

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

        for (BasketProduct basketProduct : basketProducts) {
            sb.append(basketProduct)
                    .append(", ");
        }

        // Remove the trailing comma and space if there are products
        if (!basketProducts.isEmpty()) {
            sb.delete(sb.length() - 2, sb.length());
        }

        return sb.toString();
    }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user;}

    public String getBasketStatus() {
        return basketStatus;
    }

    public void setBasketStatus(String basketStatus) {
        this.basketStatus = basketStatus;
    }

    public List<BasketProduct> getBasketProducts() {
        return basketProducts;
    }

    public void setBasketProducts(List<BasketProduct> basketProducts) {
        this.basketProducts = basketProducts;
    }

    public void addProduct(Product product, BigDecimal numberOfProductsInBasket, BigDecimal specialDiscount) {
        BasketProduct basketProduct = new BasketProduct(this, product, numberOfProductsInBasket, specialDiscount);
        basketProducts.add(basketProduct);
        product.getBasketProducts().add(basketProduct);
    }

    public void removeProduct(Product product) {
        for (Iterator<BasketProduct> iterator = basketProducts.iterator(); iterator.hasNext();) {
            BasketProduct basketProduct = iterator.next();
            if (basketProduct.getBasket().equals(this) && basketProduct.getProduct().equals(product)) {
                iterator.remove();
                basketProduct.getProduct().getBasketProducts().remove(basketProduct);
                basketProduct.setBasket(null);
                basketProduct.setProduct(null);
            }
        }
    }
}
