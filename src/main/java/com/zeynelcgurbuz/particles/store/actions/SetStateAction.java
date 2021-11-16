package com.zeynelcgurbuz.particles.store.actions;

import com.zeynelcgurbuz.particles.redux.Action;
import com.zeynelcgurbuz.particles.store.ParticlesState;

public class SetStateAction implements Action {
    private final ParticlesState state;

    public SetStateAction(ParticlesState state) {
        this.state = state;
    }

    public ParticlesState getState() {
        return state;
    }
}
