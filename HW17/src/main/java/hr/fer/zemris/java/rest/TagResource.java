package hr.fer.zemris.java.rest;


import com.google.gson.Gson;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.file.Paths;
import java.util.Set;

import static hr.fer.zemris.java.util.Gallery.getImageTags;

/**
 * Represents a Restful web service that enables the retrieval of all the tags that appear in the images.
 */
@Path("/image-tags")
public class TagResource {

    /**
     * The servlet context.
     */
    @Context
    private ServletContext context;

    /**
     * Returns all the tags in JSON format that appear in the images that are on the server.
     *
     * @return all the tags in JSON format  that appear in the images that are on the server.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTags() {
        Set<String> tags;
        String fileName = context.getRealPath("/WEB-INF/opisnik.txt");
        java.nio.file.Path path = Paths.get(fileName);

        if (path == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        tags = getImageTags(path);
        if (tags == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        Gson gson = new Gson();
        String json = gson.toJson(tags);
        return Response.status(Response.Status.OK).entity(json).build();
    }
}
