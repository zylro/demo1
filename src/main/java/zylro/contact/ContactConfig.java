package zylro.contact;

import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Configuration for contact app
 *
 * @author wot
 */
public class ContactConfig extends Configuration {

    @NotEmpty
    private String dbConnection;

    public String getDbConnection() {
        return dbConnection;
    }

    public void setDbConnection(String dbConnection) {
        this.dbConnection = dbConnection;
    }

}
