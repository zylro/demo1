package zylro.contact;

import io.dropwizard.Configuration;
import javax.validation.constraints.NotNull;

/**
 * Configuration for the contact app
 *
 * @author wot
 */
public class ContactConfig extends Configuration {

    @NotNull
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }

}
