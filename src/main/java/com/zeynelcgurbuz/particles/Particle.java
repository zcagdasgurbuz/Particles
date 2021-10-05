package com.zeynelcgurbuz.particles;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Particle {



    private double x;
    private double y;
    private double radius;
    private double mass;
    private double attractionConstant;
    private double velocityX;
    private double velocityY;
    private Color color;
    private ParticleType type;

    public Particle(double x, double y, double radius, double mass, double attractionConstant, double velocityX,
                    double velocityY, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.mass = mass;
        this.attractionConstant = attractionConstant;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.color = color;
    }

    Particle() {

    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setPosition(Vector positionVector) {
        this.x = positionVector.x;
        this.y = positionVector.y;
    }

    public void setRadius(double radius) {
        this.radius = radius;
        this.mass = radius * radius * radius;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setVelocity(double x, double y) {
        velocityX = x;
        velocityY = y;
    }

    public void setVelocity(Vector velocityVector) {
        velocityX = velocityVector.x;
        velocityY = velocityVector.y;
    }

    /** Return the given Vector set to this particle's velocity. */
    public Vector getVelocity(Vector v) {
        v.x = this.velocityX;
        v.y = this.velocityY;
        return v;
    }

    public Vector getVelocity() {
        return new Vector(velocityX, velocityY);
    }

    public Vector getPosition(Vector p) {
        p.x = x;
        p.y = y;
        return p;
    }

    public Vector getPosition() {
        return new Vector(x, y);
    }

    public double getMass() {
        return mass;
    }

    public double getRadius() {
        return radius;
    }

    public Paint getColor() {
        return color;
    }

    public void setType(ParticleType type){
        this.type = type;
    }

    public ParticleType getType(){
        return type;
    }

    public double getAttractionConstant() {
        return attractionConstant;
    }

    public void setAttractionConstant(double attractionConstant) {
        this.attractionConstant = attractionConstant;
    }
}
