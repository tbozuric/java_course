package hr.fer.zemris.java.gui.layouts;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Represents calculator layout:
 * <p>
 * ############################# #####  #####
 * #            1,1            # #1,6#  #1,7#
 * ############################# #####  #####
 * <p>
 * ##### ##### ##### ##### ##### #####  #####
 * #2,1# #2,2# #2,3# #2,4# #2,5# #2,6#  #2,7#
 * ##### ##### ##### ##### ##### #####  #####
 * <p>
 * ##### ##### ##### ##### ##### #####  #####
 * #3,1# #3,2# #3,3# #3,4# #3,5# #3,6#  #3,7#
 * ##### ##### ##### ##### ##### #####  #####
 * <p>
 * ##### ##### ##### ##### ##### #####  #####
 * #4,1# #4,2# #4,3# #4,4# #4,5# #4,6#  #4,7#
 * ##### ##### ##### ##### ##### #####  #####
 * <p>
 * ##### ##### ##### ##### ##### #####  #####
 * #5,1# #5,2# #5,3# #5,4# #5,5# #5,6#  #5,7#
 * ##### ##### ##### ##### ##### #####  #####
 * <p>
 * The maximum number of rows is 7 , and the maximum number of columns is 5.  All elements are of the same width and height,
 * except for the element on the position (1,1). Element on the position (1,1) extends to position (1,5).
 * It is not possible to add two elements to the same position. If the above restrictions are not met,
 * {@link CalcLayoutException } is thrown.
 */
public class CalcLayout implements LayoutManager2 {

    /**
     * Maximum permissible number of columns.
     */
    private static final int MAX_COLUMNS = 7;

    /**
     * Maximum permissible number of rows.
     */
    private static final int MAX_ROWS = 5;

    /**
     * Initial position in the "mesh".
     */
    private RCPosition START = new RCPosition(1, 1);

    /**
     * Gaps between components.
     */
    private int gap;

    /**
     * The map of the components and their positions.
     */
    private Map<Component, RCPosition> components;

    /**
     * Creates an instance of {@link CalcLayout}  without gap.
     */
    public CalcLayout() {
        this(0);
    }

    /**
     * Creates an instance of {@link CalcLayout}.
     *
     * @param gap desired gap between components.
     * @throws IllegalArgumentException if the gap is less than zero.
     */
    public CalcLayout(int gap) {
        if(gap < 0){
            throw new IllegalArgumentException("Gap must be greater or equal to zero!");
        }
        this.gap = gap;
        components = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     *
     * @throws CalcLayoutException if constraints are not met.
     */
    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        if (constraints instanceof String) {
            addLayoutComponent((String) constraints, comp);
            return;
        }
        if (constraints == null || !(constraints instanceof RCPosition)) {
            throw new CalcLayoutException("Cannot add to layout: constraint must be instance of RCPosition or String.");
        }
        RCPosition position = (RCPosition) constraints;
        addLayoutComponent(position.toString(), comp);
    }


    @Override
    public Dimension maximumLayoutSize(Container parent) {
        return getSize(parent, Component::getMaximumSize);
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0.5f;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0.5f;
    }

    @Override
    public void invalidateLayout(Container target) {
    }

    /**
     * {@inheritDoc}
     *
     * @throws CalcLayoutException if  constraints are not met.
     */
    @Override
    public void addLayoutComponent(String name, Component comp) {
        String[] elems = name.trim().split(",");
        if (elems.length != 2) {
            throw new CalcLayoutException("Cannot add to layout: invalid position.");
        }
        try {
            int row = Integer.parseInt(elems[0]);
            int height = Integer.parseInt(elems[1]);

            RCPosition position = new RCPosition(row, height);
            if (components.values().contains(position)) {
                throw new CalcLayoutException("There is already an element at this location.");
            }

            checkRowAndColumn(position);
            components.put(comp, position);

        } catch (NumberFormatException ignorable) {
            throw new CalcLayoutException("Cannot add to layout : invalid position");
        }
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        components.remove(comp);
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return getSize(parent, Component::getPreferredSize);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return getSize(parent, Component::getMinimumSize);
    }

    @Override
    public void layoutContainer(Container parent) {
        Insets insets = parent.getInsets();

        int availableWidth = parent.getWidth() - insets.left - insets.right - (MAX_COLUMNS - 1) * gap;
        int availableHeight = parent.getHeight() - insets.top - insets.bottom - (MAX_ROWS - 1) * gap;
        double componentWidth = availableWidth / (double) MAX_COLUMNS;
        double componentHeight = availableHeight / (double) MAX_ROWS;

        for (Component component : parent.getComponents()) {
            RCPosition position = components.get(component);
            Rectangle r;
            if (position.equals(START)) {
                r = new Rectangle(insets.left, insets.top, (int) (componentWidth * 5 + 4 * gap), (int) componentHeight);
                component.setBounds(r);
                continue;
            }
            r = getRectangle(insets, (int) componentWidth, (int) componentHeight, position);
            component.setBounds(r);
        }
    }

    /**
     * Returns the rectangle that represents  the component at the given position.
     *
     * @param insets          insets of parent container.
     * @param componentWidth  desired component width.
     * @param componentHeight desired component height.
     * @param position        desired position.
     * @return the rectangle that represents the component at the given position.
     */
    private Rectangle getRectangle(Insets insets, int componentWidth, int componentHeight, RCPosition position) {
        return new Rectangle(insets.left + componentWidth * (position.getColumn() - 1) + gap * (position.getColumn() - 1),
                insets.top + componentHeight * (position.getRow() - 1) + gap * (position.getRow() - 1),
                componentWidth, componentHeight);
    }

    /**
     * Checks if the required restrictions are met.
     *
     * @param position the position we want to check.
     * @throws CalcLayoutException if it is not possible to add new element at the given position.
     */
    private void checkRowAndColumn(RCPosition position) {
        int row = position.getRow();
        int column = position.getColumn();

        if (row < 1 || column < 1) {
            throw new CalcLayoutException("Cannot add to layout: the minimum column and row value is 1.");
        }

        if (row > MAX_ROWS || column > MAX_COLUMNS) {
            throw new CalcLayoutException("Cannot add to layout : the maximum possible value for the row is 5, and for the column is 7.");
        }

        if (row == 1 && (column > 1 && column < 6)) {
            throw new CalcLayoutException("Cannot add to layout: position " + row + " , " + column + " is reserved.");
        }
    }

    /**
     * Auxiliary method for computing the minimum and preferred layout size.
     *
     * @param parent container.
     * @param size   method handle to the desired size.
     * @return required minimum/preferred layout dimensions.
     */
    private Dimension getSize(Container parent, Function<Component, Dimension> size) {
        int width = 0;
        int height = 0;

        for (Component comp : parent.getComponents()) {
            Dimension d = size.apply(comp);

            if (d == null) {
                continue;
            }

            if (components.get(comp).equals(START)) {
                d.width = (d.width - 4 * gap) / 5;
            }

            if (d.width > width) {
                width = d.width;
            }

            if (d.height > height) {
                height = d.height;
            }
        }
        Insets insets = parent.getInsets();
        return new Dimension(MAX_COLUMNS * width + (MAX_COLUMNS - 1) * gap + insets.left + insets.right,
                MAX_ROWS * height + (MAX_ROWS - 1) * gap + insets.top + insets.bottom);
    }


}
