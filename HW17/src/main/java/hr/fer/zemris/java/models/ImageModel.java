package hr.fer.zemris.java.models;

import java.util.Set;

/**
 * This class represents an image model. Each image consists of descriptions, tags, and image names.
 */
public class ImageModel {

    /**
     * The image name.
     */
    private String name;

    /**
     * The description of the picture.
     */
    private String description;

    /**
     * The tags related to the image.
     */
    private Set<String> imageTags;

    /**
     * Creates an instance of {@link ImageModel}.
     *
     * @param name        the image name.
     * @param description the description of the picture.
     * @param imageTags   the tags related to the image.
     */
    public ImageModel(String name, String description, Set<String> imageTags) {
        this.name = name;
        this.imageTags = imageTags;
        this.description = description;
    }

    /**
     * Returns the image name.
     *
     * @return the image name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the tags related to the image.
     *
     * @return the tags related to the image.
     */
    public Set<String> getImageTags() {
        return imageTags;
    }

    /**
     * Returns the description of the image.
     *
     * @return the description of the image.
     */
    public String getDescription() {
        return description;
    }
}
