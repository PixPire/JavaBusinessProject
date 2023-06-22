package wipb.jsfcruddemo.web;

import wipb.jsfcruddemo.web.dao.*;
import wipb.jsfcruddemo.web.model.*;
import javax.annotation.PostConstruct;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.faces.annotation.FacesConfig;
import javax.inject.Inject;
import javax.mail.*;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

@DataSourceDefinition(
    name = "java:global/JsfCrudDemoDataSource",
    className = "org.h2.jdbcx.JdbcDataSource",
    //url = "jdbc:h2:file:./h2data;",
    //url = "jdbc:h2:mem:jsfcruddemo;DB_CLOSE_DELAY=-1",
    url = "jdbc:h2:file:D:/zapisy_programow_java/JAVA_BUSINESS/10ps/uwierzytelnianie/h2data",

    minPoolSize = 1,
    initialPoolSize = 1,
    user = "sa",
    password = ""
)
@CustomFormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "/login.xhtml",
                errorPage = "",
                useForwardToLogin = false
        )
)
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "java:global/JsfCrudDemoDataSource",
        callerQuery = "SELECT password FROM \"USER\" WHERE login = ?",
        groupsQuery = "SELECT ug.name FROM \"USER\" u JOIN USERGROUP ug ON u.usergroup_id=ug.id WHERE u.login = ?",
        hashAlgorithm = Pbkdf2PasswordHash.class,
        hashAlgorithmParameters = {
                "Pbkdf2PasswordHash.Algorithm=PBKDF2WithHmacSHA512",
                "Pbkdf2PasswordHash.Iterations=3072",
                "Pbkdf2PasswordHash.SaltSizeBytes=64",
        }

)
@FacesConfig // konfiguruje JSF w wersji 2.3, wymagane żeby działało wstrzykiwanie ExternalContext w LoginController
@Singleton
@Startup
public class Configuration {
        private static Logger logger = Logger.getLogger(Configuration.class.getName());

        @Inject
        private Pbkdf2PasswordHash pbkdf;

        @EJB
        private UserDao userDao;

        @EJB
        private UserGroupDao userGroupDao;

        @PostConstruct
        private void init(){
                Map<String,String> map = new HashMap<>();
                map.put("Pbkdf2PasswordHash.Algorithm","PBKDF2WithHmacSHA512");
                map.put("Pbkdf2PasswordHash.Iterations","3072");
                map.put("Pbkdf2PasswordHash.SaltSizeBytes","64");
                pbkdf.initialize(map);
                Properties prop = new Properties();
                prop.put("mail.smtp.auth", true);
                prop.put("mail.smtp.host", "smtp.gmail.com");
                prop.put("mail.smtp.port", "587");
                prop.put("mail.smtp.starttls.enable", "true");
                prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
                Session session = Session.getInstance(prop, new Authenticator()
                {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication()
                        {
                                return new PasswordAuthentication("pokermonik200@gmail.com","eufbrdfccgfuknzb");
                        }
                });
                initDatabase();
        }

        @EJB
        private ProductDao productDao;
        @EJB
        private BasketDao basketDao;
        @EJB
        private DiscountDao discountDao;

        private void initDatabase(){
                logger.severe("Wywolano inicjalizacje bazy danych");

                List<UserGroup> userGroupList = userGroupDao.findAll();
                logger.severe("Znaleziono " + userGroupList.size()+ " grup: ");
                for (UserGroup ug: userGroupList) {
                        logger.severe(ug.toString());
                }
                if(userGroupList.size() == 0)
                {
                        logger.severe("Baza danych jest pusta / nie ma jej");
                        UserGroup ugAdmin = new UserGroup("ROLE_ADMIN");
                        UserGroup ugManager = new UserGroup("ROLE_MANAGER");
                        UserGroup ugClient = new UserGroup("ROLE_CLIENT");
                        userGroupDao.save(ugAdmin);
                        userGroupDao.save(ugManager);
                        userGroupDao.save(ugClient);
                        logger.severe("Utworzono grupy");

                        User u0 = new User("manager", pbkdf.generate("manager".toCharArray()), "manager@manager.pl", true ,ugManager);
                        Basket b0 = new Basket("Created", u0);
                        basketDao.save(b0);
                        userDao.save(u0);

                        User u = new User("client", pbkdf.generate("client".toCharArray()), "client@client.pl", false ,ugClient);
                        userDao.save(u);

                        Product p = new Product("produkt testowy1", "test", new BigDecimal(100.99));
                        productDao.save(p);
                        Product p2 = new Product("produkt testowy2", "test", new BigDecimal(2000));
                        productDao.save(p2);
                        Product p4 = new Product("produkt testowy9000", "test", new BigDecimal(9000));
                        productDao.save(p4);
                        Basket b = new Basket("Created", u);

                        LocalDateTime endedDate = LocalDateTime.now().plusYears(1);

                        logger.severe("daty dzialaja");

                        Discount d1 = new Discount("super", new BigDecimal(50),  endedDate, false);
                        discountDao.save(d1);
                        Discount d2 = new Discount("ok", new BigDecimal(10),  endedDate, true);
                        discountDao.save(d2);
                        Discount d3 = new Discount("vip", new BigDecimal(10),  endedDate, true);
                        discountDao.save(d3);
                        Discount d4 = new Discount("tak", new BigDecimal(10),  endedDate, false);
                        discountDao.save(d4);
                        LocalDateTime s1 = LocalDateTime.now().minusYears(1);
                        LocalDateTime s2 = LocalDateTime.now().minusDays(3);
                        Discount d5 = new Discount("nie", new BigDecimal(10),  s1, s2, false);
                        discountDao.save(d5);

                        logger.severe("zapisano discount = " + d1);

                        b.addProduct(p, new BigDecimal(1), d1);
                        b.addProduct(p2, new BigDecimal(2), d1);
                        b.addProduct(p4, new BigDecimal(4), null);
                        basketDao.save(b);

                        logger.severe("Zapisano klienta = " + u);

                        User u2 = new User();
                        u2.setLogin("admin");
                        u2.setPassword(pbkdf.generate("admin".toCharArray()));
                        u2.setEmail("piotr.szumowski@wp.pl");
                        u2.setUserGroup(ugAdmin);

                        Product p5 = new Product("produkt testowy5", "test", new BigDecimal(0.5));
                        productDao.save(p5);
                        Basket b2 = new Basket("Created", u2);
                        b2.addProduct(p5, new BigDecimal(5000), d1);
                        b2.addProduct(p2, new BigDecimal(2000), d1);
                        basketDao.save(b2);
                        userDao.save(u2);

                        Product p3 = new Product("produkt testowy3", "test", new BigDecimal(0.1));
                        productDao.save(p3);
                }
        }

        public String generateHashedPassword(String password){
                return pbkdf.generate(password.toCharArray());
        }
}

