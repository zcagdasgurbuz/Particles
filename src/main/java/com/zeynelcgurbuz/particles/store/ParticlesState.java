package com.zeynelcgurbuz.particles.store;

import com.zeynelcgurbuz.particles.ParticlesInfo;
import com.zeynelcgurbuz.particles.Vector;

import java.io.Serializable;

public class ParticlesState implements Serializable {
    //private final List<String> items;
    private Vector mouseDragPosition;
    private Vector forceFieldPosition;


    private int particleCount = 400;
    private int colorCount = 5;


    private int inRangeStyle = 4;
    private int belowRangeStyle = 5;
    private int outRangeStyle = 21;


    private double minRLower = 10;
    private double minRUpper = 20;
    private double minRMean = 0.0;
    private double minRStd = 0.0;
    private boolean minRStandard = false;

    private double attractionMin = -1.0;
    private double attractionMax = 1.0;
    private double attractionMean = 0.5;
    private double attractionStd = 15.0;
    private boolean attractionStandard = true;
    private boolean negateSelfAttraction = false;

    private double maxRLower = 30.0;
    private double maxRUpper = 70.0;
    private double maxRMean = 0.0;
    private double maxRStd = 0.0;
    private boolean maxRStandard = false;

    private double friction = 0.25;
    private double g = 0.1; //gravity constant

    private boolean molAttract = true;
    private boolean gravAttract = false;
    private boolean wallsActive = true;

    private ParticlesInfo info;


    public ParticlesState(Vector mouseDragPosition) {
        this.mouseDragPosition = mouseDragPosition;
        info = new ParticlesInfo(colorCount);
    }

    public ParticlesState(Vector mouseDragPosition, Vector forceFieldPosition, int particleCount, int colorCount,
                          int inRangeStyle, int belowRangeStyle, int outRangeStyle, double minRLower, double minRUpper,
                          double minRMean, double minRStd, boolean minRStandard, double maxRLower, double maxRUpper,
                          double maxRMean, double maxRStd, boolean maxRStandard, double attractionMin, double attractionMax,
                          double attractionMean, double attractionStd, boolean attractionStandard, boolean negateSelfAttraction,
                          double friction, double g, boolean molAttract, boolean gravAttract, boolean wallsActive,
                          ParticlesInfo info) {
        this.mouseDragPosition = mouseDragPosition;
        this.forceFieldPosition = forceFieldPosition;
        this.particleCount = particleCount;
        this.colorCount = colorCount;
        this.inRangeStyle = inRangeStyle;
        this.belowRangeStyle = belowRangeStyle;
        this.outRangeStyle = outRangeStyle;
        this.minRLower = minRLower;
        this.minRUpper = minRUpper;
        this.minRMean = minRMean;
        this.minRStd = minRStd;
        this.minRStandard = minRStandard;
        this.maxRLower = maxRLower;
        this.maxRUpper = maxRUpper;
        this.maxRMean = maxRMean;
        this.maxRStd = maxRStd;
        this.maxRStandard = maxRStandard;
        this.attractionMin = attractionMin;
        this.attractionMax = attractionMax;
        this.attractionMean = attractionMean;
        this.attractionStd = attractionStd;
        this.attractionStandard = attractionStandard;
        this.negateSelfAttraction = negateSelfAttraction;
        this.friction = friction;
        this.g = g;
        this.molAttract = molAttract;
        this.gravAttract = gravAttract;
        this.wallsActive = wallsActive;
        this.info = info;
    }

/*    public ParticlesState fromState(ParticlesState state) {
        if (items.equals(this.items)) {
            return this;
        } else {
            return new TodoState(items);
        }
    }*/

    public ParticlesState clone() {
        return new ParticlesState(new Vector(mouseDragPosition), new Vector(forceFieldPosition),
                particleCount, colorCount, inRangeStyle, belowRangeStyle, outRangeStyle, minRLower, minRUpper,
                minRMean, minRStd, minRStandard, maxRLower, maxRUpper, maxRMean, maxRStd, maxRStandard, attractionMin,
                attractionMax, attractionMean, attractionStd, attractionStandard, negateSelfAttraction, friction,
                g, molAttract, gravAttract, wallsActive, new ParticlesInfo(info));
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!this.getClass().getName().equals(other.getClass().getName())) {
            return false;
        }
        ParticlesState otherState = (ParticlesState) other;
        return mouseDragPosition.equals(otherState.mouseDragPosition);
    }


