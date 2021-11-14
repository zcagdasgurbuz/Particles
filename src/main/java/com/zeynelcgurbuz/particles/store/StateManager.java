package com.zeynelcgurbuz.particles.store;

import com.zeynelcgurbuz.particles.Vector;
import com.zeynelcgurbuz.particles.redux.Store;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public enum StateManager {
    INSTANCE;

    private ParticlesState defaultState = new ParticlesState(1200, 800, (new Vector()), (new Vector()), 400,
            5, 4, 5, -1, 10, 20, 0.0,
            0.0, false, 30, 70, 0.0, 0.0, false,
            -1.0, 1.0, 0.5, 15.0, true,
            false, 0.25, 0.1, true, false, true, null);

    private static final String CONFIG_FILE_NAME = "savedStates.particles";
    public static final String LAST_STATE = "Lyex^H-58_-vwxt^";


    private ObservableList<ParticlesState> states;
    private Store<ParticlesState> store;

    public void initialize(){
        if(!readStates()){
            states = FXCollections.observableArrayList();
        }
        ParticlesState last = searchSavedLastState();
        if(last != null){

        } else {
            store.dispatch(new SetStateAction(defaultState));
        }
    }


    @SuppressWarnings("unchecked")
    public boolean readStates(){
        ArrayList<ParticlesState> temp;
        try {
            FileInputStream fileIn = new FileInputStream(CONFIG_FILE_NAME);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            temp = (ArrayList<ParticlesState>) objectIn.readObject();
            states = FXCollections.observableArrayList(temp);
            objectIn.close();
            fileIn.close();
            return true;
        } catch (IOException | ClassNotFoundException e) {
           return false;
        }

    }

    public boolean writeStates(){
        try {
            FileOutputStream fileOut = new FileOutputStream(CONFIG_FILE_NAME);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            ArrayList<ParticlesState> temp = new ArrayList<>(states);
            objectOut.writeObject(temp);
            objectOut.close();
            fileOut.close();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    private ParticlesState searchSavedLastState(){
        ParticlesState last = new ParticlesState(LAST_STATE);
        int idx = states.indexOf(last);
        if (idx >= 0) {
            last = states.get(idx);
            states.remove(idx);
            return last;
        }
        return null;
    }


    public void saveState(ParticlesState state, String name) {
        state.setName(name);
        states.add(state);
        writeStates();
    }

    public void setStore(Store<ParticlesState> store) {
        this.store = store;
    }
}
