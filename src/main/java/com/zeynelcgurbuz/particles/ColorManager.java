package com.zeynelcgurbuz.particles;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class ColorManager {
    private static final ArrayList<Color> colors = new ArrayList<>();
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

    private ColorManager() {
    }

    public static Color next() {
        //modulo con be used
        if (currentIdx >= colors.size()) {
            currentIdx = 0;
        }
        return colors.get(currentIdx++);
    }

}
