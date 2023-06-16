package wipb.jsfcruddemo.web.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NamedQueries({
        @NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
})
@Entity
public class Product extends  AbstractModel{
    @ManyToMany(mappedBy = "products")
    private List<Basket> baskets = new ArrayList<>();
    private String name;
    private String category;
    @Column(columnDefinition="Decimal(10,2)")
    private BigDecimal price = new BigDecimal(0);

    public Product(){}
    public Product(String name, String category, BigDecimal price)
    {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", category='" + getCategory() + '\'' +
                ", price='" + getPrice() + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public List<Basket> getBaskets() {
        return baskets;
    }
    public void setBaskets(List<Basket> baskets) {
        this.baskets = baskets;
    }
}
