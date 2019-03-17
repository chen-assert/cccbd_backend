package login_and_register; // {{ groupId}}.app
// import the rest service you created!
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class RegisterApplication extends Application {
    private Set<Object> singletons = new HashSet<Object>();
    public RegisterApplication() {
        // Register our hello service
        singletons.add(new Register());
    }
    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}