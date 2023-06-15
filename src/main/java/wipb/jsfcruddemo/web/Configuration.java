package wipb.jsfcruddemo.web;

import wipb.jsfcruddemo.web.dao.UserDao;
import wipb.jsfcruddemo.web.model.User;

import javax.annotation.PostConstruct;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.faces.annotation.FacesConfig;
import javax.inject.Inject;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.util.HashMap;
import java.util.Map;

@DataSourceDefinition(
    name = "java:global/JsfCrudDemoDataSource",
    className = "org.h2.jdbcx.JdbcDataSource",
    //url = "jdbc:h2:file:./h2data;",
    url = "jdbc:h2:file:D:/zapisy_programow_java/JAVA_BUSINESS/10ps/uwierzytelnianie/h2data",
    //url = "jdbc:h2:mem:jsfcruddemo;DB_CLOSE_DELAY=-1",
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
        groupsQuery = "SELECT ug.name FROM \"USER\" u JOIN USERGROUP ug ON u.id=ug.user_id WHERE u.login = ?",
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

        @Inject
        private Pbkdf2PasswordHash pbkdf;

        @EJB
        private UserDao userDao;

        @PostConstruct
        private void init() {
                Map<String,String> map = new HashMap<>();
                map.put("Pbkdf2PasswordHash.Algorithm","PBKDF2WithHmacSHA512");
                map.put("Pbkdf2PasswordHash.Iterations","3072");
                map.put("Pbkdf2PasswordHash.SaltSizeBytes","64");
                pbkdf.initialize(map);

                initDatabase();
        }

        private void initDatabase() {
                User u = new User();
                u.setLogin("test");
                u.setPassword(pbkdf.generate("test".toCharArray()));
                u.setEmail("test@test.pl");
                u.addGroup("ROLE_USER");
                userDao.save(u);
        }
}

