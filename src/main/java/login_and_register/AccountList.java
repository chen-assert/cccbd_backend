package login_and_register;

import sql.sqldata;

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
        String name;
        String pass;
        String id;
        String gender;

        public user(String name, String pass, String id, String gender) {
            this.name = name;
            this.pass = pass;
            this.id = id;
            this.gender = gender;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }

    @GET
    @Path("/")
    @Produces("application/json")
    public Response ReList(@QueryParam("limit") int limit) throws ClassNotFoundException, SQLException {
        Connection conn;
        try {
            conn = new sqldata().getSingletons().getConnection();
        } catch (Exception e) {
            return Response.status(403).entity("sql pool fail!!").build();
        }
        PreparedStatement ps = conn.prepareStatement("select * from mytest limit ?");
        if(limit==0)limit=10;
        ps.setInt(1, limit);
        ResultSet rs = ps.executeQuery();
        LinkedList<user> users = new LinkedList<user>();
        while (rs.next()) {
            users.add(new user(rs.getString("name"), rs.getString("pass"),
                    rs.getString("uid"), rs.getString("gender")));
        }
        return Response.status(200).entity(users).build();

    }
}

