package policy_and_claim;

import data.MyMessage;
import org.jboss.resteasy.annotations.jaxrs.CookieParam;
import sql.sqlpool;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

@Path("/claim")
@Produces("application/json; charset=utf-8")
public class Claim_user {
    static class Cl implements Serializable {
        int claimNo;
        int policyNo;
        String detail;
        String state;
        String feedback;

        public Cl(int claimNo, int policyNo, String detail, String state, String feedback) {
            this.claimNo = claimNo;
            this.policyNo = policyNo;
            this.detail = detail;
            this.state = state;
            this.feedback = feedback;
        }


        public int getClaimNo() {
            return claimNo;
        }

        public void setClaimNo(int claimNo) {
            this.claimNo = claimNo;
        }

        public int getPolicyNo() {
            return policyNo;
        }

        public void setPolicyNo(int policyNo) {
            this.policyNo = policyNo;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getFeedback() {
            return feedback;
        }

        public void setFeedback(String feedback) {
            this.feedback = feedback;
        }
    }

    @GET
    @Path("/new_claim")
    public Response new_claim(@CookieParam("token") String token, @QueryParam("policyNo") String policyNo,
                              @QueryParam("detail") String detail) throws SQLException, ClassNotFoundException, UnsupportedEncodingException {
        detail = new String(detail.getBytes("iso-8859-1"), "utf-8");
        Connection conn;
        try {
            conn = new sqlpool().getSingletons().getConnection();
        } catch (Exception e) {
            MyMessage m = new MyMessage("sql fail");
            return Response.status(403).entity(m).build();
        }
        while (true) {
            if (token == null) break;
            PreparedStatement ps = conn.prepareStatement("select * from Policy where uid = (select uid from User where token=?) and PolicyNo=?");
            ps.setString(1, token);
            ps.setString(2, policyNo);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) break;
            int uid = rs.getInt("uid");
            PreparedStatement ps2 = conn.prepareStatement("insert into Claim (uid,policyNo,detail,state) values (?,?,?,?)");
            ps2.setInt(1, uid);
            ps2.setString(2, policyNo);
            ps2.setString(3, detail);
            ps2.setString(4,"waiting");
            int update = ps2.executeUpdate();
            MyMessage m = new MyMessage();
            m.setMessage("add claim success");
            m.setStatus(200);
            m.setType("success");
            return Response.status(200).entity(m).build();
        }
        MyMessage m = new MyMessage();
        m.setMessage("policy number dismatch");
        m.setStatus(403);
        m.setType("fail");
        return Response.status(403).entity(m).build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded; charset=UTF-8")
    @Path("/new_claim")
    public Response new_claim2(@CookieParam("token") String token, @FormParam("policyNo") String policyNo,
                               @FormParam("detail") String detail) throws SQLException, ClassNotFoundException, UnsupportedEncodingException {
        return new_claim(token, policyNo, detail);
    }


    @GET
    @Path("/my_claims")
    public Response my_policies(@CookieParam("token") String token) throws SQLException, ClassNotFoundException {
        Connection conn;
        try {
            conn = new sqlpool().getSingletons().getConnection();
        } catch (Exception e) {
            MyMessage m = new MyMessage("sql fail");
            return Response.status(403).entity(m).build();
        }
        if (token != null) {
            PreparedStatement ps = conn.prepareStatement("select * from Claim where uid = (select uid from User where token=?)");
            ps.setString(1, token);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                LinkedList<Cl> pos = new LinkedList<Cl>();
                do {
                    Cl cl = new Cl(res.getInt("claimNo"), res.getInt("policyNo"),
                            res.getString("detail"), res.getString("state"),
                            res.getString("feedback"));
                    pos.addLast(cl);
                } while (res.next());
                return Response.status(200).entity(pos).build();
            }
        }
        MyMessage m = new MyMessage();
        m.setMessage("Please first login in");
        m.setStatus(403);
        m.setType("fail");
        return Response.status(403).entity(m).build();
    }

}
