package com.zeynelcgurbuz.particles.store.actions;

import com.zeynelcgurbuz.particles.redux.Action;
import javafx.scene.canvas.GraphicsContext;

/**
 * Set graphics context action, is used to associate graphics object with state.
 */
public class SetGraphicsContextAction implements Action {
    /**
     * Graphics context to be used to draw particles
     */
    private final GraphicsContext graphics;

    /**
     * Constructor
     *
     * @param graphics the graphics context to be used to draw particles
     */
    public SetGraphicsContextAction(GraphicsContext graphics){
        this.graphics = graphics;
    }

    /**
     * Retrieves graphics context
     *
     * @return the graphics context
     */
    public GraphicsContext getGraphics() {
        return graphics;
    }
}
