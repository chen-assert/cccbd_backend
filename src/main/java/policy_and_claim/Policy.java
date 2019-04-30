package policy_and_claim;

import data.MyMessage;
import org.jboss.resteasy.annotations.jaxrs.CookieParam;
import sql.sqlpool;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

@Path("/policy")
@Produces("application/json; charset=utf-8")
public class Policy {
    class po implements Serializable {
        int policyNo;
        String policyName;
        String content;

        public po(int policyNo, String policyName, String content) {
            this.policyNo = policyNo;
            this.policyName = policyName;
            this.content = content;
        }

        public int getPolicyNo() {
            return policyNo;
        }

        public void setPolicyNo(int policyNo) {
            this.policyNo = policyNo;
        }

        public String getPolicyName() {
            return policyName;
        }

        public void setPolicyName(String policyName) {
            this.policyName = policyName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    /**
     * @apiGroup Policy
     * @apiName nameIsUseless
     * @apiPermission user
     * @api {get}  /policy/my_policies get your policy list
     * @apiSuccessExample (200) {json} Success-Response:
     * [
     * {
     * "policyNo": 1,
     * "policyName": "testpolicy",
     * "content": "insure everything!"
     * },
     * {
     * "policyNo": 2,
     * "policyName": "testpolicy2",
     * "content": "insure nothing!"
     * }
     * ]
     */
    @GET
    @Path("/my_policies")
    public Response my_policies(@CookieParam("token") String token) throws SQLException, ClassNotFoundException {
        Connection conn;
        try {
            conn = new sqlpool().getSingletons().getConnection();
        } catch (Exception e) {
            MyMessage m = new MyMessage();
            m.setMessage("sql pool fail!!");
            m.setStatus(403);
            m.setType("fail");
            return Response.status(403).entity(m).build();
        }
        if (token != null) {
            PreparedStatement ps = conn.prepareStatement("select * from Policy where uid = (select uid from User where token=?)");
            ps.setString(1, token);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                LinkedList<po> pos = new LinkedList<po>();
                do {
                    pos.addLast(new po(res.getInt("policyNo"), res.getString("policyName"), res.getString("content")));
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
