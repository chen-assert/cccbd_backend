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
    public Response waiting_claim(@CookieParam("token_employee") String token_employee) throws SQLException, ClassNotFoundException {
        Connection conn;
        try {
            conn = new sqlpool().getSingletons().getConnection();
        } catch (Exception e) {
            MyMessage m = new MyMessage("sql fail");
            return Response.status(403).entity(m).build();
        }
        //if(token_employee== null) return new MyMessage("need login").anogetResponse();
        PreparedStatement ps = conn.prepareStatement("select * from Claim where state='waiting'");
        //ps.setString(1, token_employee);
        ResultSet res = ps.executeQuery();
        LinkedList<Claim_user.Cl> pos = new LinkedList<Claim_user.Cl>();
        while (res.next()) {
            String detail_temp=res.getString("detail");
            if(detail_temp.length()>20)detail_temp=detail_temp.substring(0,20)+"...";
            Claim_user.Cl cl = new Claim_user.Cl(res.getInt("claimNo"), res.getInt("policyNo"),
                    detail_temp, res.getString("state"),
                    res.getString("loss_date"), res.getString("claim_date"));
            pos.addLast(cl);
        }
        return Response.status(200).entity(pos).build();
    }

    @GET
    @Path("/processed_claims")
    public Response processed_claims(@CookieParam("token_employee") String token_employee) throws SQLException, ClassNotFoundException {
        Connection conn;
        try {
            conn = new sqlpool().getSingletons().getConnection();
        } catch (Exception e) {
            MyMessage m = new MyMessage("sql fail");
            return Response.status(403).entity(m).build();
        }
        //if(token_employee== null) return new MyMessage("need login").anogetResponse();
        PreparedStatement ps = conn.prepareStatement("select * from Claim where state <> 'waiting'");
        //ps.setString(1, token_employee);
        ResultSet res = ps.executeQuery();
        LinkedList<Claim_user.Cl> pos = new LinkedList<Claim_user.Cl>();
        while (res.next()) {
            String detail_temp=res.getString("detail");
            if(detail_temp.length()>20)detail_temp=detail_temp.substring(0,20)+"...";
            Claim_user.Cl cl = new Claim_user.Cl(res.getInt("claimNo"), res.getInt("policyNo"),
                    detail_temp, res.getString("state"),
                    res.getString("loss_date"), res.getString("claim_date"));
            pos.addLast(cl);
        }
        return Response.status(200).entity(pos).build();
    }

    @GET
    @Path("/waiting_claims_user")
    public Response waiting_claims_user(@CookieParam("token") String token) throws SQLException, ClassNotFoundException {
        Connection conn;
        try {
            conn = new sqlpool().getSingletons().getConnection();
        } catch (Exception e) {
            MyMessage m = new MyMessage("sql fail");
            return Response.status(403).entity(m).build();
        }
        if (token == null) return new MyMessage("need login").anogetResponse();
        PreparedStatement ps = conn.prepareStatement("select * from Claim where state='waiting' and uid=(select uid from User where token=?)");
        ps.setString(1, token);
        ResultSet res = ps.executeQuery();
        LinkedList<Claim_user.Cl> pos = new LinkedList<Claim_user.Cl>();
        while (res.next()) {
            String detail_temp=res.getString("detail");
            if(detail_temp.length()>20)detail_temp=detail_temp.substring(0,20)+"...";
            Claim_user.Cl cl = new Claim_user.Cl(res.getInt("claimNo"), res.getInt("policyNo"),
                    detail_temp, res.getString("state"),
                    res.getString("loss_date"), res.getString("claim_date"));
            pos.addLast(cl);
        }
        return Response.status(200).entity(pos).build();
    }

    @GET
    @Path("/processed_claims_user")
    public Response processed_claims_user(@CookieParam("token") String token) throws SQLException, ClassNotFoundException {
        Connection conn;
        try {
            conn = new sqlpool().getSingletons().getConnection();
        } catch (Exception e) {
            MyMessage m = new MyMessage("sql fail");
            return Response.status(403).entity(m).build();
        }
        if (token == null) return new MyMessage("need login").anogetResponse();
        PreparedStatement ps = conn.prepareStatement("select * from Claim where state <> 'waiting' and uid=(select uid from User where token=?)");
        ps.setString(1, token);
        ResultSet res = ps.executeQuery();
        LinkedList<Claim_user.Cl> pos = new LinkedList<Claim_user.Cl>();
        while (res.next()) {
            String detail_temp=res.getString("detail");
            if(detail_temp.length()>20)detail_temp=detail_temp.substring(0,20)+"...";
            Claim_user.Cl cl = new Claim_user.Cl(res.getInt("claimNo"), res.getInt("policyNo"),
                    detail_temp, res.getString("state"),
                    res.getString("loss_date"), res.getString("claim_date"));
            pos.addLast(cl);
        }
        return Response.status(200).entity(pos).build();
    }
}
