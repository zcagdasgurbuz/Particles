package com.zeynelcgurbuz.particles.ui;

import com.zeynelcgurbuz.particles.redux.Store;
import com.zeynelcgurbuz.particles.store.ParticlesState;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class MainViewController {

    @FXML
    public AnchorPane topContainer;

    @FXML
    public Pane display;

    public MainViewController(Store<ParticlesState> store){

    }


}
