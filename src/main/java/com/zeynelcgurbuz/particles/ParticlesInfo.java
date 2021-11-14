package com.zeynelcgurbuz.particles;

import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.Arrays;


public class ParticlesInfo implements Serializable {

    private Color[] colors;
    private double[][] attractions;
    private double[][] minDistances;
    private double[][] maxDistances;

    public ParticlesInfo(int size) {
        setSizes(size);
    }

    //deep copy!
    public ParticlesInfo(ParticlesInfo info) {
        this.colors = Arrays.stream(info.colors).map(Color::toString).map(Color::web).toArray(Color[]::new);
        this.attractions = deepCopy(info.attractions);
        this.minDistances = deepCopy(info.minDistances);
        this.maxDistances = deepCopy(info.maxDistances);

    }

    private double[][] deepCopy(double[][] oldArray) {
        double[][] newArray = new double[oldArray.length][oldArray[0].length];
        for (int i = 0; i < newArray.length; i++) {
            newArray[i] = Arrays.copyOf(oldArray[i], oldArray[i].length);
        }
        return newArray;
    }

    public void setSizes(int size) {
        colors = new Color[size];
        Arrays.fill(colors, Color.WHITE);
        attractions = new double[size][size];
        minDistances = new double[size][size];
        maxDistances = new double[size][size];
    }

    public int size() {
        return colors.length;
    }

    public void setColor(int type, Color color) {
        colors[type] = color;
    }

    public Color getColor(int type) {
        return colors[type];
    }

    public void setAttraction(int type, int otherType, double attraction) {
        attractions[type][otherType] = attraction;
    }

    public double getAttraction(int type, int otherType) {
        return attractions[type][otherType];
    }

    public void setMinDistance(int type, int otherType, double minDistance) {
        minDistances[type][otherType] = minDistance;
    }

    public double getMinDistance(int type, int otherType) {
        return minDistances[type][otherType];
    }

    public void setMaxDistance(int type, int otherType, double maxDistance) {
        maxDistances[type][otherType] = maxDistance;
    }

    public double getMaxDistance(int type, int otherType) {
        return maxDistances[type][otherType];
    }

}
