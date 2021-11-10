package com.zeynelcgurbuz.particles.ui;

//import com.jfoenix.controls.JFXHamburger;
//import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Sliding menu controller, handles the animations
 */
public class SlidingMenuController {

    /** The sliding menu container. */
    public StackPane slidingMenu;
    /** The hamburger menu button. */
    //public JFXHamburger hamburgerMenuButton;
    /** The menu content. */
    public VBox circlesMenu;
    public Button menuButton;
    public AnchorPane hamburgerShape;
    public Rectangle topLine;
    public Rectangle midLine;
    public Rectangle bottomLine;
    public ScrollPane menuContent;

    /** Whether the menu open/visible. */
    private boolean isOpen;
    /** The timeline of sliding animation. */
    private Timeline timeline;
    /** The hamburger menu button animation */
    //private HamburgerBackArrowBasicTransition burgerTask;
    //private HamburgerSlideCloseTransition burgerTask; // different animation

    /**
     * Initialize method.
     */
    @FXML
    public void initialize() {
        //to see the menu moving
        slidingMenu.setBorder(new Border(new BorderStroke(Color.WHITE,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        //menu animation is being defined here!
        final double menuButtonInitialX = menuButton.getTranslateX();
        timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(slidingMenu.translateXProperty(), -302)),
                new KeyFrame(Duration.ZERO, new KeyValue(menuButton.translateXProperty(), menuButtonInitialX)),
                new KeyFrame(Duration.ZERO, new KeyValue(topLine.rotateProperty(), 0)),
                new KeyFrame(Duration.ZERO, new KeyValue(midLine.translateXProperty(), 0)),
                new KeyFrame(Duration.ZERO, new KeyValue(bottomLine.rotateProperty(), 0)),
                new KeyFrame(Duration.millis(500), new KeyValue(slidingMenu.translateXProperty(), 0)),
                new KeyFrame(Duration.millis(500), new KeyValue(menuButton.translateXProperty(), menuButtonInitialX - 70)),
                new KeyFrame(Duration.millis(500), new KeyValue(topLine.rotateProperty(), -35)),
                new KeyFrame(Duration.millis(500), new KeyValue(midLine.translateXProperty(), 10)),
                new KeyFrame(Duration.millis(500), new KeyValue(bottomLine.rotateProperty(), 35))
        );
        isOpen = false;
    }

    /**
     * This method handles the menu sliding function by invoking timeline normally or backwards depends
     * on its current position
     */
    public void menuButtonAction() {

        // adjust the direction of play and start playing, if not already done
        boolean playing = (timeline.getStatus() == Animation.Status.RUNNING);
        if (!isOpen) {
            timeline.setRate(1);
            if (!playing) {
                timeline.playFromStart();
            }
            isOpen = true;
        } else {
            timeline.setRate(-1);
            if (!playing) {
                timeline.playFrom("end");
            }
            isOpen = false;
        }
    }
}
