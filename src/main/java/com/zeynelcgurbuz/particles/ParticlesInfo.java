package com.zeynelcgurbuz.particles;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ParticlesInfo {

    private Color[] colors;
    private double[][] attractions;
    private double[][] minDistances;
    private double[][] maxDistances;

    public void setSizes(int size){

        colors = new Color[size];
        Arrays.fill(colors, Color.WHITE);
        attractions = new double[size][size];
        minDistances = new double[size][size];
        maxDistances = new double[size][size];
    }

    public int size(){
        return colors.length;
    }

   /* public void addColor(Color color){
        colors.add(color);
    }*/

    public void setColor(int type, Color color){
        colors[type]  = color;
    }

    public Color getColor(int type){
        return colors[type];
    }

    /*public void addAttraction(double attraction){
        attractions.add(attraction);
    }
*/
    public void setAttraction(int type, int otherType, double attraction){
        attractions[type][otherType] = attraction;
    }

    public double getAttraction(int type, int otherType){
        return attractions[type][otherType];
    }

/*    public void addMinDistance(double minDistance){
        minDistances.add(minDistance);
    }*/

    public void setMinDistance(int type, int otherType, double minDistance){
        minDistances[type][otherType] = minDistance;
    }
    
    public double getMinDistance(int type, int otherType){
        return  minDistances[type][otherType];
    }

   /* public void addMaxDistance(double maxDistance){
        maxDistances.add(maxDistance);
    }*/

    public void setMaxDistance(int type, int otherType, double maxDistance){
        maxDistances[type][otherType] = maxDistance;
    }

    public double getMaxDistance(int type, int otherType){
        return maxDistances[type][otherType];
    }
}
