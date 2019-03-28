package com.nicolaifsf.rest; // Note your package will be {{ groupId }}.rest

import org.jboss.resteasy.annotations.jaxrs.CookieParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/hello/{id}")
@Produces("text/plain; charset=utf-8")
public class HelloRestService {
    @GET // This annotation indicates GET request
    public Response hello(@PathParam("id") String id,@CookieParam("token") String token) {
        return Response.status(200).entity("hello "+id+", your token is "+token).build();
    }
}