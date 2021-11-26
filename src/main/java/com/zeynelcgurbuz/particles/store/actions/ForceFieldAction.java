package com.zeynelcgurbuz.particles.store.actions;

import com.zeynelcgurbuz.particles.Vector;
import com.zeynelcgurbuz.particles.redux.Action;

/**
 * The type Mouse drag action, to inform mouse drag event.
 */
public class ForceFieldAction implements Action {
    /**
     * The new force field position
     */
    private final Vector value;

    /**
     * Instantiates a new Mouse drag action.
     *
     * @param value the new force field position
     */
    public ForceFieldAction(Vector value) {
        this.value = value;
    }

    /**
     * Gets new force field position value.
     *
     * @return new force field position value.
     */
    public Vector getValue() {
        return value;
    }
}
