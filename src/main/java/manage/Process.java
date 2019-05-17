package manage;

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
import java.util.Date;

@Path("/manage")

public class Process {
    /**
     * @apiGroup Manage
     * @apiName process claim
     * @api {post}     /manage/process process claim
     * @apiParam {String} state        要修改为的状态
     * @apiParam {String} feedback     反馈
     * @apiParam {String} claimNo      要修改的claimNo
     * @apiSuccess (200) {String} Response
     */
    @POST
    @Path("/process")
    @Consumes("application/x-www-form-urlencoded; charset=UTF-8")
    @Produces("text/plain; charset=utf-8")
    public Response process(@FormParam("state") String state, @FormParam("claimNo") String claimNo,
                            @FormParam("feedback") String feedback, @CookieParam("token_employee") String token_employee) throws SQLException, UnsupportedEncodingException {
        feedback = new String(feedback.getBytes("iso-8859-1"), "utf-8");
        Connection conn = new sqlpool().getSingletons().getConnection();
        PreparedStatement ps0 = conn.prepareStatement("select eid from Employee where token = ?");
        ps0.setString(1, token_employee);
        ResultSet resultSet = ps0.executeQuery();
        String eid;
        if (resultSet.next()) {
            eid = resultSet.getString("eid");
        } else {
            return Response.status(401).entity("Unauthorized").build();
        }
        PreparedStatement ps = conn.prepareStatement("update Claim set state=?,feedback=?,eid=?where claimNo=?");
        ps.setString(1, state);
        ps.setString(2, feedback);
        ps.setString(3, eid);
        ps.setString(4, claimNo);
        int execute = ps.executeUpdate();
        if (execute != 0) {
            return Response.status(200).entity("success").build();
        } else {
            return Response.status(403).entity("fail, seems you don't have the claim").build();
        }
    }

    /**
     * @apiGroup Manage
     * @api {post}     /manage/append append more information to claim
     * @apiParam {String} appendage    要附加的新信息
     * @apiParam {String} claimNo      要修改的claimNo
     * @apiSuccess (200) {String} Response
     */
    @POST
    @Path("/append")
    @Consumes("application/x-www-form-urlencoded; charset=UTF-8")
    @Produces("text/plain; charset=utf-8")
    public Response append(@FormParam("appendage") String appendage, @FormParam("claimNo") String claimNo,
                           @CookieParam("token") String token) throws SQLException, UnsupportedEncodingException {
        appendage = new String(appendage.getBytes("iso-8859-1"), "utf-8");
        Connection conn;
        try {
            conn = new sqlpool().getSingletons().getConnection();
        } catch (Exception e) {
            MyMessage m = new MyMessage("sql fail");
            return Response.status(403).entity(m).build();
        }
        PreparedStatement ps2 = conn.prepareStatement("select detail from Claim where claimNo=?");
        ps2.setString(1, claimNo);
        ResultSet rs2 = ps2.executeQuery();
        String detail;
        if (rs2.next()) {
            detail = rs2.getString("detail");
        } else {
            return Response.status(403).entity("fail, seems you don't have the claim").build();
        }
        PreparedStatement ps = conn.prepareStatement("update Claim set detail=?,feedback=? where claimNo=?");
        appendage = "<br>==" + new Date() + "==<br>" + appendage;
        ps.setString(1, detail + appendage);
        ps.setString(2, "waiting");
        ps.setString(3, claimNo);
        int execute = ps.executeUpdate();
        if (execute != 0) {
            return Response.status(200).entity("success").build();
        } else {
            return Response.status(403).entity("fail, seems you don't have the claim").build();
        }
    }

    /**
     * @apiGroup Manage
     * @api {post}     /manage/modify modify claim information
     * @apiParam {String} modification     要修改为的新信息
     * @apiParam {String} claimNo          要修改的claimNo
     * @apiSuccess (200) {String} Response
     */
    @POST
    @Path("/modify")
    @Consumes("application/x-www-form-urlencoded; charset=UTF-8")
    @Produces("text/plain; charset=utf-8")
    public Response modify(@FormParam("modification") String modification, @FormParam("claimNo") String claimNo,
                           @CookieParam("token") String token) throws SQLException, UnsupportedEncodingException {
        modification = new String(modification.getBytes("iso-8859-1"), "utf-8");
        Connection conn;
        try {
            conn = new sqlpool().getSingletons().getConnection();
        } catch (Exception e) {
            MyMessage m = new MyMessage("sql fail");
            return Response.status(403).entity(m).build();
        }
        PreparedStatement ps = conn.prepareStatement("update Claim set detail=?,feedback=? where claimNo=? ");
        ps.setString(1, modification);
        ps.setString(2, "waiting");
        ps.setString(3, claimNo);
        int execute = ps.executeUpdate();
        if (execute != 0) {
            return Response.status(200).entity("success").build();
        } else {
            return Response.status(403).entity("fail, seems you don't have the claim").build();
        }
    }

    /**
     * @apiGroup manage
     * @apiName get claim number
     * @api {get}  /manage/(number/my_number)      get claim number(user/employee)
     * @apiSuccessExample (200) {json} Success-Response:
     * {
     * "processed": 8,
     * "unprocessed": 2
     * }
     */
    @GET
    @Path("/number")
    @Produces("application/json; charset=utf-8")
    public Response get_number(@CookieParam("token_employee") String token_employee) throws SQLException {
        class number implements Serializable {
            public int processed;
            public int unprocessed;

            public number(int processed, int unprocessed) {
                this.processed = processed;
                this.unprocessed = unprocessed;
            }
        }
        Connection conn;
        try {
            conn = new sqlpool().getSingletons().getConnection();
        } catch (Exception e) {
            MyMessage m = new MyMessage("sql fail");
            return Response.status(403).entity(m).build();
        }
        PreparedStatement ps = conn.prepareStatement("select count(*) from Claim where (state ='waiting' or state='request')");
        ResultSet rs = ps.executeQuery();
        rs.next();
        int a = rs.getInt(1);
        PreparedStatement ps2 = conn.prepareStatement("select count(*) from Claim where (state <>'waiting' and state<>'request')");
        ResultSet rs2 = ps2.executeQuery();
        rs2.next();
        int b = rs2.getInt(1);
        return Response.status(200).entity(new number(b, a)).build();
    }

    @GET
    @Path("/my_number")
    @Produces("application/json; charset=utf-8")
    public Response get_my_number(@CookieParam("token") String token) throws SQLException {
        class number implements Serializable {
            public int processed;
            public int unprocessed;

            public number(int processed, int unprocessed) {
                this.processed = processed;
                this.unprocessed = unprocessed;
            }
        }
        Connection conn = new sqlpool().getSingletons().getConnection();
        PreparedStatement ps = conn.prepareStatement("select count(claimNo) from Claim where (state ='waiting' OR state='request') and uid=(select uid from User where token=?)");
        ps.setString(1, token);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int a = rs.getInt(1);
        PreparedStatement ps2 = conn.prepareStatement("select count(claimNo) from Claim where (state <>'waiting' and state <>'request') and uid=(select uid from User where token=?)");
        ps2.setString(1, token);
        ResultSet rs2 = ps2.executeQuery();
        rs2.next();
        int b = rs2.getInt(1);
        return Response.status(200).entity(new number(b, a)).build();
    }
}
