package wipb.jsfcruddemo.web.controller;

import wipb.jsfcruddemo.web.model.Discount;
import wipb.jsfcruddemo.web.service.DiscountService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

@Named
@ViewScoped
public class DiscountController implements Serializable {
    private static Logger logger = Logger.getLogger(DiscountController.class.getName());
    @EJB
    private DiscountService discountService;
    private List<Discount> discounts;
    private Discount editedDiscount;

    @PostConstruct
    private void init() { discounts = discountService.findAll(); }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public Discount getEditedDiscount() {
        return editedDiscount;
    }

    public void setEditedDiscount(Discount editedDiscount) {
        this.editedDiscount = editedDiscount;
    }

    public void onAddDiscount() {
        editedDiscount = new Discount();
    }

    public void onEditDiscount(Discount c) {
        editedDiscount = c;
    }

    public void onSaveDiscount() {
        if (editedDiscount.getId() == null) {
            discounts.add(editedDiscount);
            logger.severe("editDiscount bylo null");
        }
        logger.severe("editDiscount bylo =" + editedDiscount);

        Discount saved = discountService.save(editedDiscount);
        discounts.replaceAll(c-> c != editedDiscount ? c : saved);

        editedDiscount = null;
        logger.severe("SAVE PRODUCT");
    }

    public void onRemoveDiscount(Discount c) {
        discountService.delete(c.getId());
        discounts.remove(c);
    }

    public void onCancelDiscount() {
        discounts.replaceAll(c-> c != editedDiscount ? c : discountService.findById(editedDiscount.getId()));
        editedDiscount = null;
        logger.severe("CANCEL PRODUCT");
    }
}
