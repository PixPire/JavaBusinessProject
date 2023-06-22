package wipb.jsfcruddemo.web.service;

import wipb.jsfcruddemo.web.model.Basket;
import wipb.jsfcruddemo.web.model.User;

import javax.mail.MessagingException;

public interface PurchaseService {
    void realizeOrder(User user, Basket basket,String address,String phone) throws MessagingException;
    void archivizeBasket(User user, Basket basket);

}


