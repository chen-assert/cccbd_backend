package login_and_register;

import sql.sqldata;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.*;

import static sql.sqldata.*;

@Path("/login")
@Produces("text/plain; charset=utf-8")
public class Login {
    @GET
    @Path("/")
    public Response pleaseLogin() {
        Response response = Response.status(200).entity("This is login page").build();
        return response;
    }

    @GET
    @Path("/send")
    public Response login(@QueryParam("username") String username, @QueryParam("password") String password) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        //Connection conn = DriverManager.getConnection(url, sqldata.username, sqldata.password);
        Connection conn;
        try {
            conn = new sqldata().getSingletons().getConnection();
        }catch(Exception e){
            return Response.status(403).entity("sql pool fail!!").build();
        }
        PreparedStatement ps = conn.prepareStatement("select * from mytest where name=? and pass=?");
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
            return Response.status(200).entity("login success").build();
        } else return Response.status(403).entity("login fail, try again? " + username).build();
    }

    @POST
    @Path("/send")
    public Response login2(@FormParam("username") String name, @FormParam("password") String pass) throws SQLException, ClassNotFoundException {
        return login(name, pass);
    }
}

