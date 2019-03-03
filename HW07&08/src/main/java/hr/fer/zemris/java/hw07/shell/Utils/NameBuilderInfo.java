package hr.fer.zemris.java.hw07.shell.Utils;

/**
 * An interface that provides information about the {@link NameBuilder}.
 */
public interface NameBuilderInfo {
    /**
     * Returns a string builder.
     *
     * @return a string builder.
     */
    StringBuilder getStringBuilder();

    /**
     * Returns a desired group if it exists.
     *
     * @param index group index.
     * @return a desired group.
     */
    String getGroup(int index);
}
