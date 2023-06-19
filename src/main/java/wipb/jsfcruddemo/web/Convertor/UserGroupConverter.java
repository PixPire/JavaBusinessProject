package wipb.jsfcruddemo.web.Convertor;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import wipb.jsfcruddemo.web.dao.UserGroupDao;
import wipb.jsfcruddemo.web.model.UserGroup;

import java.util.logging.Logger;

//@FacesConverter(value = "userGroupConverter", managed = true)
@Named
@RequestScoped
public class UserGroupConverter implements Converter {
    private static Logger logger = Logger.getLogger(UserGroupConverter.class.getName());

    @Inject
    private UserGroupDao userGroupDao;


    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        logger.severe("CONVERT getAsObject");
//        logger.severe("userGroupDao = " + userGroupDao);
//        logger.severe(value);
        if (value != null && value.trim().length() > 0) {
            try {
                return userGroupDao.findUserGroupByName(value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid value format for UserGroup converter", e);
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        logger.severe("CONVERT getAsString");
        logger.severe(value.toString());
        if (value instanceof UserGroup) {
            return String.valueOf(value);
        }
        return null;
    }
}

