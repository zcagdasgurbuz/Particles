package com.zeynelcgurbuz.particles.store;

import com.zeynelcgurbuz.particles.Vector;
import com.zeynelcgurbuz.particles.redux.Store;
import com.zeynelcgurbuz.particles.store.actions.RestartAction;
import com.zeynelcgurbuz.particles.store.actions.SetStateAction;
import com.zeynelcgurbuz.particles.ui.util.UiUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * The Save load service, used enum to ensure that there is only one instance
 */
public enum SaveLoadService {
    /**
     * Instance save load service.
     */
    INSTANCE;
    /**
     * The Save on close.
     */
    private boolean saveOnClose;

    /**
     * The Default state.
     */
    private final ParticlesState defaultState = new ParticlesState(1200, 800, (new Vector()), (new Vector()), 400,
            9, 4, 4, 5, 0, 10, 30, 20.0,
            5.0, false, 30, 100, 75.0, 15.0, false, true,
            -10.0, 10.0, 0.5, 5.0, true,
            true, true, 0.2, 0.01, true, false, true, true, null);

    /**
     * The Dummy.
     */
    public final ParticlesState dummy = new ParticlesState(0, 0, null, null, 0,
            0, 1, 0, 0, 0, 0, 0, 0, 0,
            false, 0, 0, 0, 0, false, false, 0,
            0, 0, 0, false, false, false, 0, 0,
            false, false, false, false, null);


    /**
     * The saved file name.
     */
    private static final String CONFIG_FILE_NAME = "savedStates.particles";
    /**
     * The special word for last state name.
     */
    public static final String LAST_STATE = "Lyex^H-58_-vwxt^";
    /**
     * The current states, that are saved or retrieved.
     */
    private ObservableList<ParticlesState> states = null;
    /**
     * The store.
     */
    private Store<ParticlesState> store;

    /**
     * Initializes the service. Looks up for saved states, and last saved state and creates new list of states based
     * on that.
     */
    public void initialize() {
        if(!readStates()){
            states = FXCollections.observableArrayList();
        }
        dummy.setName("dummy"); // dummy state
        ParticlesState last = searchSavedLastState();
        if (last != null) {
            saveOnClose = true;
            store.dispatch(new SetStateAction(last));
        } else {
            saveOnClose = false;
            defaultState.setName("default");
            store.dispatch(new SetStateAction(defaultState));
        }
    }

    /**
     * Retrieves states that are in the service.
     *
     * @return the states
     */
    public ObservableList<ParticlesState> getStates() {
        return states;
    }

    /**
     * Sets whether save on close is active.
     *
     * @param saveOnClose the save on close
     */
    public void setSaveOnClose(boolean saveOnClose) {
        this.saveOnClose = saveOnClose;
    }

    /**
     * Whether save on close is active.
     *
     * @return the boolean
     */
    public boolean isSaveOnClose() {
        return saveOnClose;
    }

    /**
     * Save if startup with last.
     */
    public void saveIfStartupWithLast(){
        if(saveOnClose){
            requestSave(LAST_STATE);
        }
    }

    /**
     * Requests save.
     *
     * @param name the name to be saved
     * @return the whether saving is successful
     */
    public boolean requestSave(String name) {
        boolean okToSave;
        ParticlesState temp = new ParticlesState(name);
        //prompt user if exists
        if (states.contains(temp)) {
            okToSave = UiUtil.tools.alertYesOrNo(name + " is already exists, do you want to overwrite it?");
            if (okToSave) {
                states.remove(temp);
            }
        } else {
            okToSave = true;
        }
        //do the thing!
        if (okToSave) {
            ParticlesState stateToSave = store.getState();
            stateToSave.setName(name);
            states.add(stateToSave);
            return writeStates();
        }
        return false;
    }

    /**
     * Requests load a state, utilizes store.
     *
     * @param state the state to be loaded
     */
    public void requestLoad(ParticlesState state){
        store.dispatch(new SetStateAction(state));
        store.dispatch(new RestartAction());
    }

    /**
     * Requests remove a state from the states stored/saved.
     *
     * @param state the state
     */
    public void requestRemove(ParticlesState state) {
        if (UiUtil.tools.alertYesOrNo("Do you want to remove " + state.toString() + "?"))
            states.remove(state);
        writeStates();
    }

    /**
     * Read states from saved file.
     *
     * @return whether reading from file is successful
     */
    @SuppressWarnings("unchecked")
    public boolean readStates() {
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

    /**
     * Write states to file.
     *
     * @return whether writing to file is successful
     */
    public boolean writeStates() {
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

    /**
     * Search saved last state in states stored in this service.
     *
     * @return the particles state if found, null if not found
     */
    private ParticlesState searchSavedLastState() {
        ParticlesState last = new ParticlesState(LAST_STATE);
        int idx = states.indexOf(last);
        if (idx >= 0) {
            last = states.get(idx);
            states.remove(idx);
            writeStates();
            return last;
        }
        return null;
    }

    /**
     * Sets store to be used in the service.
     *
     * @param store the store
     */
    public void setStore(Store<ParticlesState> store) {
        this.store = store;
    }
}
