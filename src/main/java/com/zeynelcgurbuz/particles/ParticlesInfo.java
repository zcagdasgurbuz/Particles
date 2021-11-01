package com.zeynelcgurbuz.particles;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collections;

public class ParticlesInfo {

    private ArrayList<Color> colors = new ArrayList<>();
    private ArrayList<ArrayList<Double>> attractions = new ArrayList<>();
    private ArrayList<ArrayList<Double>> minDistances = new ArrayList<>();
    private ArrayList<ArrayList<Double>> maxDistances = new ArrayList<>();

    public void setSizes(int size){
        colors = new ArrayList<>(Collections.nCopies(size, Color.WHITE));
        attractions = new ArrayList<>(Collections.nCopies(size, new ArrayList<>(Collections.nCopies(size,0.0))));
        minDistances = new ArrayList<>(Collections.nCopies(size, new ArrayList<>(Collections.nCopies(size,0.0))));
        maxDistances = new ArrayList<>(Collections.nCopies(size, new ArrayList<>(Collections.nCopies(size,0.0))));
    }

    public int size(){
        return colors.size();
    }

    public void addColor(Color color){
        colors.add(color);
    }

    public void setColor(int type, Color color){
        colors.set(type, color);
    }

    public Color getColor(int type){
        return colors.get(type);
    }

    /*public void addAttraction(double attraction){
        attractions.add(attraction);
    }
*/
    public void setAttraction(int type, int otherType, double attraction){
        attractions.get(type).set(otherType, attraction);
    }

    public double getAttraction(int type, int otherType){
        return attractions.get(type).get(otherType);
    }

/*    public void addMinDistance(double minDistance){
        minDistances.add(minDistance);
    }*/

    public void setMinDistance(int type, int otherType, double minDistance){
        minDistances.get(type).set(otherType, minDistance);
    }
    
    public double getMinDistance(int type, int otherType){
        return minDistances.get(type).get(otherType);
    }

   /* public void addMaxDistance(double maxDistance){
        maxDistances.add(maxDistance);
    }*/

    public void setMaxDistance(int type, int otherType, double maxDistance){
        maxDistances.get(type).set(otherType, maxDistance);
    }

    public double getMaxDistance(int type, int otherType){
        return maxDistances.get(type).get(otherType);
    }
}
