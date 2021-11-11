package com.zeynelcgurbuz.particles;

import java.io.Serializable;

public class Vector implements Serializable {

    /** The Vector's x component. */
    public double x;
    /** The Vector's y component. */
    public double y;

    /** Construct a null Vector, <0, 0> by default. */
    public Vector() {
    }

    /**
     * Construct a new Vector from two doubles.
     *
     * @param x the x component
     * @param y the y component
     */
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Construct a new Vector from two integers.
     *
     * @param x the x component
     * @param y the y component
     */
    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Construct a new Vector from an exising one.
     *
     * @param v the source Vector
     */

    public Vector(Vector v) {
        this.x = v.x;
        this.y = v.y;
    }

    /**
     * Set the components of this Vector.
     *
     * @param x the x component
     * @param y the y component
     * @return this Vector.
     */
    public Vector set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    /**
     * Set the components of this Vector to those of v.
     *
     * @param v the source Vector
     * @return this Vector.
     */
    public Vector set(Vector v) {
        this.x = v.x;
        this.y = v.y;
        return this;
    }

    /**
     * Return the scalar norm (length) of this Vector.
     *
     * @return the norm of this Vector
     */
    public double norm() {
        return Math.hypot(x, y);
    }

    /**
     * Add the given Vector to this Vector; return this Vector.
     *
     * @param v the given Vector
     * @return the sum
     */
    public Vector add(Vector v) {
        this.x += v.x;
        this.y += v.y;
        return this;
    }

    /**
     * Subtract the given Vector from this Vector; return this Vector.
     *
     * @param v the given Vector
     * @return the difference
     */
    public Vector subtract(Vector v) {
        this.x -= v.x;
        this.y -= v.y;
        return this;
    }

    public static Vector subtract(Vector v1, Vector v2){
        return new Vector(v1.x - v2.x, v1.y - v2.y);
    }

    /**
     * Multiply the given Vector by this Vector; return the scalar product.
     *
     * @param v the given Vector
     * @return the scalar (dot) product
     */
    public double dot(Vector v) {
        return (this.x * v.x) + (this.y * v.y);
    }

    /**
     * Scale this Vector by the given scale factor.
     *
     * @param s the scale factor
     * @return the this Vector, scaled by s
     */
    public Vector scale(double s) {
        this.x *= s;
        this.y *= s;
        return this;
    }


    public Vector divide(double s) {
        this.x /= s;
        this.y /= s;
        return this;
    }

    /**
     * Scale this Vector by 1 / norm(); return this Vector.
     * The result is a unit Vector parallel to the original.
     * This is equivalent to this.scale(1 / this.norm()),
     * with a check for division by zero.
     *
     * @return the this Vector, scaled to unit length
     */
    public Vector unitVector() {
        double d = norm();
        if (d != 0) {
            this.x /= d;
            this.y /= d;
        }
        return this;
    }

    public void reset() {
        x = 0;
        y = 0;
    }

    public boolean equals(Object other){
        if(this == other){
            return true;
        } else if (other == null || getClass().getName().equals(other.getClass().getName())){
            return false;
        } else {
            Vector otherVector = (Vector) other;
            return x == otherVector.x && y == otherVector.y;
        }
    }

    /** Return this Vector's String representation. */
    public String toString() {
        return "<" + this.x + ", " + this.y + ">";
    }
}
