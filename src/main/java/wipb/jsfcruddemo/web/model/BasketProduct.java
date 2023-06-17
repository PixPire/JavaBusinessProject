package wipb.jsfcruddemo.web.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
    private BigDecimal specialDiscount;

    public BasketProduct() {}

    public BasketProduct(Basket basket, Product product, BigDecimal numberOfProductsInBasket, BigDecimal specialDiscount) {
        this.basket = basket;
        this.product = product;
        this.numberOfProductsInBasket = numberOfProductsInBasket;
        this.specialDiscount = specialDiscount;
    }

    @Override
    public String toString() {
        return "Koszyk " + basket.getUser() + "Product = " + product + "  numberOfProductsInBasket = " + numberOfProductsInBasket + "  specialDiscount = " + specialDiscount;
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

    public BigDecimal getSpecialDiscount() {
        return specialDiscount;
    }

    public void setSpecialDiscount(BigDecimal specialDiscount) {
        this.specialDiscount = specialDiscount;
    }
}

