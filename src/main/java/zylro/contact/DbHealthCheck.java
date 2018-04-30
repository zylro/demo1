package zylro.contact;

import com.codahale.metrics.health.HealthCheck;
import zylro.contact.data.ContactDA;

/**
 * Simple Db Health Check
 * @author wot
 */
public class DbHealthCheck extends HealthCheck{

    private final ContactDA db;
    public DbHealthCheck(ContactDA db) {
        this.db = db;
    }
    
    @Override
    protected Result check() throws Exception {
        if (db.ping()) {
            return HealthCheck.Result.healthy();
        }
        return HealthCheck.Result.unhealthy("Failed to ping mongo :(");
    }
    
}
