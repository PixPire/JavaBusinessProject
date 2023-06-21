package wipb.jsfcruddemo.web.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@NamedQuery(name = "Discount.findDiscountByName", query ="select d from Discount d where d.name=?1")
public class Discount extends  AbstractModel{
    private String name;
    @Min(0)
    @Max(100)
    private BigDecimal value;
    @Column(nullable = false)
    private LocalDateTime startedDate;
    @Column(nullable = false)
    private LocalDateTime endedDate;
    private Boolean onlyForVips;

    @OneToMany(mappedBy = "discount")
    private List<BasketProduct> basketProducts = new ArrayList<>();

    public Discount()
    {
        this.startedDate = LocalDateTime.now();
    }
    public Discount(String name, BigDecimal value, LocalDateTime endedDate, Boolean onlyForVips) {
        this.name = name;
        this.value = value;
        this.startedDate = LocalDateTime.now();
        this.endedDate = endedDate;
        this.onlyForVips = onlyForVips;
    }
    public Discount(String name, BigDecimal value, LocalDateTime startedDate, LocalDateTime endedDate, Boolean onlyForVips) {
        this.name = name;
        this.value = value;
        this.startedDate = startedDate;
        this.endedDate = endedDate;
        this.onlyForVips = onlyForVips;
    }

    @Override
    public String toString() {
        /*return "Discount{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", value='" + getValue() + '\'' +
                ", startedDate='" + getStartedDate() + '\'' +
                ", endedDate='" + getEndedDate() + '\'' +
                ", onlyForVips='" + getOnlyForVips() + '\'' +
                '}';*/
        StringBuilder sb = new StringBuilder();
        sb.append("Discound ")
                .append(getName())
                .append(" lista produktów: ");

        for (BasketProduct basketProduct : basketProducts) {
            sb.append(basketProduct.getProduct())
                    .append(", ");
        }
        return sb.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDateTime getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(LocalDateTime startedDate) {
        this.startedDate = startedDate;
    }

    public LocalDateTime getEndedDate() {
        return endedDate;
    }

    public void setEndedDate(LocalDateTime endedDate) {
        this.endedDate = endedDate;
    }

    public Boolean getOnlyForVips() {
        return onlyForVips;
    }

    public void setOnlyForVips(Boolean onlyForVips) {
        this.onlyForVips = onlyForVips;
    }

    public void addBasketProduct(BasketProduct basketProduct) {
        basketProducts.add(basketProduct);
        basketProduct.setDiscount(this);
    }

    public void removeBasketProduct(BasketProduct basketProduct) {
        basketProducts.remove(basketProduct);
        basketProduct.setDiscount(null);
    }

    public List<BasketProduct> getBasketProducts() {
        return basketProducts;
    }

    public void setBasketProducts(List<BasketProduct> basketProducts){
        this.basketProducts = basketProducts;
    }
}
