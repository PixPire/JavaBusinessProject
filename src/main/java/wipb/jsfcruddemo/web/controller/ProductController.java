package wipb.jsfcruddemo.web.controller;

import wipb.jsfcruddemo.web.model.Product;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import wipb.jsfcruddemo.web.service.ProductService;

@Named
@ViewScoped
public class ProductController implements Serializable {
    private static Logger logger = Logger.getLogger(ProductController.class.getName());
    @EJB
    private ProductService productService;
    private List<Product> products;
    private Product editedProduct;

    @PostConstruct
    private void init() { products = productService.findAll(); }

    public List<Product> getProducts() {
        return products;
    }

    public Product getEditedProduct() {
        return editedProduct;
    }

    public void setEditedProduct(Product editedProduct) {
        this.editedProduct = editedProduct;
    }

    public void onAddProduct() {
        editedProduct = new Product();
    }

    public void onEditProduct(Product c) {
        editedProduct = c;
    }

    public void onSaveProduct() {
        if (editedProduct.getId() == null) {
            products.add(editedProduct);
            logger.severe("editProduct bylo null");
        }
        logger.severe("editProduct bylo =" + editedProduct);

        Product saved = productService.save(editedProduct);
        products.replaceAll(c-> c != editedProduct ? c : saved);

        editedProduct = null;
        logger.severe("SAVE PRODUCT");
    }

    public void onRemoveProduct(Product c) {
        productService.delete(c.getId());
        products.remove(c);
    }

    public void onCancelProduct() {
        products.replaceAll(c-> c != editedProduct ? c : productService.findById(editedProduct.getId()));
        editedProduct = null;
        logger.severe("CANCEL PRODUCT");
    }
}
