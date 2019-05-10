package login_and_register;

import data.MyMessage;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.annotations.jaxrs.CookieParam;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import sql.sqlpool;

import javax.ws.rs.*;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Path("/profile")
public class Profile {
    private final String UPLOADED_FILE_PATH = "/usr/local/apache-tomcat-8.5.38/webapps/img/";
    @POST
    @Path("/update_profile")
    @Consumes("multipart/form-data")
    public Response update_profile(MultipartFormDataInput input, @CookieParam("token") String token) throws SQLException, IOException {
        Connection conn;
        try {
            conn = new sqlpool().getSingletons().getConnection();
        } catch (Exception e) {
            MyMessage m = new MyMessage("sql fail");
            return Response.status(403).entity(m).build();
        }
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        PreparedStatement ps = conn.prepareStatement("update User set gender=?,email=?,passport_no=?,phone=? where token=?");
        ps.setString(1, uploadForm.get("gender").get(0).getBodyAsString());
        ps.setString(2, uploadForm.get("email").get(0).getBodyAsString());
        ps.setString(3, uploadForm.get("passport_no").get(0).getBodyAsString());
        ps.setString(4, uploadForm.get("phone").get(0).getBodyAsString());
        ps.setString(5, token);
        ps.execute();
        PreparedStatement ps2 = conn.prepareStatement("select uid from User where token=?");
        ps2.setString(1, token);
        ResultSet resultSet = ps2.executeQuery();
        int uid=-1;
        if (resultSet.next()) {
            uid = resultSet.getInt("uid");
        }
        String fileName = uid +".jpg";
        List<InputPart> inputParts = uploadForm.get("uploadedFile");
        if (inputParts != null) {
            for (InputPart inputPart : inputParts) {
                try {
                    //convert the uploaded file to inputstream
                    InputStream inputStream = inputPart.getBody(InputStream.class, null);
                    byte[] bytes = IOUtils.toByteArray(inputStream);
                    //constructs upload file path
                    String absoluteName = UPLOADED_FILE_PATH + fileName;
                    writeFile(bytes, absoluteName);
                } catch (IOException e) {
                    return Response.status(500).entity(e).build();
                }
            }
            PreparedStatement ps3 = conn.prepareStatement("update User set portrait=? where token=?");
            ps3.setString(1,fileName);
            ps3.setString(2,token);
            ps3.execute();
        }
        return Response.status(200)
                .entity("Profile save successful").build();
    }

    @GET
    @Path("/my_profile")
    @Produces("application/json; charset=utf-8")
    public Response my_profile(@CookieParam("token") String token) throws SQLException {
        Connection conn;
        try {
            conn = new sqlpool().getSingletons().getConnection();
        } catch (Exception e) {
            MyMessage m = new MyMessage("sql fail");
            return Response.status(403).entity(m).build();
        }
        if (token != null) {
            PreparedStatement ps = conn.prepareStatement("select * from User where token=?");
            ps.setString(1, token);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                myProfile myProfile = new myProfile();
                myProfile.name = res.getString("name");
                myProfile.gender = res.getString("gender");
                myProfile.email = res.getString("email");
                myProfile.passport_no = res.getString("passport_no");
                myProfile.phone = res.getString("phone");
                myProfile.portrait = res.getString("portrait");
                return Response.status(200).entity(myProfile).build();
            }
        }
        MyMessage m = new MyMessage();
        m.setMessage("Please first login in");
        m.setStatus(403);
        m.setType("fail");
        return Response.status(403).entity(m).build();
    }

    public class myProfile implements Serializable {
        public String name;
        public String gender;
        public String email;
        public String passport_no;
        public String phone;
        public String portrait;
    }

    //save to somewhere
    private void writeFile(byte[] content, String filename) throws IOException {

        File file = new File(filename);

        if (!file.exists()) {
            file.createNewFile();
        }

        FileOutputStream fop = new FileOutputStream(file);

        fop.write(content);
        fop.flush();
        fop.close();

    }
}
