package zylro.contact;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import zylro.contact.data.ContactDA;
import zylro.contact.resource.ContactResource;

/**
 * Main application class to startup the service
 *
 * @author wot
 */
public class ContactApp extends Application<ContactConfig> {

    private static final Logger LOG = LoggerFactory.getLogger(ContactApp.class);

    public static void main(String[] args) throws Exception {
        new ContactApp().run(args);
    }

    @Override
    public void run(ContactConfig config, Environment env) throws Exception {
        ContactDA contactDa = new ContactDA(config.getDbConnection());
        env.healthChecks().register("db", new DbHealthCheck(contactDa));
        env.jersey().register(new ContactResource(contactDa));
        LOG.info("ContactApp initialized.");
    }

}
