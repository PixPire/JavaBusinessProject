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
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BasketProduct> basketProducts = new ArrayList<>();
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

    public List<BasketProduct> getBasketProducts() {
        return basketProducts;
    }
    public void setBasketProducts(List<BasketProduct> basketProducts) {
        this.basketProducts = basketProducts;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Product other = (Product) obj;
        return name.equals(other.name) && category.equals(other.category) && price.equals(other.price);
    }
}
