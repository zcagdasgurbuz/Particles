package com.zeynelcgurbuz.particles;

import com.zeynelcgurbuz.particles.animation.ParticleAnimator;
import com.zeynelcgurbuz.particles.redux.Store;
import com.zeynelcgurbuz.particles.store.ParticlesReducer;
import com.zeynelcgurbuz.particles.store.ParticlesState;
import com.zeynelcgurbuz.particles.store.SaveLoadService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Objects;

/**
 * The app class, actual starting point of the application
 */
public class App extends Application {

    /**
     * The Save load service.
     */
    SaveLoadService saveLoadService;

    @Override
    public void start(Stage stage) throws IOException {

        saveLoadService = SaveLoadService.INSTANCE;
        ParticlesReducer reducer = new ParticlesReducer();
        Store<ParticlesState> store = new Store<>(saveLoadService.dummy, reducer);
        //save / load service
        saveLoadService.setStore(store);
        saveLoadService.initialize();

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("ui/main-view.fxml"));
        //inject store and manager to the classes that needs them
        fxmlLoader.setControllerFactory((Class<?> type) -> {
            try {
                for (Constructor<?> c : type.getConstructors()) {
                    //only one param and expects store
                    if (c.getParameterCount() == 1 && c.getParameterTypes()[0] == Store.class) {
                        return c.newInstance(store);
                    }
                    //two params expect store and manager
                    if (c.getParameterCount() == 2 && c.getParameterTypes()[0] == Store.class &&
                            c.getParameterTypes()[1] == SaveLoadService.class) {
                        return c.newInstance(store, saveLoadService);
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
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/img/icon.png")).toExternalForm()));
        stage.setTitle("Particles");
        stage.setScene(scene);
        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (KeyCode.F11.equals(event.getCode())) {
                stage.setFullScreen(!stage.isFullScreen());
            }
        });
        stage.show();
        //generate cosmos and animator, then connect them.
        ParticleAnimator animator = new ParticleAnimator(true);
        Cosmos cosmos = new Cosmos(store);
        cosmos.setAnimator(animator);
        animator.start();
    }

    @Override
    public void stop(){
        saveLoadService.saveIfStartupWithLast();
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch();
    }
}
