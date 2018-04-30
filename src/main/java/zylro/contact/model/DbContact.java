package zylro.contact.model;

import org.bson.Document;

/**
 * Represents an internal Db model of a Contact
 *
 * @author wot
 */
public class DbContact extends Contact {

    //mongodb uses this as the identifying field
    public static String ID_FIELD = "_id";
    
    private String id;
    
    //helper constructor to convert to dbContact
    public DbContact(Contact contact) {
        this.id = contact.emailAddress;
        firstName = contact.firstName;
        lastName = contact.lastName;
        emailAddress = contact.emailAddress;
        phoneNumber = contact.phoneNumber;
        status = contact.status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    
    public Document toDb() {
        return new Document("firstName", firstName)
                .append("lastName", lastName)
                //using emailaddress as the identifying field
                .append(ID_FIELD, emailAddress)
                .append("phoneNumber", phoneNumber)
                .append("status", status.toString());
    }
}
