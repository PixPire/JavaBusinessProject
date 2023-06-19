package wipb.jsfcruddemo.web.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class BasketProduct extends AbstractModel {
    @Id
    @ManyToOne
    private Basket basket;

    @Id
    @ManyToOne
    private Product product;

    private BigDecimal numberOfProductsInBasket;

    @ManyToOne(optional = true)
    private Discount discount;

    public BasketProduct() {}

//    public BasketProduct(Basket basket, Product product, BigDecimal numberOfProductsInBasket) {
//        this.basket = basket;
//        this.product = product;
//        this.numberOfProductsInBasket = numberOfProductsInBasket;
//    }

    public BasketProduct(Basket basket, Product product, BigDecimal numberOfProductsInBasket, Discount discount) {
        this.basket = basket;
        this.product = product;
        this.numberOfProductsInBasket = numberOfProductsInBasket;
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Koszyk " + basket.getUser() + "Product = " + product + "  numberOfProductsInBasket = " + numberOfProductsInBasket + "  discount = " + discount;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getNumberOfProductsInBasket() {
        return numberOfProductsInBasket;
    }

    public void setNumberOfProductsInBasket(BigDecimal numberOfProductsInBasket) {
        this.numberOfProductsInBasket = numberOfProductsInBasket;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }
}

