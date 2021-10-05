package com.zeynelcgurbuz.particles;

import com.zeynelcgurbuz.particles.redux.Reducer;
import com.zeynelcgurbuz.particles.redux.Store;
import com.zeynelcgurbuz.particles.store.ParticlesReducer;
import com.zeynelcgurbuz.particles.store.ParticlesState;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Locale;
import java.util.ResourceBundle;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        ParticlesState state = new ParticlesState(new Vector());
        ParticlesReducer reducer = new ParticlesReducer();
        Store<ParticlesState> store = new Store<>(state, reducer);

/*        Callback<Class<?> type> controllerFactory = controllerType -> {
            try {
                for (Constructor<?> c : type.getConstructors()) {
                    if (c.getParameterCount() == 1 && c.getParameterTypes()[0] == Model.class) {
                        return c.newInstance(model);
                    }
                }
                // default behavior: invoke no-arg constructor:
                return type.newInstance();
            } catch (Exception exc) {
                throw new RuntimeException(exc);
            }


            if (controllerType == MainViewController.class) {
                return new MainViewController(store);
            } else {
                throw new IllegalStateException("Unexpected controller class: "+controllerType.getName());
            }
        };*/

            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main-view.fxml"));

            //FXMLLoader fxmlLoader = new FXMLLoader();
            //fxmlLoader.setLocation(getClass().getClassLoader().getResource("/main-view.fxml"));
            fxmlLoader.setControllerFactory((Class<?> type) -> {
                try {
                    for (Constructor<?> c : type.getConstructors()) {
                        if (c.getParameterCount() == 1 && c.getParameterTypes()[0] == Store.class) {
                            return c.newInstance(store);
                        }
                    }
                    // default behavior: invoke no-arg constructor:
                    return type.newInstance();
                } catch (Exception exc) {
                    throw new RuntimeException(exc);
                }
            });


        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        //MainViewController controller = fxmlLoader.getController();


        stage.setTitle("Particles!");
        stage.setScene(scene);
        stage.show();
/*        controller.centerProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
        });*/


    }

    public static void main(String[] args) {
        launch();
    }
}
