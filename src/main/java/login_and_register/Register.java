package login_and_register;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.*;

import static sql.sqldata.*;

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
    public Response register(@QueryParam("username") String name, @QueryParam("password") String pass) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url, username, password);
        PreparedStatement checkps = conn.prepareStatement("select * from mytest where name=?");
        checkps.setString(1,name);
        ResultSet resultSet = checkps.executeQuery();
        if(resultSet.next()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO `cccbd`.`mytest` (`name`, `pass`) VALUES (?, ?)");
            ps.setString(1, name);
            ps.setString(2, pass);
            int execute = ps.executeUpdate();
            if (execute != 0) {
                return Response.status(200).entity("register success?").build();
            } else return Response.status(403).entity("register file?").build();
        }
        return Response.status(403).entity("该账号已被注册").build();
    }

    @POST
    @Path("/send")
    public Response register2(@FormParam("username") String name, @FormParam("password") String pass) throws SQLException, ClassNotFoundException {
        return register(name, pass);
    }
}

