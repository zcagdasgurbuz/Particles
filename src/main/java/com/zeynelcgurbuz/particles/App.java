package com.zeynelcgurbuz.particles;

import com.zeynelcgurbuz.particles.animation.ParticleAnimator;
import com.zeynelcgurbuz.particles.redux.Store;
import com.zeynelcgurbuz.particles.store.ParticlesReducer;
import com.zeynelcgurbuz.particles.store.ParticlesState;
import com.zeynelcgurbuz.particles.store.StateManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Constructor;

public class App extends Application {

    StateManager manager;

    @Override
    public void start(Stage stage) throws IOException {

        manager = StateManager.INSTANCE;
        ParticlesReducer reducer = new ParticlesReducer();
        Store<ParticlesState> store = new Store<>(manager.dummy, reducer);
        //save / load service
        manager.setStore(store);
        manager.initialize();

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("ui/main-view.fxml"));

        //inject store to the classes that have only constructor param and expect store
        fxmlLoader.setControllerFactory((Class<?> type) -> {
            try {
                for (Constructor<?> c : type.getConstructors()) {
                    if (c.getParameterCount() == 1 && c.getParameterTypes()[0] == Store.class) {
                        return c.newInstance(store);
                    }
                    if (c.getParameterCount() == 2 && c.getParameterTypes()[0] == Store.class &&
                            c.getParameterTypes()[1] == StateManager.class) {
                        return c.newInstance(store, manager);
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
        manager.saveIfStartupWithLast();
    }

    public static void main(String[] args) {
        launch();
    }
}
