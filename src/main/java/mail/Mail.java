package mail;


import com.sendgrid.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.Random;

import static sql.sqldata.sendgrid_api_key;


@Path("/mail")
public class Mail {
    @GET
    @Path("/")
    @Produces("text/plain; charset=utf-8")
    public Response sendmail(@QueryParam("email_address") String address) throws SQLException {
        if (address == null) {
            return Response.status(403).entity("you have to set email_address parameter").build();
        }
        try {
            Email from = new Email("admin@cccbd.top");
            String subject = "Verification code from Hibernia-Sino Travel";
            Email to = new Email(address);
            int flag = new Random().nextInt(899999);
            flag+=100000;
            String content2 = "" +
                    "Dear " + address + ",\n" +
                    "Here is the your verification code \n" +
                    flag;
            Content content = new Content("text/plain", content2);
            com.sendgrid.Mail mail = new com.sendgrid.Mail(from, subject, to, content);
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
            return Response.status(200).entity("send success, your email address is:" + address +
                    ", 另外,目前用的API每天只能发送100封邮件").build();
        } catch (Exception e) {
            return Response.status(403).entity(e).build();
        }
    }
}
