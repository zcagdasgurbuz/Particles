package com.zeynelcgurbuz.particles.store;

import com.zeynelcgurbuz.particles.redux.Action;
import javafx.scene.canvas.GraphicsContext;

public class SetGraphicsContextAction implements Action {
    private GraphicsContext graphics;

    public SetGraphicsContextAction(GraphicsContext graphics){
        this.graphics = graphics;
    }

    public GraphicsContext getGraphics() {
        return graphics;
    }
}
