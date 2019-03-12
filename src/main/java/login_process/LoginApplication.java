package login_process; // {{ groupId}}.app
// import the rest service you created!
import com.nicolaifsf.rest.HelloRestService;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;
public class LoginApplication extends Application {
    private Set<Object> singletons = new HashSet<Object>();
    public LoginApplication() {
        // Register our hello service
        singletons.add(new Login());
    }
    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}