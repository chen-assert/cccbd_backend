package manage;

import org.jboss.resteasy.annotations.jaxrs.CookieParam;
import sql.sqlpool;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Path("/manage")

public class Process {
    @POST
    @Path("/process")
    @Produces("text/plain; charset=utf-8")
    public Response process(@FormParam("state") String state, @FormParam("claimNo") String claimNo,
                            @CookieParam("token_employee") String token) throws SQLException {
        Connection conn = new sqlpool().getSingletons().getConnection();
        PreparedStatement ps = conn.prepareStatement("update Claim set state=? where claimNo=? ");
        ps.setString(1, state);
        ps.setString(2, claimNo);
        int execute = ps.executeUpdate();
        if (execute != 0) {
            return Response.status(200).entity("success").build();
        } else {
            return Response.status(403).entity("fail, seems you don't have the claim").build();
        }
    }

    @GET
    @Path("/number")
    @Produces("application/json; charset=utf-8")
    public Response get_number(@CookieParam("token_employee") String token) throws SQLException {
        class number implements Serializable {
            public int processed;
            public int unprocessed;

            public number(int processed, int unprocessed) {
                this.processed = processed;
                this.unprocessed = unprocessed;
            }
        }
        Connection conn = new sqlpool().getSingletons().getConnection();
        PreparedStatement ps = conn.prepareStatement("select count(*) from Claim where state ='waiting'");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int a = rs.getInt(1);
        PreparedStatement ps2 = conn.prepareStatement("select count(*) from Claim where state <>'waiting'");
        ResultSet rs2 = ps2.executeQuery();
        rs2.next();
        int b = rs2.getInt(1);
        return Response.status(200).entity(new number(a, b)).build();
    }
}
