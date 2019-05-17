import com.nicolaifsf.rest.HelloRestService;
import com.sendgrid.*;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.plugins.server.resourcefactory.POJOResourceFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.apache.commons.codec.digest.DigestUtils;

import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;
import static org.apache.commons.codec.digest.DigestUtils.sha384Hex;
import static sql.sqldata.sendgrid_api_key;

public class test1 {
    private static Dispatcher dispatcher;
    private static POJOResourceFactory noDefaults;

    // This code here gets run before our tests begin
    @BeforeClass
    public static void setup() {
        dispatcher = MockDispatcherFactory.createDispatcher();
        noDefaults = new POJOResourceFactory(HelloRestService.class);
        dispatcher.getRegistry().addResourceFactory(noDefaults);
    }

    // One of our actual tests!
    @Test
    public void helloTest() {
        String w="qwerty";
        String sha384hex = sha384Hex(w);
        System.out.println(sha384hex);
        System.out.println(System.getProperty("os.name"));
    }

    @Test
    public void Testmail() {
        try {
            Email from = new Email("admin@cccbd.top");
            String subject = "Sending with SendGrid is Fun";
            Email to = new Email("942202476@qq.com");
            Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
            Mail mail = new Mail(from, subject, to, content);
            //String sendgrid_api_key = System.getenv("SENDGRID_API_KEY");
            SendGrid sg = new SendGrid(sendgrid_api_key);
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            com.sendgrid.Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        System.out.println("hello world".substring(0, 9));
    }
}