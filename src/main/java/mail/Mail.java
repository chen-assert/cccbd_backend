package mail;


import com.sendgrid.*;
import data.MyMessage;
import sql.sqlpool;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import static sql.sqldata.sendgrid_api_key;


@Path("/verify_code")
@Produces("text/plain; charset=UTF-8")
public class Mail {
    /**
     * @apiGroup Login and Register
     * @api {post}   /verify_code/new_account  send verify code for register
     * @apiParam {String} address 邮箱地址
     * @apiSuccess (200) {String} Response
     */
    @POST
    @Path("/new_account")
    public Response new_account2(@FormParam("address") String address)
            throws SQLException {
        return new_account(address);
    }


    @GET
    @Path("/new_account")
    public Response new_account(@QueryParam("address") String address) throws SQLException {
        Connection conn;
        if (address == null) {
            return Response.status(403).entity("you have to set email_address parameter").build();
        }
        try {
            conn = new sqlpool().getSingletons().getConnection();
        } catch (Exception e) {
            MyMessage m = new MyMessage("sql fail");
            return Response.status(403).entity(m).build();
        }
        PreparedStatement checkps = conn.prepareStatement("select * from User where email=?");
        checkps.setString(1, address);
        ResultSet resultSet = checkps.executeQuery();
        if (resultSet.next()) {
            return Response.status(403).entity("this address has been used").build();
        }
        try {
            int flag = send_email(address);
            PreparedStatement ps = conn.prepareStatement(
                    "REPLACE INTO Email_verification(email_address,create_time,verify_code) VALUES (?,?,?)");
            ps.setString(1, address);
            ps.setTimestamp(2, getCurrentTimeStamp());
            ps.setString(3, String.valueOf(flag));
            ps.execute();
            return Response.status(200).entity("Sent successfully").build();
        } catch (Exception e) {
            return Response.status(403).entity("Sent fail, maybe that is not a valid email address").build();
        }
    }

    /**
     * @apiGroup Login and Register
     * @api {post}   /verify_code/(email_employee/email_customer)  send verify code for reset password
     * @apiParam {String} address 邮箱地址
     * @apiSuccess (200) {String} Response
     */
    @POST
    @Path("/email_employee")
    public Response email_employee(@FormParam("address") String address) throws SQLException {
        Connection conn;
        if (address == null) {
            return Response.status(403).entity("you have to set email_address parameter").build();
        }
        try {
            conn = new sqlpool().getSingletons().getConnection();
        } catch (Exception e) {
            MyMessage m = new MyMessage("sql fail");
            return Response.status(403).entity(m).build();
        }
        PreparedStatement checkps = conn.prepareStatement("select * from Employee where email=?");
        checkps.setString(1, address);
        ResultSet resultSet = checkps.executeQuery();
        if (!resultSet.next()) {
            return Response.status(403).entity("this address doesn't exist").build();
        }
        try {
            int flag = send_email(address);
            PreparedStatement ps = conn.prepareStatement(
                    "REPLACE INTO Email_verification(email_address,create_time,verify_code) VALUES (?,?,?)");
            ps.setString(1, address);
            ps.setTimestamp(2, getCurrentTimeStamp());
            ps.setString(3, String.valueOf(flag));
            ps.execute();
            return Response.status(200).entity("Sent successfully").build();
        } catch (Exception e) {
            return Response.status(403).entity("Sent fail, maybe that is not a valid email address").build();
        }
    }

