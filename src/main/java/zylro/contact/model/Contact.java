package zylro.contact.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import java.util.HashMap;
import java.util.Map;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * POJO for a contact object
 *
 * @author wot
 */
public class Contact {

    public enum Status {
        Active, Inactive
    }
    protected final Map<String, Object> additionalProperties = new HashMap<>();
    //setting arbituary max lengths on the first/last names
    @Length(max = 30)
    protected String firstName;
    @Length(max = 30)
    protected String lastName;
    @NotNull
    protected String emailAddress;
    @Length(max = 20)
    protected String phoneNumber;
    @NotNull
    protected Status status;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress.toLowerCase();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @JsonAnySetter
    public void setAdditionalProperties(String key, Object value) {
        this.additionalProperties.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }
}
