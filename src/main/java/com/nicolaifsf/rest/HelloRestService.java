package com.nicolaifsf.rest; // Note your package will be {{ groupId }}.rest

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/hello/{id}")
public class HelloRestService {
    @GET // This annotation indicates GET request

    public Response hello(@PathParam("id") String id) {

        return Response.status(200).entity("hello "+id ).build();
    }
}