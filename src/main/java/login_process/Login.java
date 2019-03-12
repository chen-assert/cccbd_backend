package login_process;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/login/{username}/{password}")
public class Login {
    @GET
    public Response login(@PathParam("username") String name, @PathParam("password") String pass) {
        if (name.equals("n1") && pass.equals("n2")) {
            return Response.status(200).entity("login success").build();
        }
        else return Response.status(403).entity("login fail, try again?").build();
    }
}

