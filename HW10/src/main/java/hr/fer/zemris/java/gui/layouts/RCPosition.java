package hr.fer.zemris.java.gui.layouts;

import java.util.Objects;

/**
 * Represents row-column position in our custom layout manager.
 */
public class RCPosition {

    /**
     * Row position.
     */
    private int row;

    /**
     * Column position.
     */
    private int column;

    /**
     * Creates an instance of row-column position.
     *
     * @param row    row position.
     * @param column column position.
     */
    public RCPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Returns the row position.
     *
     * @return thw row position.
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column position.
     *
     * @return the column position.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Objects are equals if they have the same values of row and columns position.
     *
     * @param o some object.
     * @return true if the objects are same.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RCPosition position = (RCPosition) o;
        return row == position.row &&
                column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    /**
     * Returns string representation of {@link RCPosition} in format <p>"row,column".</p>
     *
     * @return string representation of {@link RCPosition}.
     */
    @Override
    public String toString() {
        return row + "," + column;
    }
}
