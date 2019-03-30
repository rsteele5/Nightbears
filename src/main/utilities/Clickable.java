package main.utilities;

/**
 * Contains the necessary functions needed to be a clickable object in the game.
 *
 * @see #onClick()
 * @see #setOnClick(Action)
 * @see #contains(int, int)
 * @see #setPressed(boolean)
 */
public interface Clickable {
    /**
     * Executes when the object is clicked
     */
    void onClick();

    /**
     * Sets the desired functionality of the clickable when it is clicked. The method Accepts and {@link Action}, but a
     * parameterless lamba expression can be passed instead.
     *
     * @param   onClick A function dictating how the clickable will behave on click.
     */
    void setOnClick(Action onClick);

    /**
     * A check to see if the coordinates passed are contained within the object.
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @return  true: the coordinate is contained in the object. <br>
     *          false: the coordinate is not contained in the object.
     */
    boolean contains(int x, int y);
    void setPressed(boolean isPressed);
}
