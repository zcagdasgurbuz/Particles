package com.zeynelcgurbuz.particles.store.actions;

public class SetSaveOnCloseAction {
    boolean saveOnClose;

    public SetSaveOnCloseAction(boolean saveOnClose){
        this.saveOnClose = saveOnClose;
    }

    public boolean getSaveOnClose(){
        return saveOnClose;
    }
}