package com.zeynelcgurbuz.particles;

import com.zeynelcgurbuz.particles.animation.ParticleAnimator;
import com.zeynelcgurbuz.particles.redux.Store;
import com.zeynelcgurbuz.particles.store.ParticlesReducer;
import com.zeynelcgurbuz.particles.store.ParticlesState;
import com.zeynelcgurbuz.particles.store.SetStateAction;
import com.zeynelcgurbuz.particles.store.StateManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Constructor;

public class App extends Application {

    Store<ParticlesState> store;
    boolean saveOnClose;


    @Override
    public void start(Stage stage) throws IOException {

        StateManager manager = StateManager.INSTANCE;
        ParticlesState dummy = new ParticlesState(0,0, null, null, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                false, 0, 0, 0, 0, false, 0,
        0, 0, 0, false, false, 0, 0,
                false, false, false, null);
        dummy.setManager(manager);
        ParticlesReducer reducer = new ParticlesReducer();
        store = new Store<>(dummy, reducer);
        manager.setStore(store);
        manager.initialize();

        //store.dispatch(new SetStateAction(defaultState));


        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("ui/main-view.fxml"));

        //inject store to the classes that have only constructor param and expect store
        fxmlLoader.setControllerFactory((Class<?> type) -> {
            try {
                for (Constructor<?> c : type.getConstructors()) {
                    if (c.getParameterCount() == 1 && c.getParameterTypes()[0] == Store.class) {
                        return c.newInstance(store);
                    }
                }
                // default behavior, if store is not expected
                return type.getDeclaredConstructor().newInstance();
            } catch (Exception exc) {
                throw new RuntimeException(exc);
            }
        });


        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        //change font face
        scene.getStylesheets()
                .add("https://fonts.googleapis.com/css2?family=JetBrains+Mono:wght@100;300;400;500;700;800&display=swap");
        // attach style sheet
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        stage.setTitle("Particles!");
        stage.setScene(scene);
        stage.show();

        //generate cosmos and animator, then connect them.
        ParticleAnimator animator = new ParticleAnimator(true);
        Cosmos cosmos = new Cosmos(store,1200, 800);
        cosmos.setAnimator(animator);
        animator.start();
    }

    @Override
    public void stop(){

    }

    public static void main(String[] args) {
        launch();
    }
}
