package com.zeynelcgurbuz.particles.store;

import com.zeynelcgurbuz.particles.Vector;
import com.zeynelcgurbuz.particles.redux.Action;
import java.util.Arrays;

public class MouseDragAction implements Action {
    private final Vector value;


    public MouseDragAction(Vector value) {
        this.value = value;
    }

    public Vector getValue() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || getClass() != other.getClass())
            return false;
        return value.equals( ((MouseDragAction)other).value);
    }

    @Override
    public int hashCode() {
        return value.toString().hashCode();
    }

    @Override
    public String toString() {
        return "MouseDragAction(" + value + ")";
    }

}
