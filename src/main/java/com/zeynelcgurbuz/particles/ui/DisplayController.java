package com.zeynelcgurbuz.particles.ui;

import com.zeynelcgurbuz.particles.Vector;
import com.zeynelcgurbuz.particles.redux.Store;
import com.zeynelcgurbuz.particles.store.ParticlesState;
import com.zeynelcgurbuz.particles.store.actions.BoundariesChangedAction;
import com.zeynelcgurbuz.particles.store.actions.MouseDragAction;
import com.zeynelcgurbuz.particles.store.actions.SetGraphicsContextAction;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


/**
 * The type Display controller.
 */
public class DisplayController {

    /**
     * The Canvas.
     */
    public Canvas canvas;

    /**
     * The Container.
     */
    public Pane container;

    /**
     * The Store.
     */
    private final Store<ParticlesState> store;

    /**
     * Instantiates a new Display controller.
     *
     * @param store the store
     */
    public DisplayController(Store<ParticlesState> store) {
        this.store = store;
    }

    /**
     * Initialize, the display controller
     */
    @FXML
    public void initialize() {
        canvas.widthProperty().bind(container.widthProperty());
        canvas.heightProperty().bind(container.heightProperty());
        GraphicsContext gr = canvas.getGraphicsContext2D();


        addMouseHandler();
        container.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        canvas.widthProperty().addListener((observable, oldValue, newValue) ->
                store.dispatch(new BoundariesChangedAction(newValue.doubleValue(), canvas.getHeight())));
        canvas.heightProperty().addListener((observable, oldValue, newValue) ->
                store.dispatch(new BoundariesChangedAction(canvas.getWidth(), newValue.doubleValue())));
        store.dispatch(new SetGraphicsContextAction(gr));
    }


    /**
     * Add mouse handler to window.
     */
    private void addMouseHandler() {
        final double[] captured = new double[2];
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            captured[0] = event.getX();
            captured[1] = event.getY();

        });
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            Vector currentPos = store.getState().getMouseDragPosition();
            double x = event.getX() - captured[0];
            double y = event.getY() - captured[1];
            captured[0] = event.getX();
            captured[1] = event.getY();
            store.dispatch(new MouseDragAction(currentPos.add(new Vector(x, y))));
        });
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                store.dispatch(new MouseDragAction(new Vector()));
            }
        });
    }
}
