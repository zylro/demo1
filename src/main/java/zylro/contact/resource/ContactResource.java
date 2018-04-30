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
import zylro.contact.data.ContactDA;
import zylro.contact.model.Contact;
import zylro.contact.service.ContactValidations;

/**
 * Resource for handling Contact information
 *
 * @author wot
 */
@Path("/contact")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContactResource {

    private final ContactDA contactDa;
    
    public ContactResource(ContactDA contactDa) {
        this.contactDa = contactDa;
    }

    @GET
    public Response getAllContacts() {
        return Response.ok(contactDa.getAllContacts()).build();
    }

    @GET
    @Path("/{emailAddress}")
    public Response getContactByEmail(@PathParam("emailAddress") String emailAddress) {
        ContactValidations.validateEmail(emailAddress);
        return Response.ok(contactDa.getContactByEmail(emailAddress)).build();
    }
    
    @PUT
    public Response updateContact(@Valid Contact contact) {
        ContactValidations.validateEmail(contact);
        contactDa.upsertContact(contact);
        return Response.noContent().build();
    }

    @POST
    public Response createContact(@Valid Contact contact) {
        ContactValidations.validateEmail(contact);
        contactDa.upsertContact(contact);
        return Response.ok(contact).build();
    }

    @DELETE
    public Response deleteContact(@Valid Contact contact) {
        ContactValidations.validateEmail(contact);
        contactDa.deleteContact(contact);
        return Response.noContent().build();
    }
}
