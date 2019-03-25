package mail;

import org.jboss.resteasy.plugins.interceptors.CorsFilter;
import sql.sqlpool;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class MailApplication extends Application {
    private Set<Object> singletons = new HashSet<Object>();

    public MailApplication() {
        new sqlpool();
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.getAllowedOrigins().add("*");
        corsFilter.setAllowedMethods("OPTIONS, GET, POST, DELETE, PUT, PATCH");
        singletons.add(corsFilter);
        singletons.add(new Mail());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
