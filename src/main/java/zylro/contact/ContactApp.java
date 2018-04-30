package zylro.contact;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
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
public class ContactApp extends Application<Configuration> {

    private static final Logger LOG = LoggerFactory.getLogger(ContactApp.class);

    public static void main(String[] args) throws Exception {
        new ContactApp().run(args);
    }

    @Override
    public void run(Configuration config, Environment env) throws Exception {
        ContactDA contactDa = new ContactDA(System.getenv("CONNECTION_STRING"));
        env.healthChecks().register("db", new DbHealthCheck(contactDa));
        env.jersey().register(new ContactResource(contactDa));
        LOG.info("ContactApp initialized.");
    }

}
