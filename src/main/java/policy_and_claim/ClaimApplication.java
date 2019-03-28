package policy_and_claim;

import org.jboss.resteasy.plugins.interceptors.CorsFilter;
import sql.sqlpool;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class ClaimApplication extends Application {
    private Set<Object> singletons = new HashSet<Object>();

    public ClaimApplication() {
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.getAllowedOrigins().add("*");
        corsFilter.setAllowedMethods("OPTIONS, GET, POST, DELETE, PUT, PATCH");
        singletons.add(corsFilter);
        singletons.add(new Claim());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
