package login_and_register;

import sql.sqlpool;

import javax.ws.rs.*;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Path("/login")
public class Login {
    class Message implements Serializable {
        int status;
        String type;
        String username;
        String message;

        Message() {

        }

        Message(int status, String type, String username, String message) {
            this.status = status;
            this.type = type;
            this.username = username;
            this.message = message;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }

    @GET
    @Path("/")
    @Produces("text/plain; charset=utf-8")
    public Response pleaseLogin() {
        Response response = Response.status(200).entity("This is login page").build();
        return response;
    }

    @GET
    @Path("/send")
    @Produces("application/json; charset=utf-8")
    public Response login(@QueryParam("username") String username, @QueryParam("password") String password) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn;
        try {
            conn = new sqlpool().getSingletons().getConnection();
        } catch (Exception e) {
            Message m = new Message();
            m.setMessage("sql pool fail!!");
            m.setStatus(403);
            m.setType("fail");
            return Response.status(403).entity(m).build();
        }
        PreparedStatement ps = conn.prepareStatement("select * from User where name=? and pass=?");
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
            NewCookie cookie = new NewCookie("name", "password");
            return Response.status(200).entity(
                    new Message(200, "success", username, "Login success")).cookie(cookie).build();
        } else return Response.status(403).entity(
                new Message(403, "fail", username, "Login fail")).build();
    }

    @POST
    @Path("/send")
    public Response login2(@FormParam("username") String name, @FormParam("password") String pass) throws SQLException, ClassNotFoundException {
        return login(name, pass);
    }
}

