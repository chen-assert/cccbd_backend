package transaction;

import mail.Mail;
import org.jboss.resteasy.plugins.interceptors.CorsFilter;
import sql.sqlpool;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class TransactionApplication extends Application {
    private Set<Object> singletons = new HashSet<Object>();

    public TransactionApplication() {
        new sqlpool();
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.getAllowedOrigins().add("*");
        corsFilter.setAllowedMethods("OPTIONS, GET, POST, DELETE, PUT, PATCH");
        singletons.add(corsFilter);
        singletons.add(new Transaction());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
