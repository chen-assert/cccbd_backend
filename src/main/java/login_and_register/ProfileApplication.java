package login_and_register;

import org.jboss.resteasy.plugins.interceptors.CorsFilter;
import sql.sqldata;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class ProfileApplication extends Application {
    private Set<Object> singletons = new HashSet<Object>();

    public ProfileApplication() {
        new sqldata();
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.getAllowedOrigins().add("*");
        corsFilter.setAllowedMethods("OPTIONS, GET, POST, DELETE, PUT, PATCH");
        singletons.add(corsFilter);
        singletons.add(new Profile());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}