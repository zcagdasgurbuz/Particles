package com.zeynelcgurbuz.particles.store.actions;

import com.zeynelcgurbuz.particles.redux.Action;
import com.zeynelcgurbuz.particles.store.ParticlesState;

public class RemoveRequestAction implements Action {
    private ParticlesState state;

    public RemoveRequestAction(ParticlesState state){
        this.state = state;
    }

    public ParticlesState getState() {
        return state;
    }
}
