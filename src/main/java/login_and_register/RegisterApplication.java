package login_and_register; // {{ groupId}}.app
// import the rest service you created!
import org.jboss.resteasy.plugins.interceptors.CorsFilter;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class RegisterApplication extends Application {
    private Set<Object> singletons = new HashSet<Object>();
    public RegisterApplication() {
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.getAllowedOrigins().add("*");
        corsFilter.setAllowedMethods("OPTIONS, GET, POST, DELETE, PUT, PATCH");
        singletons.add(corsFilter);
        // Register our hello service
        singletons.add(new Register());
    }
    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}