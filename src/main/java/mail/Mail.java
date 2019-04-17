package mail;


import com.sendgrid.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.*;
import java.sql.SQLException;
import java.util.Random;

import static sql.sqldata.sendgrid_api_key;


@Path("/verify_code")
@Produces("text/plain; charset=UTF-8")
public class Mail {

    @GET
    @Path("/")
    public Response sendmail(@QueryParam("address") String address) throws SQLException {
        //if(1==1) return Response.status(200).entity(System.getProperty("user.dir")).build();
        if (address == null) {
            return Response.status(403).entity("you have to set email_address parameter").build();
        }
        try {
            String path=System.getProperty("user.dir")+"/../webapps/RESTHello-1.0-SNAPSHOT/example/B.html";
            String content2=readToString(path);
            Email from = new Email("admin@cccbd.top","Hibernia-Sino");
            String subject = "Verification code from Hibernia-Sino Travel";
            Email to = new Email(address);
            int flag = new Random().nextInt(899999);
            flag += 100000;
            content2 = content2.replace("{accountname}", address);
            content2 = content2.replace("{verify_code}", String.valueOf(flag));
            Content content = new Content("text/html", content2);
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
            return Response.status(200).entity("send success, to:" + address).build();
        } catch (Exception e) {
            return Response.status(403).entity(e).build();
        }
    }

    @POST
    public Response sendmail2(@FormParam("address") String address)
            throws SQLException, ClassNotFoundException {
        return sendmail(address);
    }
    public String readToString(String fileName) throws IOException {
        String encoding = "UTF-8";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }
}