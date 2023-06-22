package wipb.jsfcruddemo.web.model;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ArchiveProduct extends  AbstractModel{
    @OneToMany(mappedBy = "archiveProduct", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArchiveOrder> archiveOrders = new ArrayList<>();
    private String name;
    private String category;
    @Column(columnDefinition="Decimal(10,2)")
    private BigDecimal price = new BigDecimal(0);

    public ArchiveProduct(){}
    public ArchiveProduct(String name, String category, BigDecimal price)
    {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    @Override
    public String toString() {
        return "ArchiveProduct{" +
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

    public List<ArchiveOrder> getArchiveOrders() {
        return archiveOrders;
    }

    public void setArchiveOrders(List<ArchiveOrder> archiveOrders) {
        this.archiveOrders = archiveOrders;
    }

    public void addArchiveOrder(Long orderId, User user, BigDecimal numberOfProductsInBasket, BigDecimal discount) {
        ArchiveOrder archiveOrder = new ArchiveOrder(orderId, user, this, numberOfProductsInBasket, discount);
        archiveOrders.add(archiveOrder);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ArchiveProduct other = (ArchiveProduct) obj;
        return name.equals(other.name) && category.equals(other.category) && price.equals(other.price);
    }
}
