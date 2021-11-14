package com.zeynelcgurbuz.particles.store.actions;

import com.zeynelcgurbuz.particles.redux.Action;
import com.zeynelcgurbuz.particles.store.ParticlesState;

public class LoadRequestAction implements Action {
    private ParticlesState state;

    public LoadRequestAction(ParticlesState state){
        this.state = state;
    }

    public ParticlesState getState() {
        return state;
    }
}
