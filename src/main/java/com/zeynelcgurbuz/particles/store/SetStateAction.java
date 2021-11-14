package com.zeynelcgurbuz.particles.store;

import com.zeynelcgurbuz.particles.redux.Action;

public class SetStateAction implements Action {
    private ParticlesState state;
    public SetStateAction(ParticlesState state){
        this.state = state;
    }
    public ParticlesState getState(){
        return state;
    }
}
