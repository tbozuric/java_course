package hr.fer.zemris.java.rest;

import hr.fer.zemris.java.models.ImageModel;
import hr.fer.zemris.java.util.Gallery;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;
import java.nio.file.Paths;

/**
 * This class represents the RESTful Web service to retrieve the image from the server of the original dimensions.
 * The web service also provides a retrieval of image descriptions and image-related tags.
 */
@Path("/image")
public class FullImageResource {

    /**
     * The servlet context.
     */
    @Context
    private ServletContext context;

    /**
     * Returns the image associated with the given param name or bad request status.
     *
     * @param imageName the image name.
     * @return the image associated with the given param name or bad request status.
     */
    @GET
    @Path("/{name}")
    @Produces("image/jpg")
    public Response getOriginalImage(@PathParam("name") String imageName) {
        if (imageName == null || imageName.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        String fileName = context.getRealPath("/WEB-INF/" + imageName);
        java.nio.file.Path path = Paths.get(fileName);
        if (path == null || !path.toFile().exists()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }


        return Response.ok((StreamingOutput) output ->
                Gallery.stream(new BufferedInputStream(new FileInputStream(fileName)), output)).build();
    }

    /**
     * Returns a description of the image and tags associated with the image in JSON format.
     *
     * @param imageTag  the image tag.
     * @param imageName the image name.
     * @return a description of the image and tags associated with the image in JSON format.
     */
    @GET
    @Path("/info/{tag}/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getImageInformation(@PathParam("tag") String imageTag, @PathParam("name") String imageName) {
        if (!Gallery.containsTag(imageTag)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        ImageModel image = Gallery.getImageAssociatedByTag(imageName, imageTag);
        if (image == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        JSONObject result = new JSONObject();
        result.put("description", image.getDescription());
        JSONArray tags = new JSONArray();
        for (String tag : image.getImageTags()) {
            tags.put(tag);
        }
        result.put("tags", tags);
        return Response.ok(result.toString()).build();
    }
}
