package com.zeynelcgurbuz.particles.store.actions;

import com.zeynelcgurbuz.particles.redux.Action;
import com.zeynelcgurbuz.particles.store.ParticlesState;

/**
 * Set state action, is used to set new state.
 */
public class SetStateAction implements Action {
    /**
     * The new state.
     */
    private final ParticlesState state;

    /**
     * Constructor.
     *
     * @param state the new state
     */
    public SetStateAction(ParticlesState state) {
        this.state = state;
    }

    /**
     * Retrieves the state to be set
     *
     * @return the new state
     */
    public ParticlesState getState() {
        return state;
    }
}
