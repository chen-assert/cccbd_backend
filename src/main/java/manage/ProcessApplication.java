package manage;

import org.jboss.resteasy.plugins.interceptors.CorsFilter;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class ProcessApplication extends Application {
    private Set<Object> singletons = new HashSet<Object>();

    public ProcessApplication() {
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.getAllowedOrigins().add("*");
        corsFilter.setAllowedMethods("OPTIONS, GET, POST, DELETE, PUT, PATCH");
        singletons.add(corsFilter);
        singletons.add(new Process());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}