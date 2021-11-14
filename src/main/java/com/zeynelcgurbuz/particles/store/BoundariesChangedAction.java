package com.zeynelcgurbuz.particles.store;

import com.zeynelcgurbuz.particles.redux.Action;

public class BoundariesChangedAction implements Action {

    private double width, height;

    public BoundariesChangedAction(double width, double height){
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
