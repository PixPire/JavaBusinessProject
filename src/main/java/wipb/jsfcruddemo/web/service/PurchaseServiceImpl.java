package wipb.jsfcruddemo.web.service;

import wipb.jsfcruddemo.web.dao.ArchiveOrderDao;
import wipb.jsfcruddemo.web.dao.BasketDao;
import wipb.jsfcruddemo.web.model.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Stateless
public class PurchaseServiceImpl implements PurchaseService {
    private static Logger logger = Logger.getLogger(PurchaseServiceImpl.class.getName());
    private Properties prop;
    private Session session;
    public PurchaseServiceImpl()
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


    @EJB
    private ArchiveProductService archiveProductService;
    @EJB
    private ArchiveOrderService archiveOrderService;
    @EJB
    private ArchiveOrderDao archiveOrderDao;

    @Override
    public void archivizeBasket(User user, Basket basket) {
        logger.severe("ARCHIVIZE WYWOLANE");
        Long orderId = archiveOrderDao.findMaxOrderId() + 1L;//(long)archiveProductService.findAll().size() + 1L;
        logger.severe(" orderId = " + orderId);
        for(BasketProduct bp: basket.getBasketProducts()){

            Product p = bp.getProduct();
            ArchiveProduct ap = new ArchiveProduct(p.getName(), p.getCategory(), p.getPrice());
            archiveProductService.save(ap);
            logger.severe("zapisano Ap = " + ap);
            logger.severe("BP = " + bp + " user = " + user);
            ArchiveOrder ao;
            if(bp.getDiscount() != null)
                ao = new ArchiveOrder(orderId, user, ap, bp.getNumberOfProductsInBasket(), bp.getDiscount().getValue());
            else
                ao = new ArchiveOrder(orderId, user, ap, bp.getNumberOfProductsInBasket(), new BigDecimal(0));
            logger.severe("Stworzono Ao = " + ao);
            archiveOrderService.save(ao);
            logger.severe("zapisano Ao = " + ao);
        }
    }
}
