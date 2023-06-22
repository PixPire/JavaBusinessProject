package wipb.jsfcruddemo.web.controller;

import wipb.jsfcruddemo.web.bean.UserBean;
import wipb.jsfcruddemo.web.model.Discount;
import wipb.jsfcruddemo.web.model.User;
import wipb.jsfcruddemo.web.service.DiscountService;
import wipb.jsfcruddemo.web.service.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import static wipb.jsfcruddemo.web.controller.BasketProductController.redirect;

@Named
@ViewScoped
public class DiscountController implements Serializable {
    private static Logger logger = Logger.getLogger(DiscountController.class.getName());
    @EJB
    private DiscountService discountService;
    private List<Discount> discounts;
    private Discount editedDiscount;

    @Inject
    private UserBean userBean;
    @EJB
    private UserService userService;

    @PostConstruct
    private void init() {
        discounts = discountService.findAll();

        String login = userBean.getLogin();
        User actualUser = userService.findByLogin(login);
        if(actualUser == null)
            redirect("/index.xhtml");
    }

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
