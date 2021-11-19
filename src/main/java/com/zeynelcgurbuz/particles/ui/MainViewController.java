package com.zeynelcgurbuz.particles.ui;

import com.zeynelcgurbuz.particles.redux.Store;
import com.zeynelcgurbuz.particles.store.ParticlesState;
import javafx.fxml.FXML;
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

    /**
     * Instantiates a new Main view controller.
     *
     * @param store the store
     */
    public MainViewController(Store<ParticlesState> store) {
    }
}
