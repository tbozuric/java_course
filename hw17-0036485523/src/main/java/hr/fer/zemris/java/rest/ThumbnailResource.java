package hr.fer.zemris.java.rest;

import hr.fer.zemris.java.util.Gallery;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import static hr.fer.zemris.java.util.Gallery.*;
/**
 * Represents a RESTful web service that returns reduced-size version of images(thumbnails).
 *
 * @see <a href="https://en.wikipedia.org/wiki/Thumbnail">Thumbnail</a>
 */
@Path("/thumbnail")
public class ThumbnailResource {

    /**
     * The servlet context.
     */
    @Context
    private ServletContext context;

    /**
     * Returns a picture of reduced dimensions (150x150 )
     *
     * @param imageName the image name.
     * @return a picture of reduced dimension (150 x 150)
     */
    @Path("/{imageName}")
    @GET
    @Produces("image/jpg")
    public Response getImage(@PathParam("imageName") String imageName) {

        if (imageName == null || imageName.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        String fileName = context.getRealPath("/WEB-INF/thumbnails");
        java.nio.file.Path path = Paths.get(fileName);

        if (path == null || !path.toFile().exists()) {
            if (!createThumbnailsFolder(context.getRealPath("/WEB-INF"))) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        }

        String originalImage = context.getRealPath("/WEB-INF/" + imageName);
        File original = new File(originalImage);
        if (!original.exists()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        java.nio.file.Path originalImagePath = Paths.get(originalImage);

        fileName = context.getRealPath("/WEB-INF/thumbnails/" + imageName);
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                createThumbnail(originalImagePath, fileName);
                fileName = context.getRealPath("/WEB-INF/thumbnails/" + imageName);
                String finalFileName = fileName;
                return Response.ok((StreamingOutput) output ->
                        Gallery.stream(new BufferedInputStream(new FileInputStream(finalFileName)),
                                output)).build();
            } catch (IOException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        }

        String finalFileName = fileName;
        return Response.ok((StreamingOutput) output ->
                Gallery.stream(new BufferedInputStream(new FileInputStream(finalFileName)), output)).build();
    }
}
