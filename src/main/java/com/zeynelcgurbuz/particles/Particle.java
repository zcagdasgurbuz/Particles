package com.zeynelcgurbuz.particles;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Particle class, stores info of a particle.
 */
public class Particle {

    /**
     * The position vector.
     */
    private Vector position;
    /**
     * The velocity vector.
     */
    private Vector velocity;
    /**
     * The radius of the particle.
     */
    private double radius;
    /**
     * The mass of the particle.
     */
    private double mass;
    /**
     * The color of the particle.
     */
    private Color color;
    /**
     * The type of the particle.
     */
    private int type;

    /**
     * Instantiates a new Particle.
     */
    public Particle(){
        position = new Vector();
        velocity = new Vector();
    }

    /**
     * Sets position.
     *
     * @param positionVector the position vector
     */
    public void setPosition(Vector positionVector) {
        this.position = positionVector;
    }

    /**
     * Sets radius.
     *
     * @param radius the radius
     */
    public void setRadius(double radius) {
        this.radius = radius;
        this.mass = radius * radius * radius;
    }

    /**
     * Sets color.
     *
     * @param color the color
     */
    public void setColor(Color color) {
        this.color = color;
    }


    /**
     * Sets velocity.
     *
     * @param velocityVector the velocity vector
     */
    public void setVelocity(Vector velocityVector) {
        this.velocity = velocityVector;
    }

    /**
     * Gets velocity of the particle.
     *
     * @return the velocity
     */
    public Vector getVelocity() {
        return velocity;
    }

    /**
     * Gets position of the particle.
     *
     * @return the position
     */
    public Vector getPosition() {
        return new Vector(position);
    }

    /**
     * Gets mass of the particle.
     *
     * @return the mass
     */
    public double getMass() {
        return mass;
    }

    /**
     * Gets radius of the particle.
     *
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Gets color of the particle.
     *
     * @return the color
     */
    public Paint getColor() {
        return color;
    }

    /**
     * Sets type of the particle.
     *
     * @param type the type
     */
    public void setType(int type){
        this.type = type;
    }

    /**
     * Get type of the particle.
     *
     * @return the int
     */
    public int getType(){
        return type;
    }
}
