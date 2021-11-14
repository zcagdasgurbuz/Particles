package com.zeynelcgurbuz.particles.store;

import com.zeynelcgurbuz.particles.redux.Action;

public class LoadRequestAction implements Action {
    private ParticlesState state;

    public LoadRequestAction(ParticlesState state){
        this.state = state;
    }

    public ParticlesState getState() {
        return state;
    }
}
