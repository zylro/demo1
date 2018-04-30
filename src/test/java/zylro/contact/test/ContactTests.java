package zylro.contact.test;

import org.eclipse.jetty.http.HttpStatus;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Test;
import zylro.contact.model.Contact;
import zylro.contact.resource.ServiceException;
import zylro.contact.service.ContactValidations;

/**
 * Unit tests for basic functionality
 *
 * @author wot
 */
public class ContactTests {

    private static Contact c;

    @BeforeClass
    public static void init() {
        c = new Contact();
    }

    @Test
    public void nullEmail() {
        try {
            ContactValidations.validateEmail(c);
        } catch (ServiceException ex) {
            assertEquals(HttpStatus.UNPROCESSABLE_ENTITY_422, ex.getResponse().getStatus());
            return;
        }
        fail("Not a valid email");
    }

    @Test
    public void invalidEmail() {
        c.setEmailAddress("asd");
        try {
            ContactValidations.validateEmail(c);
        } catch (ServiceException ex) {
            assertEquals(HttpStatus.UNPROCESSABLE_ENTITY_422, ex.getResponse().getStatus());
            return;
        }
        fail("Not a valid email");
    }

    @Test
    public void validEmail() {
        c.setEmailAddress("asd@asd.com");
        try {
            ContactValidations.validateEmail(c);
        } catch (ServiceException ex) {
            fail("Valid email");
        }
    }
}
