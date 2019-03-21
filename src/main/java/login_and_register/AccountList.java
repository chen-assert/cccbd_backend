package login_and_register;

import sql.sqldata;

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

    class test2 implements Serializable {
        private static final long serialVersionUID = 1L;
        int a;
        String b;
        double c;
        LinkedList<Integer> e;

        public test2() {
            a = 10;
            b = "20";
            c = 30.5;
            e = new LinkedList<Integer>();
            e.addLast(10);
            e.addLast(20);
        }

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

        public double getC() {
            return c;
        }

        public void setC(double c) {
            this.c = c;
        }

        public LinkedList<Integer> getE() {
            return e;
        }

        public void setE(LinkedList<Integer> e) {
            this.e = e;
        }
    }

    class test implements Serializable {
        private static final long serialVersionUID = 1L;
        int a;
        String b;
        double c;
        test2 d;

        public test() {
            a = 10;
            b = "20";
            c = 30.5;
            d = new test2();
        }

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

        public double getC() {
            return c;
        }

        public void setC(double c) {
            this.c = c;
        }

        public test2 getD() {
            return d;
        }

        public void setD(test2 d) {
            this.d = d;
        }
    }

    @GET
    @Path("/")
    @Produces("application/json")
    public Response AccountList() throws ClassNotFoundException, SQLException {
        Connection conn;
        try {
            conn = new sqldata().getSingletons().getConnection();
        } catch (Exception e) {
            return Response.status(403).entity("sql pool fail!!").build();
        }
        PreparedStatement ps = conn.prepareStatement("select * from mytest");
        ResultSet rs = ps.executeQuery();
        LinkedList<user> users = new LinkedList<user>();
        while (rs.next()) {
            users.add(new user(rs.getString("name"), rs.getString("pass"),
                    rs.getString("id"), rs.getString("gender")));
        }
        return Response.status(200).entity(users).build();

    }
}

