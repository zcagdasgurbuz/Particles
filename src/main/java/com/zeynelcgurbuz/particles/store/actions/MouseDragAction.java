package com.zeynelcgurbuz.particles.store.actions;

import com.zeynelcgurbuz.particles.Vector;
import com.zeynelcgurbuz.particles.redux.Action;

/**
 * The type Mouse drag action, to inform mouse drag event.
 */
public class MouseDragAction implements Action {
    /**
     * The new drag position
     */
    private final Vector value;

    /**
     * Instantiates a new Mouse drag action.
     *
     * @param value the new drag position
     */
    public MouseDragAction(Vector value) {
        this.value = value;
    }

    /**
     * Gets new drag position value.
     *
     * @return new drag position value.
     */
    public Vector getValue() {
        return value;
    }
}
