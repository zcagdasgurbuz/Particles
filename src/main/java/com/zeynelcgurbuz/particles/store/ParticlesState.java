package com.zeynelcgurbuz.particles.store;

import com.zeynelcgurbuz.particles.Vector;

import java.io.Serializable;
import java.util.Arrays;

public class ParticlesState implements Serializable {
    //private final List<String> items;
    private Vector mouseDragPosition;
    private Vector forceFieldPosition;


    private int particleCount = 400;
    private int colorCount = 5;


    private int inRangeStyle = 4;
    private int belowRangeStyle = 5;
    private int outRangeStyle = 21;


    private double minRLower = 10;
    private double minRUpper = 20;
    private double minRMean = 0.0;
    private double minRStd = 0.0;
    private boolean minRStandard = false;

    private double attractionMin = -1.0;
    private double attractionMax = 1.0;
    private double attractionMean = 0.5;
    private double attractionStd = 15.0;
    private boolean attractionStandard = true;
    private boolean negateSelfAttraction = false;

    private double maxRLower = 30.0;
    private double maxRUpper = 70.0;
    private double maxRMean = 0.0;
    private double maxRStd = 0.0;
    private boolean maxRStandard = false;

    private double friction = 0.25;
    private double g = 0.1; //gravity constant

    private boolean molAttract = true;
    private boolean gravAttract = false;
    private boolean walls = true;





    public ParticlesState(Vector mouseDragPosition) {
        this.mouseDragPosition = mouseDragPosition;
    }


/*    public ParticlesState fromState(ParticlesState state) {
        if (items.equals(this.items)) {
            return this;
        } else {
            return new TodoState(items);
        }
    }*/

    @Override
    public boolean equals(Object other){
        if(other == this){
            return true;
        } else if ( !this.getClass().getName().equals(other.getClass().getName()) ) {
            return false;
        }
        ParticlesState otherState = (ParticlesState)other;
        return mouseDragPosition.equals(otherState.mouseDragPosition);
    }


/*    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("TodoState (items=" + items.size() + ")[\n");

        items.forEach(item -> builder.append("\t").append(item).append("\n"));

        builder.append("]");
        return builder.toString();
    }*/

    public Vector getMouseDragPosition() {
        return mouseDragPosition;
    }

    public ParticlesState setMouseDragPosition(Vector mouseDragPosition){
        this.mouseDragPosition = mouseDragPosition;
        return this;
    }
}
