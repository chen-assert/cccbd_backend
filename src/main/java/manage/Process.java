package manage;

import data.MyMessage;
import org.jboss.resteasy.annotations.jaxrs.CookieParam;
import sql.sqlpool;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.SQLException;

@Path("/manage")
@Produces("application/json; charset=utf-8")
public class Process {
    @POST
    @Path("/process")
    public Response process(@CookieParam("token_employee") String token) throws SQLException {
        Connection conn;
        try {
            conn = new sqlpool().getSingletons().getConnection();
        } catch (Exception e) {
            MyMessage m = new MyMessage("sql fail");
            return Response.status(403).entity(m).build();
        }
        return null;
    }
}
