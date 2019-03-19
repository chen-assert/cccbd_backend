package login_and_register;

import sql.sqldata;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.*;

import static sql.sqldata.url;

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
    public Response register(@QueryParam("username") String username, @QueryParam("password") String password,
                             @QueryParam("id") String id, @QueryParam("gender") String gender)
            throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url, sqldata.username, sqldata.password);
        PreparedStatement checkps = conn.prepareStatement("select * from mytest where name=?");
        checkps.setString(1, username);
        ResultSet resultSet = checkps.executeQuery();
        if (!resultSet.next()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO `cccbd`.`mytest` (`name`, `pass`,`id`,`gender`) VALUES (?, ?,?,?)");
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, id);
            ps.setString(4, gender);
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
    public Response register2(@FormParam("username") String username, @FormParam("password") String password,
                              @FormParam("id") String id, @FormParam("gender") String gender)
            throws SQLException, ClassNotFoundException {
        return register(username, password, id, gender);
    }
}

