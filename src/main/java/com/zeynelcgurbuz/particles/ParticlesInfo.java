package com.zeynelcgurbuz.particles;
import java.io.Serializable;
import java.util.Arrays;


/**
 * Particles info, stores info of all particle types.
 */
public class ParticlesInfo implements Serializable {

    /**
     * The colors of the types.
     */
    private String[] colors;
    /**
     * The attraction values of the types.
     */
    private double[][] attractions;
    /**
     * The minimum attraction distances of the types.
     */
    private double[][] minDistances;
    /**
     * The maximum attraction distances of the types.
     */
    private double[][] maxDistances;

    /**
     * Instantiates a new Particles info.
     *
     * @param size the size
     */
    public ParticlesInfo(int size) {
        setSizes(size);
    }

    /**
     * Instantiates a new Particles info.
     *
     * @param info the info
     */
    public ParticlesInfo(ParticlesInfo info) {
        this.colors = Arrays.copyOf(info.colors, info.colors.length);
        this.attractions = deepCopy(info.attractions);
        this.minDistances = deepCopy(info.minDistances);
        this.maxDistances = deepCopy(info.maxDistances);
    }

    /**
     * Helper, deep copy of 2D array.
     *
     * @param oldArray the old array
     * @return the deep clone
     */
    private double[][] deepCopy(double[][] oldArray) {
        double[][] newArray = new double[oldArray.length][oldArray[0].length];
        for (int i = 0; i < newArray.length; i++) {
            newArray[i] = Arrays.copyOf(oldArray[i], oldArray[i].length);
        }
        return newArray;
    }

    /**
     * Sets sizes, new arrays for particle types.
     *
     * @param size the size
     */
    public void setSizes(int size) {
        colors = new String[size];
        Arrays.fill(colors, "0xFFF");
        attractions = new double[size][size];
        minDistances = new double[size][size];
        maxDistances = new double[size][size];
    }

    /**
     * Gets the number of types.
     *
     * @return the int
     */
    public int size() {
        return colors.length;
    }

    /**
     * Sets color of a specific type.
     *
     * @param type  the type
     * @param color the color
     */
    public void setColor(int type, String color) {
        colors[type] = color;
    }

    /**
     * Gets color of a specific type.
     *
     * @param type the type
     * @return the color
     */
    public String getColor(int type) {
        return colors[type];
    }

    /**
     * Sets attraction of a specific type.
     *
     * @param type       the type
     * @param otherType  the other type
     * @param attraction the attraction
     */
    public void setAttraction(int type, int otherType, double attraction) {
        attractions[type][otherType] = attraction;
    }

    /**
     * Gets attraction of a specific type.
     *
     * @param type      the type
     * @param otherType the other type
     * @return the attraction
     */
    public double getAttraction(int type, int otherType) {
        return attractions[type][otherType];
    }

    /**
     * Sets min distance of a specific type
     *
     * @param type        the type
     * @param otherType   the other type
     * @param minDistance the min distance
     */
    public void setMinDistance(int type, int otherType, double minDistance) {
        minDistances[type][otherType] = minDistance;
    }

    /**
     * Gets min distance of a specific types.
     *
     * @param type      the type
     * @param otherType the other type
     * @return the min distance
     */
    public double getMinDistance(int type, int otherType) {
        return minDistances[type][otherType];
    }

    /**
     * Sets max distance of a specific types.
     *
     * @param type        the type
     * @param otherType   the other type
     * @param maxDistance the max distance
     */
    public void setMaxDistance(int type, int otherType, double maxDistance) {
        maxDistances[type][otherType] = maxDistance;
    }

    /**
     * Gets max distance of a specific types.
     *
     * @param type      the type
     * @param otherType the other type
     * @return the max distance
     */
    public double getMaxDistance(int type, int otherType) {
        return maxDistances[type][otherType];
    }

}
