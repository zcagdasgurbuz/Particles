package com.zeynelcgurbuz.particles.store.actions;

import com.zeynelcgurbuz.particles.redux.Action;

/**
 * The type Boundaries changed action, to inform that the boundaries of the cosmos has been changed
 */
public class BoundariesChangedAction implements Action {

    /**
     * The width of the boundaries.
     */
    private final double width;
    /**
     * The height of the boundaries.
     */
    private final double height;

    /**
     * Constructor
     *
     * @param width  the new width
     * @param height the new height
     */
    public BoundariesChangedAction(double width, double height){
        this.width = width;
        this.height = height;
    }

    /**
     * Gets new width.
     *
     * @return the new width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Gets new height.
     *
     * @return the new height
     */
    public double getHeight() {
        return height;
    }
}
