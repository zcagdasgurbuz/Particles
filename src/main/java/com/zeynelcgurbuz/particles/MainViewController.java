package com.zeynelcgurbuz.particles;

import com.zeynelcgurbuz.particles.redux.Store;
import com.zeynelcgurbuz.particles.store.ParticlesState;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class MainViewController {

    @FXML
    public StackPane topContainer;

    @FXML
    public Pane display;



    public MainViewController(Store<ParticlesState> store){

    }


}
