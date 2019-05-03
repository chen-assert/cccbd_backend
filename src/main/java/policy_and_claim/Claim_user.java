package policy_and_claim;

import data.MyMessage;
import org.jboss.resteasy.annotations.Form;
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
        String loss_date;
        String claim_date;

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

        public String getLoss_date() {
            return loss_date;
        }

        public void setLoss_date(String loss_date) {
            this.loss_date = loss_date;
        }

        public String getClaim_date() {
            return claim_date;
        }

        public void setClaim_date(String claim_date) {
            this.claim_date = claim_date;
        }

        public Cl(int claimNo, int policyNo, String detail, String state, String loss_date, String claim_date) {
            this.claimNo = claimNo;
            this.policyNo = policyNo;
            this.detail = detail;
            this.state = state;
            this.loss_date = loss_date;
            this.claim_date = claim_date;
        }
    }

    /**
     * @apiGroup Policy and Claim
     * @apiPermission user
     * @api {post}   /claim/new_claim add a new claim
     * @apiParam {Int} policyNo
     * @apiParam {String} detail
     * @apiParam {String} real_name
     * @apiParam {Date} claim_date
     * @apiParam {Date} loss_date
     * @apiSuccessExample (200) {json} Success-Response:
     * {
     * "status": 200,
     * "type": "success",
     * "message": "Add cliam successfully"
     * }
     */
    @POST
    @Consumes("application/x-www-form-urlencoded; charset=UTF-8")
    @Path("/new_claim")
    public Response new_claim(@CookieParam("token") String token, @Form Claim claim
    ) throws SQLException, UnsupportedEncodingException {
        claim.detail = new String(claim.detail.getBytes("iso-8859-1"), "utf-8");
        claim.real_name = new String(claim.real_name.getBytes("iso-8859-1"), "utf-8");
        Connection conn;
        try {
            conn = new sqlpool().getSingletons().getConnection();
        } catch (Exception e) {
            MyMessage m = new MyMessage("sql fail");
            return Response.status(403).entity(m).build();
        }
        if (claim.detail == null) {
            MyMessage m = new MyMessage();
            m.setMessage("Bad Request(please write claim detail)");
            m.setStatus(400);
            m.setType("fail");
            return Response.status(400).entity(m).build();
        }
        if (token == null) {
            MyMessage m = new MyMessage();
            m.setMessage("Unauthorized");
            m.setStatus(401);
            m.setType("fail");
            return Response.status(401).entity(m).build();
        }
        PreparedStatement ps = conn.prepareStatement("select * from Policy where uid = (select uid from User where token=?) and PolicyNo=?");
        ps.setString(1, token);
        ps.setString(2, claim.policyNo);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            MyMessage m = new MyMessage();
            m.setMessage("Policy number dismatch(you don't have this policy)");
            m.setStatus(403);
            m.setType("fail");
            return Response.status(403).entity(m).build();
        }
        int uid = rs.getInt("uid");
        PreparedStatement ps2 = conn.prepareStatement
                ("insert into Claim (uid,policyNo,detail,state,real_name,loss_date,claim_date) " +
                        "values (?,?,?,?,?,?,?)");
        ps2.setInt(1, uid);
        ps2.setString(2, claim.policyNo);
        ps2.setString(3, claim.detail);
        ps2.setString(4, "waiting");
        ps2.setString(5, claim.real_name);
        ps2.setString(6, claim.loss_date);
        ps2.setString(7, claim.claim_date);

        int update = ps2.executeUpdate();
        MyMessage m = new MyMessage();
        m.setMessage("Add claim successfully");
        m.setStatus(200);
        m.setType("success");
        return Response.status(200).entity(m).build();

    }

    /**
     * @apiGroup Policy and Claim
     * @apiPermission user
     * @api {get}   /claim/my_claims  get user's claim list
     * @apiSuccessExample (200) {json} Success-Response:
     * [
     * {
     * "claimNo": 1,
     * "policyNo": 1,
     * "detail": "I lost myself",
     * "state": "accept",
     * "feedback": null
     * },
     * {
     * "claimNo": 2,
     * "policyNo": 1,
     * "detail": "I lost! I lost!",
     * "state": "waiting",
     * "feedback": null
     * }
     * ]
     */
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
                    String detail_temp = res.getString("detail");
                    if (detail_temp != null && detail_temp.length() > 20)
                        detail_temp = detail_temp.substring(0, 20) + "...";
                    Cl cl = new Cl(res.getInt("claimNo"), res.getInt("policyNo"),
                            detail_temp, res.getString("state"),
                            res.getString("loss_date"), res.getString("claim_date"));
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

    /**
     * @apiGroup Policy and Claim
     * @apiPermission user or employee
     * @api {get}   /claim/detail  get a claim detail
     * @apiParam {Int} ClaimNo
     * @apiSuccessExample (200) {json} Success-Response:
     * {
     * "ClaimNo": "34",
     * "policyNo": "2",
     * "detail": "detail blablabla",
     * "real_name": "chen",
     * "claim_date": "2019-04-02",
     * "loss_date": "2019-04-01",
     * "feedback": null
     * }
     */
    @GET
    @Path("/detail")
    public Response claim_detail(@CookieParam("token") String token, @CookieParam("token_employee") String token_employee,
                                 @QueryParam("claimNo") String claimNo) throws SQLException, ClassNotFoundException {
        Connection conn;
        try {
            conn = new sqlpool().getSingletons().getConnection();
        } catch (Exception e) {
            MyMessage m = new MyMessage("sql fail");
            return Response.status(403).entity(m).build();
        }
        int state = 0;
        if (token == null && token_employee == null) state = -1;
        if (token != null) {
            PreparedStatement ps = conn.prepareStatement("select * from User where token=?");
            ps.setString(1, token);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) state = -1;
        }
        if (token_employee != null) {
            PreparedStatement ps = conn.prepareStatement("select * from Employee where token=?");
            ps.setString(1, token_employee);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) state = -1;
        }
        if (state == -1) {
            MyMessage m = new MyMessage();
            m.setMessage("Unauthorized");
            m.setStatus(401);
            m.setType("fail");
            return Response.status(401).entity(m).build();
        } else {
            PreparedStatement ps = conn.prepareStatement("select * from Claim where claimNo = ?");
            ps.setString(1, claimNo);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                Claim claim = new Claim();
                claim.detail = res.getString("detail");
                claim.policyNo = res.getString("policyNo");
                claim.real_name = res.getString("real_name");
                claim.claim_date = res.getString("claim_date");
                claim.loss_date = res.getString("loss_date");
                claim.ClaimNo = res.getString("ClaimNo");
                claim.feedback = res.getString("feedback");
                return Response.status(200).entity(claim).build();
            } else {
                MyMessage m = new MyMessage();
                m.setMessage("No such a claim");
                m.setStatus(404);
                m.setType("fail");
                return Response.status(404).entity(m).build();
            }
        }
    }
}
