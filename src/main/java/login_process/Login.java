package login_process;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/login")
@Produces("text/plain; charset=utf-8")
public class Login {
    @GET
    @Path("/")
    public Response pleaseLogin() {
        Response response = Response.status(200).entity("Please login in").build();
        return response;
    }
    @GET
    @Path("/try")
    public Response login(@QueryParam("username") String name, @QueryParam("password") String pass) {
        if (name.equals("n1") && pass.equals("n2")) {
            return Response.status(200).entity("login success").build();
        }
        else return Response.status(403).entity("login fail, try again?").build();
    }
}

