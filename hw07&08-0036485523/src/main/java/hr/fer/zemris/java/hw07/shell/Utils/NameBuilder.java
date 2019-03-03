package hr.fer.zemris.java.hw07.shell.Utils;

/**
 * An interface that offers a method for generating part names.
 */
public interface NameBuilder {
    /**
     * Generates part of the name by writing to the {@link StringBuilder} that gets over the
     * argument info.
     *
     * @param info a reference to {@link NameBuilderInfo}
     */
    void execute(NameBuilderInfo info);
}
