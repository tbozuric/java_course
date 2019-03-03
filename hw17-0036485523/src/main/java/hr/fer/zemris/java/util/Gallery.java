package hr.fer.zemris.java.util;

import hr.fer.zemris.java.models.ImageModel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Represents an auxiliary class for working with images.
 * It offers methods for creating HTTP responses and retrieving image extensions.
 */
public class Gallery {

    /**
     * The image width.
     */
    private static final int WIDTH = 150;

    /**
     * The image height.
     */
    private static final int HEIGHT = 150;

    /**
     * Represents a map that contains image models associated with a particular tag.
     */
    private static Map<String, List<ImageModel>> images = new ConcurrentHashMap<>();


    /**
     * The method creates a thumbnail, an image of reduced dimensions from the original image
     *
     * @param originalImagePath the original path to image.
     * @param newImageFileName  the new path to image( path to the thumbnail).
     * @throws IOException if an error occurs while reading the image.
     */
    public static void createThumbnail(Path originalImagePath, String newImageFileName) throws IOException {
        File inputFile = new File(originalImagePath.toString());
        BufferedImage inputImage = ImageIO.read(inputFile);

        BufferedImage outputImage = new BufferedImage(WIDTH,
                HEIGHT, inputImage.getType());

        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, WIDTH, HEIGHT, null);
        g2d.dispose();

        String formatName = Gallery.getExtension(originalImagePath.toString());
        ImageIO.write(outputImage, formatName, new File(newImageFileName));
    }

    /**
     * Creates a "thumbnails" folder in WEB-INF directory..
     *
     * @return true if the folder is created.
     */
    public static boolean createThumbnailsFolder(String root) {
        return new File(root + "/thumbnails").mkdirs();
    }


    /**
     * Returns the image extension.
     *
     * @param imagePath the path to the image.
     * @return the image extension.
     */
    private static String getExtension(String imagePath) {
        return imagePath.substring(imagePath.lastIndexOf(".") + 1);
    }

    /**
     * Checks if map contains the given tag..
     *
     * @param tag the image tag.
     * @return true if map contains the given tag..
     */
    public static boolean containsTag(String tag) {
        return images.containsKey(tag);
    }

    /**
     * Returns the set of tags that appear in the images.
     *
     * @param path the path to the directory with images.
     * @return the set of tags.
     */
    public static Set<String> getImageTags(Path path) {
        try {
            populateImages(path.toString());
            return images.keySet();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * The auxiliary method that reads the image description on the server and loads the image data into the memory.
     * The image is not loaded into memory due to memory requirements.
     *
     * @throws IOException if an error occurs while reading the file on the server.
     */
    private static void populateImages(String fileName) throws IOException {
        Path path = Paths.get(fileName);

        try (BufferedReader br = Files.newBufferedReader(path)) {

            while (true) {
                String name = br.readLine();
                String description = br.readLine();
                String allTags = br.readLine();

                if (name == null || description == null || allTags == null) {
                    break;
                }

                String[] tagsAsArray = allTags.split(",");
                Set<String> tags = Arrays.stream(tagsAsArray).map(String::trim)
                        .distinct().collect(Collectors.toCollection(TreeSet::new));

                for (String tag : tags) {
                    if (images.getOrDefault(tag, null) == null) {
                        List<ImageModel> imagesList = new ArrayList<>();
                        imagesList.add(new ImageModel(name, description, tags));
                        images.put(tag, imagesList);
                        continue;
                    }
                    images.get(tag).add(new ImageModel(name, description, tags));
                }
            }
        }
    }

    /**
     * Returns a set of images which contains the given tag.
     *
     * @param imageTag the image tag.
     * @return a set of images which contains the given tag.
     */
    public static Set<String> getImagesByTag(String imageTag) {
        return images.get(imageTag).stream().map(ImageModel::getName).collect(Collectors.toSet());
    }

    /**
     * Returns {@link ImageModel} for the given image name and tag.
     *
     * @param image    the image name.
     * @param imageTag the image tag.
     * @return the image model for the given image name and tag.
     */
    public static ImageModel getImageAssociatedByTag(String image, String imageTag) {
        List<ImageModel> models = images.get(imageTag);
        Optional<ImageModel> imageModel = models.stream().filter(s -> s.getName().equals(image)).findFirst();
        return imageModel.orElse(null);
    }

    /**
     * Represents an auxiliary method for writing resource from input stream to the output stream in blocks.
     * This way we end up with a consistent memory usage.
     *
     * @param input  the input stream to an image.
     * @param output the output stream.
     * @return the total size of the written resource.
     * @throws IOException if an error occurs while writing or reading to/from output/input stream
     */
    public static long stream(InputStream input, OutputStream output) throws IOException {
        try (
                ReadableByteChannel inputChannel = Channels.newChannel(input);
                WritableByteChannel outputChannel = Channels.newChannel(output)
        ) {
            ByteBuffer buffer = ByteBuffer.allocateDirect(10240);
            long size = 0;

            while (inputChannel.read(buffer) != -1) {
                buffer.flip();
                size += outputChannel.write(buffer);
                buffer.clear();
            }

            return size;
        }
    }
}
