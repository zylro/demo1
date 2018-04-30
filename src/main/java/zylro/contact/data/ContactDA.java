package zylro.contact.data;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zylro.contact.Utils;
import zylro.contact.model.Contact;
import zylro.contact.model.DbContact;
import zylro.contact.resource.ServiceException;

/**
 * Data access for accessing contacts
 *
 * @author wot
 */
public class ContactDA {

    private final String COLLECTION_NAME = "contact";
    private final MongoCollection<Document> contactData;
    private final MongoDatabase contactMdb;
    private static final Logger LOG = LoggerFactory.getLogger(ContactDA.class);
    private static final UpdateOptions UPSERT = new UpdateOptions().upsert(true);

    public ContactDA(String connectionString) {
        try {
            MongoClientURI uri = new MongoClientURI(connectionString);
            MongoClient mongoClient = new MongoClient(uri);
            contactMdb = mongoClient.getDatabase("contact");
            contactData = contactMdb.getCollection(COLLECTION_NAME);
        } catch (Exception ex) {
            throw new Error("Failed to connect to DB", ex);
        }
        LOG.info("ContactDataAccess initialized");
    }

    public boolean ping() {
        try {
            contactMdb.runCommand(new Document("ping", 1));
        } catch (Exception ex) {
            LOG.error("Failed to ping DB.", ex);
            return false;
        }
        LOG.info("ping successful");
        return true;
    }

    public List<Contact> getAllContacts() {
        FindIterable<Document> fi = contactData.find();
        List<Contact> contacts = new ArrayList<>();
        for (Document doc : fi) {
            contacts.add(convertDocumentToContact(doc));
        }
        LOG.debug("Retrieved " + contacts.size() + " contact(s)");
        return contacts;
    }

    public Contact getContactByEmail(String emailAddress) {
        Document doc
                = contactData.find(eq(DbContact.ID_FIELD, emailAddress)).first();
        if (doc == null) {
            throw new ServiceException(HttpStatus.NOT_FOUND_404, emailAddress);
        }
        LOG.debug("Retrieved 1 contact with email: " + emailAddress);
        return convertDocumentToContact(doc);
    }

    public void upsertContact(Contact contact) {
        DbContact dbContact = new DbContact(contact);
        UpdateResult result = contactData.replaceOne(eq(DbContact.ID_FIELD,
                contact.getEmailAddress()), dbContact.toDb(), UPSERT);
        boolean updateSuccessful = result.isModifiedCountAvailable()
                && result.getMatchedCount() == 1L;
        boolean insertSuccessful = result.getUpsertedId() != null;
        if (!updateSuccessful && !insertSuccessful) {
            LOG.error("Upsert failed: insert {}; update {}", insertSuccessful, updateSuccessful);
            throw new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR_500, "Upsert failed");
        }
    }

    public void deleteContact(Contact contact) {
        //doing soft delete
        contact.setStatus(Contact.Status.Inactive);
        upsertContact(contact);
    }

    private Contact convertDocumentToContact(Document doc) {
        String emailAddress = doc.get(DbContact.ID_FIELD, String.class);
        doc.remove(DbContact.ID_FIELD);
        doc.put("emailAddress", emailAddress);
        return Utils.fromJson(doc.toJson(), Contact.class);
    }
}
