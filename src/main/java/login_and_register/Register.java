package login_and_register;

import sql.sqldata;

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

    @GET
    @Path("/send")
    public Response register(@QueryParam("username") String username,
                             @QueryParam("password") String password, @QueryParam("gender") String gender)
            throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        //Connection conn = DriverManager.getConnection(url, sqldata.username, sqldata.password);
        Connection conn;
        try {
            conn = new sqldata().getSingletons().getConnection();
        } catch (Exception e) {
            return Response.status(403).entity("sql pool fail!!").build();
        }
        PreparedStatement checkps = conn.prepareStatement("select * from mytest where name=?");
        checkps.setString(1, username);
        ResultSet resultSet = checkps.executeQuery();
        if (!resultSet.next()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO `cccbd`.`mytest` (`name`, `pass`,`gender`) VALUES (?, ?,?,?)");
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, gender);
            int execute = ps.executeUpdate();
            if (execute != 0) {
                return Response.status(200).entity("Register success, your username is:" + username).build();
            } else {
                return Response.status(403).entity("Register file,something went wrong, your username is:" + username).build();
            }
        }
        return Response.status(403).entity("This account have been registed").build();
    }

    @POST
    @Path("/send")
    public Response register2(@FormParam("username") String username,
                              @FormParam("password") String password, @FormParam("gender") String gender)
            throws SQLException, ClassNotFoundException {
        return register(username, password, gender);
    }
}

