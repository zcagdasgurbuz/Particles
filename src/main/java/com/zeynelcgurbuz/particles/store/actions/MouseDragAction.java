package com.zeynelcgurbuz.particles.store.actions;

import com.zeynelcgurbuz.particles.Vector;
import com.zeynelcgurbuz.particles.redux.Action;

public class MouseDragAction implements Action {
    private final Vector value;

    public MouseDragAction(Vector value) {
        this.value = value;
    }

    public Vector getValue() {
        return value;
    }
}
