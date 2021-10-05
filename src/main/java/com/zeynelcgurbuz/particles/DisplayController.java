package com.zeynelcgurbuz.particles;

import com.zeynelcgurbuz.particles.animation.Animator;
import com.zeynelcgurbuz.particles.animation.ParticleAnimator;
import com.zeynelcgurbuz.particles.redux.Store;
import com.zeynelcgurbuz.particles.store.MouseDragAction;
import com.zeynelcgurbuz.particles.store.ParticlesState;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class DisplayController {

    @FXML
    Canvas canvas;

    @FXML
    Pane container;

    private GraphicsContext gr;
    private Cosmos cosmos;
    private Animator animator;

    private DoubleProperty x;
    private DoubleProperty y;

    private ObjectBinding<Point2D> center;
    private Store<ParticlesState> store;

    public DisplayController(Store<ParticlesState> store){
        this.store = store;
    }
    //DisplayController(){}


    @FXML
    public void initialize() {
        canvas.widthProperty().bind(container.widthProperty());
        canvas.heightProperty().bind(container.heightProperty());
        gr = canvas.getGraphicsContext2D();

        x = new SimpleDoubleProperty(0);
        y = new SimpleDoubleProperty(0);

        center = new ObjectBinding<>() {
            {
                super.bind(x,y);
            }
            @Override
            protected Point2D computeValue() {
                System.out.println(x.doubleValue() + " -- " + y.doubleValue());
                return new Point2D(x.doubleValue(), y.doubleValue());
            }
        };
        addMouseHandler();

        container.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        cosmos = new Cosmos(store,100, 5, 1200, 800);
        //cosmos.setBoundaries(0, 0, 600, 400);
        canvas.widthProperty().addListener((observable, oldValue, newValue) -> {
            cosmos.setBoundaries(0, 0, (int) newValue.doubleValue(), (int) canvas.getHeight());
        });
        canvas.heightProperty().addListener((observable, oldValue, newValue) -> {
            cosmos.setBoundaries(0, 0, (int) canvas.getWidth(), (int) newValue.doubleValue());
        });
        animator = new ParticleAnimator(true);
        cosmos.setGraphics(gr);
        cosmos.setAnimator(animator);
        animator.start();
    }

    public void startCosmos() {
        gr.setFill(Color.WHITE);
        gr.fillRect(50, 50, 50, 50);
        System.out.println(canvas.getHeight());
        System.out.println(canvas.getWidth());
    }


    private void addMouseHandler() {
        final double[] captured = new double[2];
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            captured[0] = event.getX();
            captured[1] = event.getY();

        });
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            //x.set(event.getX() - captured[0]);
            //y.set(event.getY() - captured[1]);
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

    public Point2D getCenter() {
        return center.get();
    }

    public ObjectBinding<Point2D> centerProperty() {
        return center;
    }



}
