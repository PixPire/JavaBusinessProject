package wipb.jsfcruddemo.web.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@NamedQuery(name = "ArchiveOrder.findOrdersByUser", query ="select ao from ArchiveOrder ao where ao.user=?1")
@NamedQuery(name= "ArchiveOrder.findMaxOrderId", query = "select max(ao.orderId) from ArchiveOrder ao")
public class ArchiveOrder extends AbstractModel {
    @Id
    private Long orderId;
    @ManyToOne
    private User user;
    @ManyToOne
    private ArchiveProduct archiveProduct;

    private BigDecimal numberOfProductsInOrder;

    private BigDecimal discount;

    public ArchiveOrder() {}

    public ArchiveOrder(Long orderId ,User user, ArchiveProduct archiveProduct, BigDecimal numberOfProductsInBasket, BigDecimal discount) {
        this.orderId = orderId;
        this.user = user;
        this.archiveProduct = archiveProduct;
        //archiveProduct.addArchiveOrder(orderId, user, numberOfProductsInBasket, discount);
        this.numberOfProductsInOrder = numberOfProductsInBasket;
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Zamowienie archiwalne " + user + "ArchiveProduct = " + archiveProduct + "  numberOfProductsInOrder = " + numberOfProductsInOrder + "  discount = " + discount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArchiveProduct getArchiveProduct() {
        return archiveProduct;
    }

    public void setArchiveProduct(ArchiveProduct archiveProduct) {
        this.archiveProduct = archiveProduct;
    }

    public BigDecimal getNumberOfProductsInOrder() {
        return numberOfProductsInOrder;
    }

    public void setNumberOfProductsInOrder(BigDecimal numberOfProductsInOrder) {
        this.numberOfProductsInOrder = numberOfProductsInOrder;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ArchiveOrder other = (ArchiveOrder) obj;
        return orderId.equals(other.orderId) && user.equals(other.user) && archiveProduct.equals(other.archiveProduct) && createDate.equals(other.createDate);
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}

