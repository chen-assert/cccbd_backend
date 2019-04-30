package login_and_register;

import sql.sqlpool;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Path("/register")
@Produces("text/plain; charset=utf-8")
public class Register {
    @GET
    @Path("/")
    public Response pleaseLogin() {
        return Response.status(200).entity("This is register page").build();
    }

    /**
     * @apiGroup Login&Register
     * @api {post}   /login/send/  customer register
     * @apiParam {String} username
     * @apiParam {String} password
     * @apiParam {String} gender
     * @apiParam {String} email
     * @apiParam {String} verified_code
     * @apiSuccess (200) {String} Response
     */
    @POST
    @Path("/send")
    public Response register2(@FormParam("username") String username, @FormParam("password") String password,
                              @FormParam("gender") String gender, @FormParam("email") String email, @FormParam("verified_code") String verified_code)
            throws SQLException {
        Connection conn;
        try {
            conn = new sqlpool().getSingletons().getConnection();
        } catch (Exception e) {
            return Response.status(403).entity("sql pool fail!!").build();
        }
        PreparedStatement codeps = conn.prepareStatement("select verify_code from Email_verification where email_address=?");
        codeps.setString(1, email);
        ResultSet coders = codeps.executeQuery();
        if (!coders.next() || !coders.getString("verify_code").equals(verified_code)) {
            return Response.status(403).entity("Wrong verification code").build();
        }
        PreparedStatement checkps = conn.prepareStatement("select * from User where name=?");
        checkps.setString(1, username);
        ResultSet resultSet = checkps.executeQuery();
        if (!resultSet.next()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO User (`name`, `pass`, `gender`, `email`) VALUES (?, ?, ?,?)");
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, gender);
            ps.setString(4, email);
            int execute = ps.executeUpdate();
            if (execute != 0) {
                return Response.status(200).entity("Registration success, your username is:" + username).build();
            } else {
                return Response.status(403).entity("Registration failed, unknown reason").build();
            }
        }
        return Response.status(403).entity("Registration failed, this username has been used").build();
    }
}

