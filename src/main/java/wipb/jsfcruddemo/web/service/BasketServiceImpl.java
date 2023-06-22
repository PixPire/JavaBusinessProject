package wipb.jsfcruddemo.web.service;

import wipb.jsfcruddemo.web.controller.BasketProductController;
import wipb.jsfcruddemo.web.dao.BasketDao;
import wipb.jsfcruddemo.web.model.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

@Stateless
public class BasketServiceImpl implements BasketService {
    private static Logger logger = Logger.getLogger(BasketServiceImpl.class.getName());
    private Properties prop;
    private Session session;
    public BasketServiceImpl()
    {
        prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        session = Session.getInstance(prop, new Authenticator()
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication("pokermonik200@gmail.com","eufbrdfccgfuknzb");
            }
        });
    }
    @EJB
    private BasketDao basketDao;

    @Override
    public Basket save(Basket t) {
        return basketDao.save(t);
    }

    @Override
    public void delete(Long id) {
        basketDao.delete(id);
    }

    @Override
    public Basket findById(Long id) {
        return basketDao.findById(id).orElse(null);
    }
    @Override
    public List<Basket> findAll() {
        return basketDao.findAll();
    }

    @Override
    public Basket findByUser(User user) {
        return basketDao.findByUser(user);
    }

    @Override
    public void addEditProductToBasket(Basket basket, Product product, BigDecimal numberOfProductsInBasket, Discount discount){
        logger.severe("Wywolal sie serwis addProdutToBasket");
        logger.severe("Basket = " + basket + " Product = " + product + " ilosc = " + numberOfProductsInBasket);
        BasketProduct bp = basketDao.findBasketProduct(basket, product);
        logger.severe("Znaleziono " + bp);
        if(bp != null)
            deleteProductFromBasket(basket, product);
        basket.addProduct(product, numberOfProductsInBasket, discount);
        save(basket);
    }

    public Message constructMessage(User user,Basket basket,String address, String phone) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("pokermonik200@gmail.com"));
        message.setSubject("Twoje zamowienie w sklepie");
        message.setRecipient(Message.RecipientType.TO,new InternetAddress(user.getEmail()));

        List<BasketProduct> products = basket.getBasketProducts();
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append("Dzien dobry ").append(user.getLogin()).append(", <br> <br> Dziekujemy za zakupy w naszym sklepie. <br><br> Twoj numer: ").append(phone).append(" <br><br>adres dostawy: ").append(address).append(". <br><br> Oto twoje zamowienie:<br><br>");
        contentBuilder.append("<table style='border-collapse: collapse;'>");
        contentBuilder.append("<tr>")
                .append("<th style='border: 1px solid black;'> Nazwa </th>")
                .append("<th style='border: 1px solid black;'> Cena </th>")
                .append("<th style='border: 1px solid black;'> Ilosc </th>")
                .append("<th style='border: 1px solid black;'> Znizka </th>")
                .append("<th style='border: 1px solid black;'> Suma </th>")
                .append("</tr>");
        BigDecimal suma= new BigDecimal(0);
        BigDecimal finalPrice = new BigDecimal(0);
        BigDecimal obnizka = new BigDecimal(0);
        for (BasketProduct product : products) {
            if(product.getDiscount()!=null)
            {
                obnizka=product.getDiscount().getValue();
            }
            finalPrice=product.getNumberOfProductsInBasket().multiply(product.getProduct().getPrice()).multiply((obnizka.subtract(new BigDecimal(100)))).divide(new BigDecimal(100).negate());
            suma=suma.add(finalPrice);
            contentBuilder.append("<tr>")
                    .append("<td style='border: 1px solid black;'>").append(product.getProduct().getName()).append("</td>")
                    .append("<td style='border: 1px solid black;'>").append(product.getProduct().getPrice()).append("</td>")
                    .append("<td style='border: 1px solid black;'>").append(product.getNumberOfProductsInBasket()).append("</td>")
                    .append("<td style='border: 1px solid black;'>").append(obnizka).append("</td>")
                    .append("<td style='border: 1px solid black;'>").append(finalPrice).append("</td>")
                    .append("</tr>");
        }
        contentBuilder.append("<tr> <td></td> <td></td> <td></td> <td></td> <td style='border: 1px solid black;'> ").append(suma).append("<td> </tr>");

        contentBuilder.append("</table>");
        contentBuilder.append("<br><br> Zapraszamy ponownie");
        message.setContent(contentBuilder.toString(), "text/html");

        return message;
    }
    @Override
    public void realizeOrder(User user,Basket basket, String address, String phone) throws MessagingException {
            Transport.send(constructMessage(user,basket,address,phone));
            logger.severe("Udało się wysłać mail");
    }

    @Override
    public void deleteProductFromBasket(Basket basket, Product product) {
        logger.severe("Usuwanie produktu z koszyka");
        basket.removeProduct(product);
        save(basket);
    }

    @Override
    public void clearBasket(Basket basket) {
        basket.clearBasket();
        save(basket);
    }
}
