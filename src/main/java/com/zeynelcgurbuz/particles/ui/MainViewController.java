package com.zeynelcgurbuz.particles.ui;

import com.zeynelcgurbuz.particles.redux.Store;
import com.zeynelcgurbuz.particles.store.ParticlesState;
import com.zeynelcgurbuz.particles.store.actions.RestartAction;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * The type Main view controller.
 */
public class MainViewController {

    /**
     * The top container.
     */
    @FXML
    public AnchorPane topContainer;

    /**
     * The particles display.
     */
    @FXML
    public Pane display;


    private Store<ParticlesState> store;

    /**
     * Instantiates a new Main view controller.
     *
     * @param store the store
     */
    public MainViewController(Store<ParticlesState> store) {
        this.store = store;
    }

    @FXML
    public void initialize(){
        Button menuButton =  (Button)topContainer.lookup("#slidingMenuButton");

        KeyCombination ctrlR = new KeyCodeCombination(KeyCode.R, KeyCodeCombination.CONTROL_DOWN);
        KeyCombination ctrlShiftC = new KeyCodeCombination(KeyCode.C, KeyCodeCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);
        topContainer.setOnKeyPressed(event -> {
            if(ctrlR.match(event)){
                store.dispatch(new RestartAction());
            }
            if(ctrlShiftC.match(event)){
                menuButton.setVisible(!menuButton.isVisible());
            }
        });

    }
}
