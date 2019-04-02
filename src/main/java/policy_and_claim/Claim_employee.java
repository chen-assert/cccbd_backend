package policy_and_claim;

import data.MyMessage;
import org.jboss.resteasy.annotations.jaxrs.CookieParam;
import sql.sqlpool;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;


@Path("/claim")
@Produces("application/json; charset=utf-8")
public class Claim_employee {
    @GET
    @Path("/waiting_claims")
    public Response waiting_claim(@CookieParam("token_employee") String token) throws SQLException, ClassNotFoundException {
        Connection conn;
        try {
            conn = new sqlpool().getSingletons().getConnection();
        } catch (Exception e) {
            MyMessage m = new MyMessage("sql fail");
            return Response.status(403).entity(m).build();
        }
        PreparedStatement ps = conn.prepareStatement("select * from Claim where state is null");
        //ps.setString(1, token);
        ResultSet res = ps.executeQuery();
        LinkedList<Claim_user.Cl> pos = new LinkedList<Claim_user.Cl>();
        do {
            Claim_user.Cl cl = new Claim_user.Cl(res.getInt("claimNo"), res.getInt("policyNo"),
                    res.getString("detail"), res.getString("state"), res.getString("feedback"));
            pos.addLast(cl);
        } while (res.next());
        return Response.status(200).entity(pos).build();
    }
}
