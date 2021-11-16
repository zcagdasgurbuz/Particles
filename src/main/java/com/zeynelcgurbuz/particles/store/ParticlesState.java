package com.zeynelcgurbuz.particles.store;

import com.zeynelcgurbuz.particles.ParticlesInfo;
import com.zeynelcgurbuz.particles.Vector;
import javafx.scene.canvas.GraphicsContext;

import java.io.Serializable;

public class ParticlesState implements Serializable {


    private double width;
    private double height;

    private Vector mouseDragPosition;
    private Vector forceFieldPosition;

    private int particleCount;
    private int colorCount;

    private double flatRadius;

    private int inRangeStyle;
    private int belowRangeStyle;
    private int outRangeStyle;

    private double minRLower;
    private double minRUpper;
    private double minRMean;
    private double minRStd;
    private boolean minRStandard;

    private double attractionMin;
    private double attractionMax;
    private double attractionMean;
    private double attractionStd;
    private boolean attractionStandard;
    private boolean negateSelfAttraction;

    private double maxRLower;
    private double maxRUpper;
    private double maxRMean;
    private double maxRStd;
    private boolean maxRStandard;

    private double friction;
    private double g;

    private boolean molAttract;
    private boolean gravAttract;
    private boolean wallsActive;

    private ParticlesInfo info;
    private transient GraphicsContext graphics;
    private String name;

    private boolean restartRequested;

    public ParticlesState(String name) {
        this.name = name;
    }

    public ParticlesState(double width, double height, Vector mouseDragPosition, Vector forceFieldPosition, int particleCount, int colorCount,
                          double flatRadius, int inRangeStyle, int belowRangeStyle, int outRangeStyle, double minRLower, double minRUpper,
                          double minRMean, double minRStd, boolean minRStandard, double maxRLower, double maxRUpper,
                          double maxRMean, double maxRStd, boolean maxRStandard, double attractionMin, double attractionMax,
                          double attractionMean, double attractionStd, boolean attractionStandard, boolean negateSelfAttraction,
                          double friction, double g, boolean molAttract, boolean gravAttract, boolean wallsActive,
                          ParticlesInfo info) {
        this.width = width;
        this.height = height;
        this.mouseDragPosition = mouseDragPosition;
        this.forceFieldPosition = forceFieldPosition;
        this.particleCount = particleCount;
        this.colorCount = colorCount;
        this.flatRadius = flatRadius;
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

    public ParticlesState copy() {
        ParticlesState copy = new ParticlesState(width, height, new Vector(mouseDragPosition), new Vector(forceFieldPosition),
                particleCount, colorCount, flatRadius, inRangeStyle, belowRangeStyle, outRangeStyle, minRLower, minRUpper,
                minRMean, minRStd, minRStandard, maxRLower, maxRUpper, maxRMean, maxRStd, maxRStandard, attractionMin,
                attractionMax, attractionMean, attractionStd, attractionStandard, negateSelfAttraction, friction,
                g, molAttract, gravAttract, wallsActive, info == null ? null : new ParticlesInfo(info));
        copy.setGraphics(graphics);
        copy.setName(name);
        return copy;
    }

    public ParticlesState shallowCopy() {
        ParticlesState copy = new ParticlesState(width, height, mouseDragPosition, forceFieldPosition,
                particleCount, colorCount, flatRadius, inRangeStyle, belowRangeStyle, outRangeStyle, minRLower, minRUpper,
                minRMean, minRStd, minRStandard, maxRLower, maxRUpper, maxRMean, maxRStd, maxRStandard, attractionMin,
                attractionMax, attractionMean, attractionStd, attractionStandard, negateSelfAttraction, friction,
                g, molAttract, gravAttract, wallsActive, info);
        copy.setGraphics(graphics);
        copy.setName(name);
        return copy;
    }


    @Override
    public boolean equals(Object other) {
        return ((other == this) || (other != null && other.getClass().getName().equals(this.getClass().getName()) &&
                name.equals(((ParticlesState) other).name)));
    }


    public Vector getMouseDragPosition() {
        return mouseDragPosition;
    }

    public ParticlesState setMouseDragPosition(Vector mouseDragPosition) {
        this.mouseDragPosition = mouseDragPosition;
        return this;
    }

    public double getWidth() {
        return width;
    }

    public ParticlesState setWidth(double width) {
        this.width = width;
        return this;
    }

    public double getHeight() {
        return height;
    }

    public ParticlesState setHeight(double height) {
        this.height = height;
        return this;
    }

    public double getFlatRadius() {
        return flatRadius;
    }

    public ParticlesState setFlatRadius(double flatRadius) {
        this.flatRadius = flatRadius;
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

    public GraphicsContext getGraphics() {
        return graphics;
    }

    public ParticlesState setGraphics(GraphicsContext graphics) {
        this.graphics = graphics;
        return this;
    }

    public boolean isRestartRequested() {
        return restartRequested;
    }

    public ParticlesState setRestartRequested(boolean restartRequested) {
        this.restartRequested = restartRequested;
        return this;
    }


    public ParticlesState setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return name;
    }

}
