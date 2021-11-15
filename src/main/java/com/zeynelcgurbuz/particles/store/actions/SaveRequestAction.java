package com.zeynelcgurbuz.particles.store.actions;

import com.zeynelcgurbuz.particles.redux.Action;

public class SaveRequestAction implements Action {
    private String name;

    public SaveRequestAction(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
