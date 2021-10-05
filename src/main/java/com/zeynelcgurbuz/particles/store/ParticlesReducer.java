package com.zeynelcgurbuz.particles.store;

import com.zeynelcgurbuz.particles.Vector;
import com.zeynelcgurbuz.particles.redux.Action;
import com.zeynelcgurbuz.particles.redux.Reducer;

import java.util.Arrays;

public class ParticlesReducer implements Reducer<ParticlesState> {
    @Override
    public ParticlesState reduce(ParticlesState oldState, Action action) {
        if (action instanceof MouseDragAction) {
            MouseDragAction mouseDragAction = (MouseDragAction) action;
            return cloneState(oldState).setMouseDragPosition(mouseDragAction.getValue());
        } else if (action instanceof  MouseDragStopAction){
            return cloneState(oldState).setMouseDragPosition(new Vector());
        }

        return oldState;
    }


    private ParticlesState cloneState(ParticlesState oldState){
        return new ParticlesState(new Vector(oldState.getMouseDragPosition()));
    }

}
