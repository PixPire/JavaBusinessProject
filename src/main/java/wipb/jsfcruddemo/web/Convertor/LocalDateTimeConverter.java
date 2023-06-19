package wipb.jsfcruddemo.web.Convertor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

//@FacesConverter("localDateTimeConverter")
@Named
@RequestScoped
public class LocalDateTimeConverter implements Converter {
    private static Logger logger = Logger.getLogger(UserGroupConverter.class.getName());

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        logger.severe("CONVERT getAsObject value = " + value);
        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            logger.severe("formatter = " + formatter);
            return LocalDateTime.parse(value, formatter);
        } catch (Exception e) {
            throw new ConverterException("Invalid LocalDateTime format", e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        logger.severe("CONVERT getAsString value = " + value);
        if (value == null) {
            return "";
        }

        if (!(value instanceof LocalDateTime)) {
            throw new ConverterException("Invalid LocalDateTime object");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return ((LocalDateTime) value).format(formatter);
    }
}

