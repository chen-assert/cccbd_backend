package login_and_register;

import sql.sqldata;
import sql.sqlpool;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

@Path("/account")
public class AccountList {
    class user implements Serializable {
        String uid;
        String name;
        String pass;
        String gender;
        String email;


        public user(String uid, String name, String pass, String gender, String email) {
            this.uid = uid;
            this.name = name;
            this.pass = pass;
            this.gender = gender;
            this.email = email;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPass() {
            return pass;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    @GET
    @Path("/")
    @Produces("application/json")
    public Response ReList(@QueryParam("limit") int limit) throws ClassNotFoundException, SQLException {
        Connection conn;
        try {
            conn = new sqlpool().getSingletons().getConnection();
        } catch (Exception e) {
            return Response.status(403).entity("sql pool fail!!").build();
        }
        PreparedStatement ps = conn.prepareStatement("select * from User limit ?");
        if (limit == 0) limit = 10;
        ps.setInt(1, limit);
        ResultSet rs = ps.executeQuery();
        LinkedList<user> users = new LinkedList<user>();
        while (rs.next()) {
            users.add(new user(rs.getString("uid"), rs.getString("name"), rs.getString("pass"),
                    rs.getString("gender"), rs.getString("email")));
        }
        return Response.status(200).entity(users).build();

    }
}