    /**
     * @apiGroup Login and Register
     * @api {post}   /verify_code/(reset_employee/reset_customer)  reset password
     * @apiParam {String} username
     * @apiParam {String} password
     * @apiParam {String} email
     * @apiParam {String} verified_code
     * @apiSuccess (200) {String} Response
     */
    @POST
    @Path("/reset_employee")
    public Response reset_employee(@FormParam("username") String username, @FormParam("password") String password,
                                   @FormParam("email") String email, @FormParam("verified_code") String verified_code)
            throws SQLException {
        Connection conn = new sqlpool().getSingletons().getConnection();
        PreparedStatement codeps = conn.prepareStatement("select verify_code from Email_verification where email_address=?");
        codeps.setString(1, email);
        ResultSet coders = codeps.executeQuery();
        if (!coders.next() || !coders.getString("verify_code").equals(verified_code)) {
            return Response.status(403).entity("Wrong verification code").build();
        }
        PreparedStatement checkps = conn.prepareStatement("select * from Employee where name=?");
        checkps.setString(1, username);
        ResultSet resultSet = checkps.executeQuery();
        if (!resultSet.next() || !resultSet.getString("email").equals(email)) {
            return Response.status(403).entity("Email dismatch").build();
        }
        PreparedStatement ps = conn.prepareStatement("update Employee set pass=? where name=?");
        ps.setString(1, password);
        ps.setString(2, username);
        int execute = ps.executeUpdate();
        if (execute != 0) {
            return Response.status(200).entity("Reset success, your username is:" + username).build();
        } else {
            return Response.status(403).entity("Reset failed, unknown reason").build();
        }
    }

    @POST
    @Path("/email_customer")
    public Response email_customer(@FormParam("address") String address) throws SQLException {
        Connection conn;
        if (address == null) {
            return Response.status(403).entity("you have to set email_address parameter").build();
        }
        try {
            conn = new sqlpool().getSingletons().getConnection();
        } catch (Exception e) {
            MyMessage m = new MyMessage("sql fail");
            return Response.status(403).entity(m).build();
        }
        PreparedStatement checkps = conn.prepareStatement("select * from User where email=?");
        checkps.setString(1, address);
        ResultSet resultSet = checkps.executeQuery();
        if (!resultSet.next()) {
            return Response.status(403).entity("this address doesn't exist").build();
        }
        try {
            int flag = send_email(address);
            PreparedStatement ps = conn.prepareStatement(
                    "REPLACE INTO Email_verification(email_address,create_time,verify_code) VALUES (?,?,?)");
            ps.setString(1, address);
            ps.setTimestamp(2, getCurrentTimeStamp());
            ps.setString(3, String.valueOf(flag));
            ps.execute();
            return Response.status(200).entity("Sent successfully").build();
        } catch (Exception e) {
            return Response.status(403).entity("Sent fail, maybe that is not a valid email address").build();
        }
    }

    @POST
    @Path("/reset_customer")
    public Response reset_customer(@FormParam("username") String username, @FormParam("password") String password,
                                   @FormParam("email") String email, @FormParam("verified_code") String verified_code)
            throws SQLException {
        Connection conn = new sqlpool().getSingletons().getConnection();
        PreparedStatement codeps = conn.prepareStatement("select verify_code from Email_verification where email_address=?");
        codeps.setString(1, email);
        ResultSet coders = codeps.executeQuery();
        if (!coders.next() || !coders.getString("verify_code").equals(verified_code)) {
            return Response.status(403).entity("Wrong verification code").build();
        }
        PreparedStatement checkps = conn.prepareStatement("select * from User where name=?");
        checkps.setString(1, username);
        ResultSet resultSet = checkps.executeQuery();
        if (!resultSet.next() || !resultSet.getString("email").equals(email)) {
            return Response.status(403).entity("Email dismatch").build();
        }
        PreparedStatement ps = conn.prepareStatement("update User set pass=? where name=?");
        ps.setString(1, password);
        ps.setString(2, username);
        int execute = ps.executeUpdate();
        if (execute != 0) {
            return Response.status(200).entity("Reset success, your username is:" + username).build();
        } else {
            return Response.status(403).entity("Reset failed, unknown reason").build();
        }
    }

    private int send_email(String address) throws IOException {
        String path = System.getProperty("user.dir") + "/../webapps/RESTHello-1.0-SNAPSHOT/example/B.html";
        String content2 = readToString(path);
        Email from = new Email("admin@cccbd.top", "Hibernia-Sino");
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
//            System.out.println(response.getStatusCode());
//            System.out.println(response.getBody());
//            System.out.println(response.getHeaders());
        return flag;
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

    private static java.sql.Timestamp getCurrentTimeStamp() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());
    }
}