/*    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("TodoState (items=" + items.size() + ")[\n");

        items.forEach(item -> builder.append("\t").append(item).append("\n"));

        builder.append("]");
        return builder.toString();
    }*/

    public Vector getMouseDragPosition() {
        return mouseDragPosition;
    }

    public ParticlesState setMouseDragPosition(Vector mouseDragPosition) {
        this.mouseDragPosition = mouseDragPosition;
        return this;
    }

    public int getParticleCount() {
        return particleCount;
    }

    public ParticlesState setParticleCount(int particleCount) {
        this.particleCount = particleCount;
        return this;
    }

    public int getColorCount() {
        return colorCount;
    }

    public ParticlesState setColorCount(int colorCount) {
        this.colorCount = colorCount;
        return this;
    }

    public int getInRangeStyle() {
        return inRangeStyle;
    }

    public ParticlesState setInRangeStyle(int inRangeStyle) {
        this.inRangeStyle = inRangeStyle;
        return this;
    }

    public int getBelowRangeStyle() {
        return belowRangeStyle;
    }

    public ParticlesState setBelowRangeStyle(int belowRangeStyle) {
        this.belowRangeStyle = belowRangeStyle;
        return this;
    }

    public int getOutRangeStyle() {
        return outRangeStyle;
    }

    public ParticlesState setOutRangeStyle(int outRangeStyle) {
        this.outRangeStyle = outRangeStyle;
        return this;
    }

    public double getMinRLower() {
        return minRLower;
    }

    public ParticlesState setMinRLower(double minRLower) {
        this.minRLower = minRLower;
        return this;
    }

    public double getMinRUpper() {
        return minRUpper;
    }

    public ParticlesState setMinRUpper(double minRUpper) {
        this.minRUpper = minRUpper;
        return this;
    }

    public double getMinRMean() {
        return minRMean;
    }

    public ParticlesState setMinRMean(double minRMean) {
        this.minRMean = minRMean;
        return this;
    }

    public double getMinRStd() {
        return minRStd;
    }

    public ParticlesState setMinRStd(double minRStd) {
        this.minRStd = minRStd;
        return this;
    }

    public boolean isMinRStandard() {
        return minRStandard;
    }

    public ParticlesState setMinRStandard(boolean minRStandard) {
        this.minRStandard = minRStandard;
        return this;
    }

    public double getAttractionMin() {
        return attractionMin;
    }

    public ParticlesState setAttractionMin(double attractionMin) {
        this.attractionMin = attractionMin;
        return this;
    }

    public double getAttractionMax() {
        return attractionMax;
    }

    public ParticlesState setAttractionMax(double attractionMax) {
        this.attractionMax = attractionMax;
        return this;
    }

    public double getAttractionMean() {
        return attractionMean;
    }

    public ParticlesState setAttractionMean(double attractionMean) {
        this.attractionMean = attractionMean;
        return this;
    }

    public double getAttractionStd() {
        return attractionStd;
    }

    public ParticlesState setAttractionStd(double attractionStd) {
        this.attractionStd = attractionStd;
        return this;
    }

    public boolean isAttractionStandard() {
        return attractionStandard;
    }

    public ParticlesState setAttractionStandard(boolean attractionStandard) {
        this.attractionStandard = attractionStandard;
        return this;
    }

    public boolean isNegateSelfAttraction() {
        return negateSelfAttraction;
    }

    public ParticlesState setNegateSelfAttraction(boolean negateSelfAttraction) {
        this.negateSelfAttraction = negateSelfAttraction;
        return this;
    }

    public double getMaxRLower() {
        return maxRLower;
    }

    public ParticlesState setMaxRLower(double maxRLower) {
        this.maxRLower = maxRLower;
        return this;
    }

    public double getMaxRUpper() {
        return maxRUpper;
    }

    public ParticlesState setMaxRUpper(double maxRUpper) {
        this.maxRUpper = maxRUpper;
        return this;
    }

    public double getMaxRMean() {
        return maxRMean;
    }

    public ParticlesState setMaxRMean(double maxRMean) {
        this.maxRMean = maxRMean;
        return this;
    }

    public double getMaxRStd() {
        return maxRStd;
    }

    public ParticlesState setMaxRStd(double maxRStd) {
        this.maxRStd = maxRStd;
        return this;
    }

    public boolean isMaxRStandard() {
        return maxRStandard;
    }

    public ParticlesState setMaxRStandard(boolean maxRStandard) {
        this.maxRStandard = maxRStandard;
        return this;
    }

    public double getFriction() {
        return friction;
    }

    public ParticlesState setFriction(double friction) {
        this.friction = friction;
        return this;
    }

    public double getG() {
        return g;
    }

    public ParticlesState setG(double g) {
        this.g = g;
        return this;
    }

    public boolean isMolAttract() {
        return molAttract;
    }

    public ParticlesState setMolAttract(boolean molAttract) {
        this.molAttract = molAttract;
        return this;
    }

    public boolean isGravAttract() {
        return gravAttract;
    }

    public ParticlesState setGravAttract(boolean gravAttract) {
        this.gravAttract = gravAttract;
        return this;
    }

    public boolean isWallsActive() {
        return wallsActive;
    }

    public ParticlesState setWallsActive(boolean wallsActive) {
        this.wallsActive = wallsActive;
        return this;
    }

    public ParticlesInfo getInfo() {
        return info;
    }

    public void setInfo(ParticlesInfo info) {
        this.info = info;
    }
}
