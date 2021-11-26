package com.zeynelcgurbuz.particles;

import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Color manager, fake random color generator.
 */
public class ColorManager {
    /**
     * The colors.
     */
    private static final ArrayList<Color> colors = new ArrayList<>();
    /**
     * The last color id
     */
    private static int currentIdx = 0;

    static {
        colors.add(Color.web("#ff0000"));
        colors.add(Color.web("#00ff00"));
        colors.add(Color.web("#0000ff"));
        colors.add(Color.web("#ffff00"));
        colors.add(Color.web("#ffa500"));
        colors.add(Color.web("#00ced1"));
        colors.add(Color.web("#eee8aa"));
        colors.add(Color.web("#00fa9a"));
        colors.add(Color.web("#7f0000"));
        colors.add(Color.web("#696969"));
        colors.add(Color.web("#006400"));
        colors.add(Color.web("#ff69b4"));
        colors.add(Color.web("#6495ed"));
        colors.add(Color.web("#00008b"));
    }

    /**
     * Instantiates a new Color manager.
     */
    private ColorManager() {
    }

    /**
     * Next color.
     *
     * @return the color
     */
    public static Color next() {
        currentIdx %= colors.size();
        return colors.get(currentIdx++);
    }
}
