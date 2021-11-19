package com.zeynelcgurbuz.particles;

import java.io.Serializable;

/**
 * Vector, class
 */
public class Vector implements Serializable {

    /** The x component of the vector */
    public double x;
    /** The y component of the vector. */
    public double y;

    /**
     * Empty constructor, x=0 and y =0
     * */
    public Vector() {
    }

    /**
     * Constructs a new Vector from doubles.
     *
     * @param x the x component
     * @param y the y component
     */
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }


    /**
     * Construct a new Vector by copying existing vector
     *
     * @param v the Vector to be copied
     */
    public Vector(Vector v) {
        this.x = v.x;
        this.y = v.y;
    }

    /**
     * Return the length of the vector.
     *
     * @return the length of this Vector
     */
    public double length() {
        return Math.hypot(x, y);
    }

    /**
     * Adds the given Vector to this Vector and return this Vector.
     *
     * @param v the given Vector
     * @return this vector after addition
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

    /**
     * Subtracts vector2 from vector1.
     *
     * @param v1 the vector 1
     * @param v2 the vector 2
     * @return the resulting vector
     */
    public static Vector subtract(Vector v1, Vector v2){
        return new Vector(v1.x - v2.x, v1.y - v2.y);
    }

    /**
     * Returns dot product of this vector and given vector.
     *
     * @param v the given Vector
     * @return the dot product
     */
    public double dot(Vector v) {
        return (this.x * v.x) + (this.y * v.y);
    }

    /**
     * Scales this Vector by the given scale factor.
     *
     * @param s the scale factor
     * @return this Vector after scaled by s
     */
    public Vector scale(double s) {
        this.x *= s;
        this.y *= s;
        return this;
    }

    /**
     * Converts this vector to unit vector then returns this vector.
     *
     * @return this vector after converted to unit vector
     */
    public Vector unitVector() {
        double d = length();
        if (d != 0) {
            this.x /= d;
            this.y /= d;
        }
        return this;
    }
}
