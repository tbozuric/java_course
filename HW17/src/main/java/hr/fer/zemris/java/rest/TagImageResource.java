package hr.fer.zemris.java.rest;

import com.google.gson.Gson;
import hr.fer.zemris.java.util.Gallery;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Represents RESTFul web service for retrieving image names associated with a given tag.
 */
@Path("/images")
public class TagImageResource {

    /**
     * The servlet context.
     */
    @Context
    private ServletContext context;

    /**
     * Returns names of thumbnails in JSON format associated with the given tag.
     *
     * @param imageTag the image tag.
     * @return names of thumbnails in JSON format associated with the given tag.
     */
    @Path("/{tag}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNameOfThumbnails(@PathParam("tag") String imageTag) {
        if (imageTag == null || imageTag.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (!Gallery.containsTag(imageTag)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Gson gson = new Gson();
        String json = gson.toJson(Gallery.getImagesByTag(imageTag));
        return Response.ok(json).build();
    }
}
