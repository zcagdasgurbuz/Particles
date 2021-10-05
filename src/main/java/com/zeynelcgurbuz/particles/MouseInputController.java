package com.zeynelcgurbuz.particles;

import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class MouseInputController {

    @FXML
    public Pane mouseInputPane;
    private DoubleProperty x;
    private DoubleProperty y;

    private ObjectBinding<Point2D> center;

    @FXML
    public void initialize(){
/*        mouseInput.prefHeightProperty().bind(topContainer.heightProperty());
        mouseInput.prefWidthProperty().bind(topContainer.widthProperty());
        display.prefHeightProperty().bind(topContainer.heightProperty());
        display.prefWidthProperty().bind(topContainer.widthProperty());*/

        x = new SimpleDoubleProperty(0);
        y = new SimpleDoubleProperty(0);

        center = new ObjectBinding<Point2D>() {
            {
                super.bind(x,y);
            }
            @Override
            protected Point2D computeValue() {
                return new Point2D(x.doubleValue(), y.doubleValue());
            }
        };
        addMouseHandler();
    }

    private void addMouseHandler() {
        final double[] captured = new double[2];
        mouseInputPane.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            captured[0] = event.getX();
            captured[1] = event.getY();
        });
        mouseInputPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            x.set(event.getX() - captured[0]);
            y.set(event.getY() - captured[1]);
        });
        mouseInputPane.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                x.set(0);
                y.set(0);
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
