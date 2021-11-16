package com.zeynelcgurbuz.particles.store.actions;

import com.zeynelcgurbuz.particles.redux.Action;
import javafx.scene.canvas.GraphicsContext;

public class SetGraphicsContextAction implements Action {
    private final GraphicsContext graphics;

    public SetGraphicsContextAction(GraphicsContext graphics){
        this.graphics = graphics;
    }

    public GraphicsContext getGraphics() {
        return graphics;
    }
}
