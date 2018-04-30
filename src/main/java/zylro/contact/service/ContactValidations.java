package zylro.contact.service;

import org.apache.commons.validator.routines.EmailValidator;
import org.eclipse.jetty.http.HttpStatus;
import zylro.contact.model.Contact;
import zylro.contact.resource.ServiceException;

/**
 * Validations for the Contact object
 *
 * @author wot
 */
public class ContactValidations {

    /**
     * Defining a valid email address to be not null and must be valid using
     * apache commons
     *
     * @param contact
     */
    public static void validateEmail(Contact contact) {
        validateEmail(contact.getEmailAddress());
    }

    public static void validateEmail(String emailAddress) {
        EmailValidator emailValidator = EmailValidator.getInstance();
        if (emailAddress == null
                || !emailValidator.isValid(emailAddress)) {
            throw new ServiceException(HttpStatus.UNPROCESSABLE_ENTITY_422, "Invalid Email");
        }
    }
}
