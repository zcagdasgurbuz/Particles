package com.zeynelcgurbuz.particles.store;

import com.zeynelcgurbuz.particles.Vector;

import java.util.Arrays;

public class ParticlesState {
    //private final List<String> items;
    private Vector mouseDragPosition;

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
