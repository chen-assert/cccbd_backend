package transaction;

import data.MyMessage;
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

@Path("/transaction")
@Produces("application/json; charset=utf-8")
public class Transaction {
    class Product implements Serializable {
        int productNo;
        String productName;
        String content;
        double price;

        public Product(int productNo, String productName, String content, double price) {
            this.productNo = productNo;
            this.productName = productName;
            this.content = content;
            this.price = price;
        }

        public int getProductNo() {
            return productNo;
        }

        public void setProductNo(int productNo) {
            this.productNo = productNo;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }

    @GET
    @Path("/products_list")
    public Response products_list() throws SQLException, ClassNotFoundException {
        Connection conn;
        try {
            conn = new sqlpool().getSingletons().getConnection();
        } catch (Exception e) {
            MyMessage m = new MyMessage("sql fail");
            return Response.status(403).entity(m).build();
        }
        PreparedStatement ps = conn.prepareStatement("select * from Insurance_product");
        ResultSet res = ps.executeQuery();
        LinkedList<Product> products = new LinkedList<Product>();
        while (res.next()) {
            products.addLast(new Product(res.getInt("productNo"), res.getString("productName"),
                    res.getString("content"), res.getDouble("price")));
        }
        return Response.status(200).entity(products).build();
    }

    @GET
    @Path("/product_detail")
    public Response product_detail(@QueryParam("productNo") int productNo) throws SQLException {
        Connection conn;
        try {
            conn = new sqlpool().getSingletons().getConnection();
        } catch (Exception e) {
            MyMessage m = new MyMessage("sql fail");
            return Response.status(403).entity(m).build();
        }
        PreparedStatement ps = conn.prepareStatement("select * from Insurance_product where ProductNo=?");
        ps.setInt(1, productNo);
        ResultSet res = ps.executeQuery();
        if (res.next()) {
            Product product = new Product(res.getInt("productNo"), res.getString("productName"),
                    res.getString("content"), res.getDouble("price"));
            return Response.status(200).entity(product).build();
        } else return Response.status(403).entity("this productNo don't exist").build();

    }

    @POST
    @Path("/buy_product")
    @Consumes("application/x-www-form-urlencoded; charset=UTF-8")
    @Produces("text/plain; charset=utf-8")
    public Response modify(@FormParam("productNo") int productNo,
                           @org.jboss.resteasy.annotations.jaxrs.CookieParam("token") String token) throws SQLException, UnsupportedEncodingException {
        Connection conn;
        try {
            conn = new sqlpool().getSingletons().getConnection();
        } catch (Exception e) {
            MyMessage m = new MyMessage("sql fail");
            return Response.status(403).entity(m).build();
        }
        PreparedStatement ps1 = conn.prepareStatement("SELECT * from Insurance_product where ProductNo=?");
        ps1.setInt(1, productNo);
        ResultSet resultSet = ps1.executeQuery();
        if (resultSet.next()) {
            PreparedStatement ps2 = conn.prepareStatement("insert into Policy(uid, policyName, content)values ((select uid from User where token=?),?,?)");
            ps2.setString(1, token);
            ps2.setString(2, resultSet.getString("ProductName"));
            ps2.setString(3, resultSet.getString("content"));
            boolean execute = ps2.execute();
            return Response.status(200).entity("success").build();
        } else {
            return Response.status(403).entity("this productNo don't exist").build();
        }
    }
}
