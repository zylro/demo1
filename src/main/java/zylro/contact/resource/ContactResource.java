package zylro.contact.resource;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zylro.contact.Utils;
import zylro.contact.auth.ApiKeyAuth;
import zylro.contact.data.ContactDA;
import zylro.contact.model.Contact;
import zylro.contact.service.ContactValidations;

/**
 * Resource for handling Contact information
 *
 * @author wot
 */
@ApiKeyAuth
@Path("/contact")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContactResource {

    private static final Logger LOG = LoggerFactory.getLogger(ContactResource.class);
    private final ContactDA contactDa;

    public ContactResource(ContactDA contactDa) {
        this.contactDa = contactDa;
        LOG.debug("ContactResource initialized.");
    }

    @GET
    public Response getAllContacts() {
        LOG.info("getAllContacts request");
        return Response.ok(contactDa.getAllContacts()).build();
    }

    @GET
    @Path("/{emailAddress}")
    public Response getContactByEmail(@PathParam("emailAddress") String emailAddress) {
        LOG.info("getContactByEmail request ~ " + emailAddress);
        ContactValidations.validateEmail(emailAddress);
        return Response.ok(contactDa.getContactByEmail(emailAddress)).build();
    }

    @PUT
    public Response updateContact(@Valid Contact contact) {
        LOG.info("updateContact request ~ " + Utils.toJson(contact));
        ContactValidations.validateEmail(contact);
        contactDa.upsertContact(contact);
        return Response.noContent().build();
    }

    @POST
    public Response createContact(@Valid Contact contact) {
        LOG.info("createContact request ~ " + Utils.toJson(contact));
        ContactValidations.validateEmail(contact);
        contactDa.upsertContact(contact);
        return Response.ok(contact).build();
    }

    @DELETE
    public Response deleteContact(@Valid Contact contact) {
        LOG.info("deleteContact request ~ " + Utils.toJson(contact));
        ContactValidations.validateEmail(contact);
        contactDa.deleteContact(contact);
        return Response.noContent().build();
    }
}
