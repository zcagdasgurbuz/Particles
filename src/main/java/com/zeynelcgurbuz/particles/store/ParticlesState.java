package com.zeynelcgurbuz.particles.store;

import com.zeynelcgurbuz.particles.ParticlesInfo;
import com.zeynelcgurbuz.particles.Vector;
import javafx.scene.canvas.GraphicsContext;

import java.io.Serializable;

/**
 * Particles state, data structure that hold all the information about particles
 */
public class ParticlesState implements Serializable {

    /**
     * The width of the visible area.
     */
    private double width;
    /**
     * The height of the visible area.
     */
    private double height;

    /**
     * The mouse drag position.
     */
    private Vector mouseDragPosition;
    /**
     * The Force field position, *not utilized yet*
     */
    private Vector forceFieldPosition;

    /**
     * The number of particles.
     */
    private int particleCount;
    /**
     * The number of types/colors.
     */
    private int colorCount;

    /**
     * The radius of the particles.
     */
    private double flatRadius;

    /**
     * In range style attraction style.
     */
    private int inRangeStyle;
    /**
     * Below range attraction style.
     */
    private int belowRangeStyle;
    /**
     * Out range attraction style
     */
    private int outRangeStyle;

    /**
     * The minimum of minimum attraction range, is used to generate random minimum range value
     */
    private double minRLower;
    /**
     * The maximum of minimum attraction range, is used to generate random minimum range value
     */
    private double minRUpper;
    /**
     * The mean of minimum attraction range, is used to generate random minimum range value
     */
    private double minRMean;
    /**
     * The standard dev. of minimum attraction range, is used to generate random minimum range value
     */
    private double minRStd;
    /**
     * Whether the random minimum attraction range is calculated based on standard distribution
     */
    private boolean minRStandard;
    /**
     * The minimum of the attraction value, is used to generate random attraction value.
     */
    private double attractionMin;
    /**
     * The maximum of the attraction value, is used to generate random attraction value.
     */
    private double attractionMax;
    /**
     * The mean of the attraction value, is used to generate random attraction value.
     */
    private double attractionMean;
    /**
     * The standard dev of the attraction value, is used to generate random attraction value.
     */
    private double attractionStd;
    /**
     * Whether the random attraction value is calculated based on standard distribution.
     */
    private boolean attractionStandard;
    /**
     * Whether the self attraction is always negative.
     */
    private boolean negateSelfAttraction;

    /**
     * The minimum of maximum attraction range, is used to generate random maximum range value
     */
    private double maxRLower;
    /**
     * The maximum of maximum attraction range, is used to generate random maximum range value
     */
    private double maxRUpper;
    /**
     * The mean of maximum attraction range, is used to generate random maximum range value
     */
    private double maxRMean;
    /**
     * The standard dev. of maximum attraction range, is used to generate random maximum range value
     */
    private double maxRStd;
    /**
     * Whether the random maximum attraction range is calculated based on standard distribution
     */
    private boolean maxRStandard;

    /**
     * The friction value
     */
    private double friction;
    /**
     * The g factor, is used to scale gravity
     */
    private double g;

    /**
     * Whether the molecular attraction is active
     */
    private boolean molAttract;
    /**
     * Whether the gravitational attraction is active
     */
    private boolean gravAttract;
    /**
     * Whether the walls are active
     */
    private boolean wallsActive;

    /**
     * The particles' info that stores the type info.
     */
    private ParticlesInfo info;
    /**
     * The graphics context, is used to draw particles.
     */
    private transient GraphicsContext graphics;

    /**
     * The name of the state, is used for save/load functionality
     */
    private String name;

    /**
     * whether restart is requested.
     */
    private boolean restartRequested;

    /**
     * Constructor
     *
     * @param name the name of the state
     */
    public ParticlesState(String name) {
        this.name = name;
    }

    /**
     * Instantiates a new Particles state.
     *
     * @param width                The width of the visible area.
     * @param height               The height of the visible area.
     * @param mouseDragPosition    The mouse drag position
     * @param forceFieldPosition   The force field position, *not utilized yet*
     * @param particleCount        The number of particles
     * @param colorCount           The number of types/colors
     * @param flatRadius           The radius of the particles
     * @param inRangeStyle         In range style attraction style
     * @param belowRangeStyle      Below range attraction style
     * @param outRangeStyle        Out range attraction style
     * @param minRLower            The minimum of minimum attraction range, is used to generate random minimum range value
     * @param minRUpper            The maximum of minimum attraction range, is used to generate random minimum range value
     * @param minRMean             The mean of minimum attraction range, is used to generate random minimum range value
     * @param minRStd              The standard dev. of minimum attraction range, is used to generate random minimum range value
     * @param minRStandard         Whether the random minimum attraction range is calculated based on standard distribution
     * @param maxRLower            The minimum of maximum attraction range, is used to generate random maximum range value
     * @param maxRUpper            The maximum of maximum attraction range, is used to generate random maximum range value
     * @param maxRMean             The mean of maximum attraction range, is used to generate random maximum range value
     * @param maxRStd              The standard dev. of maximum attraction range, is used to generate random maximum range value
     * @param maxRStandard         Whether the random maximum attraction range is calculated based on standard distribution
     * @param attractionMin        The minimum of the attraction value, is used to generate random attraction value.
     * @param attractionMax        The maximum of the attraction value, is used to generate random attraction value.
     * @param attractionMean       The mean of the attraction value, is used to generate random attraction value.
     * @param attractionStd        The standard dev of the attraction value, is used to generate random attraction value.
     * @param attractionStandard   Whether the random attraction value is calculated based on standard distribution.
     * @param negateSelfAttraction Whether the self attraction is always negative.
     * @param friction             The friction value
     * @param g                    The g factor, is used to scale gravity
     * @param molAttract           Whether the molecular attraction is active
     * @param gravAttract          Whether the gravitational attraction is active
     * @param wallsActive          Whether the walls are active
     * @param info                 The particles' info that stores the type info.
     */
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

    /**
     * Deepish Copy particles state.
     *
     * @return the particles state
     */
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

    /**
     * Shallowish copy particles state.
     *
     * @return the particles state
     */
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

//********************************************************//
//************* THE REST AUTO GENERATED JAVADOC***********//
//********************************************************//

    /**
     * Gets mouse drag position.
     *
     * @return the mouse drag position
     */
    public Vector getMouseDragPosition() {
        return mouseDragPosition;
    }

    /**
     * Sets mouse drag position.
     *
     * @param mouseDragPosition the mouse drag position
     * @return the mouse drag position
     */
    public ParticlesState setMouseDragPosition(Vector mouseDragPosition) {
        this.mouseDragPosition = mouseDragPosition;
        return this;
    }

    /**
     * Gets width.
     *
     * @return the width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Sets width.
     *
     * @param width the width
     * @return the width
     */
    public ParticlesState setWidth(double width) {
        this.width = width;
        return this;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets height.
     *
     * @param height the height
     * @return the height
     */
    public ParticlesState setHeight(double height) {
        this.height = height;
        return this;
    }

    /**
     * Gets flat radius.
     *
     * @return the flat radius
     */
    public double getFlatRadius() {
        return flatRadius;
    }

    /**
     * Sets flat radius.
     *
     * @param flatRadius the flat radius
     * @return the flat radius
     */
    public ParticlesState setFlatRadius(double flatRadius) {
        this.flatRadius = flatRadius;
        return this;
    }

    /**
     * Gets particle count.
     *
     * @return the particle count
     */
    public int getParticleCount() {
        return particleCount;
    }

    /**
     * Sets particle count.
     *
     * @param particleCount the particle count
     * @return the particle count
     */
    public ParticlesState setParticleCount(int particleCount) {
        this.particleCount = particleCount;
        return this;
    }

    /**
     * Gets color count.
     *
     * @return the color count
     */
    public int getColorCount() {
        return colorCount;
    }

    /**
     * Sets color count.
     *
     * @param colorCount the color count
     * @return the color count
     */
    public ParticlesState setColorCount(int colorCount) {
        this.colorCount = colorCount;
        return this;
    }

    /**
     * Gets in range style.
     *
     * @return the in range style
     */
    public int getInRangeStyle() {
        return inRangeStyle;
    }

    /**
     * Sets in range style.
     *
     * @param inRangeStyle the in range style
     * @return the in range style
     */
    public ParticlesState setInRangeStyle(int inRangeStyle) {
        this.inRangeStyle = inRangeStyle;
        return this;
    }

    /**
     * Gets below range style.
     *
     * @return the below range style
     */
    public int getBelowRangeStyle() {
        return belowRangeStyle;
    }

    /**
     * Sets below range style.
     *
     * @param belowRangeStyle the below range style
     * @return the below range style
     */
    public ParticlesState setBelowRangeStyle(int belowRangeStyle) {
        this.belowRangeStyle = belowRangeStyle;
        return this;
    }

    /**
     * Gets out range style.
     *
     * @return the out range style
     */
    public int getOutRangeStyle() {
        return outRangeStyle;
    }

    /**
     * Sets out range style.
     *
     * @param outRangeStyle the out range style
     * @return the out range style
     */
    public ParticlesState setOutRangeStyle(int outRangeStyle) {
        this.outRangeStyle = outRangeStyle;
        return this;
    }

    /**
     * Gets min r lower.
     *
     * @return the min r lower
     */
    public double getMinRLower() {
        return minRLower;
    }

    /**
     * Sets min r lower.
     *
     * @param minRLower the min r lower
     * @return the min r lower
     */
    public ParticlesState setMinRLower(double minRLower) {
        this.minRLower = minRLower;
        return this;
    }

    /**
     * Gets min r upper.
     *
     * @return the min r upper
     */
    public double getMinRUpper() {
        return minRUpper;
    }

    /**
     * Sets min r upper.
     *
     * @param minRUpper the min r upper
     * @return the min r upper
     */
    public ParticlesState setMinRUpper(double minRUpper) {
        this.minRUpper = minRUpper;
        return this;
    }

    /**
     * Gets min r mean.
     *
     * @return the min r mean
     */
    public double getMinRMean() {
        return minRMean;
    }

    /**
     * Sets min r mean.
     *
     * @param minRMean the min r mean
     * @return the min r mean
     */
    public ParticlesState setMinRMean(double minRMean) {
        this.minRMean = minRMean;
        return this;
    }

    /**
     * Gets min r std.
     *
     * @return the min r std
     */
    public double getMinRStd() {
        return minRStd;
    }

    /**
     * Sets min r std.
     *
     * @param minRStd the min r std
     * @return the min r std
     */
    public ParticlesState setMinRStd(double minRStd) {
        this.minRStd = minRStd;
        return this;
    }

    /**
     * Is min r standard boolean.
     *
     * @return the boolean
     */
    public boolean isMinRStandard() {
        return minRStandard;
    }

    /**
     * Sets min r standard.
     *
     * @param minRStandard the min r standard
     * @return the min r standard
     */
    public ParticlesState setMinRStandard(boolean minRStandard) {
        this.minRStandard = minRStandard;
        return this;
    }

    /**
     * Gets attraction min.
     *
     * @return the attraction min
     */
    public double getAttractionMin() {
        return attractionMin;
    }

    /**
     * Sets attraction min.
     *
     * @param attractionMin the attraction min
     * @return the attraction min
     */
    public ParticlesState setAttractionMin(double attractionMin) {
        this.attractionMin = attractionMin;
        return this;
    }

    /**
     * Gets attraction max.
     *
     * @return the attraction max
     */
    public double getAttractionMax() {
        return attractionMax;
    }

    /**
     * Sets attraction max.
     *
     * @param attractionMax the attraction max
     * @return the attraction max
     */
    public ParticlesState setAttractionMax(double attractionMax) {
        this.attractionMax = attractionMax;
        return this;
    }

    /**
     * Gets attraction mean.
     *
     * @return the attraction mean
     */
    public double getAttractionMean() {
        return attractionMean;
    }

    /**
     * Sets attraction mean.
     *
     * @param attractionMean the attraction mean
     * @return the attraction mean
     */
    public ParticlesState setAttractionMean(double attractionMean) {
        this.attractionMean = attractionMean;
        return this;
    }

    /**
     * Gets attraction std.
     *
     * @return the attraction std
     */
    public double getAttractionStd() {
        return attractionStd;
    }

    /**
     * Sets attraction std.
     *
     * @param attractionStd the attraction std
     * @return the attraction std
     */
    public ParticlesState setAttractionStd(double attractionStd) {
        this.attractionStd = attractionStd;
        return this;
    }

    /**
     * Is attraction standard boolean.
     *
     * @return the boolean
     */
    public boolean isAttractionStandard() {
        return attractionStandard;
    }

    /**
     * Sets attraction standard.
     *
     * @param attractionStandard the attraction standard
     * @return the attraction standard
     */
    public ParticlesState setAttractionStandard(boolean attractionStandard) {
        this.attractionStandard = attractionStandard;
        return this;
    }

    /**
     * Is negate self attraction boolean.
     *
     * @return the boolean
     */
    public boolean isNegateSelfAttraction() {
        return negateSelfAttraction;
    }

    /**
     * Sets negate self attraction.
     *
     * @param negateSelfAttraction the negate self attraction
     * @return the negate self attraction
     */
    public ParticlesState setNegateSelfAttraction(boolean negateSelfAttraction) {
        this.negateSelfAttraction = negateSelfAttraction;
        return this;
    }

    /**
     * Gets max r lower.
     *
     * @return the max r lower
     */
    public double getMaxRLower() {
        return maxRLower;
    }

    /**
     * Sets max r lower.
     *
     * @param maxRLower the max r lower
     * @return the max r lower
     */
    public ParticlesState setMaxRLower(double maxRLower) {
        this.maxRLower = maxRLower;
        return this;
    }

    /**
     * Gets max r upper.
     *
     * @return the max r upper
     */
    public double getMaxRUpper() {
        return maxRUpper;
    }

    /**
     * Sets max r upper.
     *
     * @param maxRUpper the max r upper
     * @return the max r upper
     */
    public ParticlesState setMaxRUpper(double maxRUpper) {
        this.maxRUpper = maxRUpper;
        return this;
    }

    /**
     * Gets max r mean.
     *
     * @return the max r mean
     */
    public double getMaxRMean() {
        return maxRMean;
    }

    /**
     * Sets max r mean.
     *
     * @param maxRMean the max r mean
     * @return the max r mean
     */
    public ParticlesState setMaxRMean(double maxRMean) {
        this.maxRMean = maxRMean;
        return this;
    }

    /**
     * Gets max r std.
     *
     * @return the max r std
     */
    public double getMaxRStd() {
        return maxRStd;
    }

    /**
     * Sets max r std.
     *
     * @param maxRStd the max r std
     * @return the max r std
     */
    public ParticlesState setMaxRStd(double maxRStd) {
        this.maxRStd = maxRStd;
        return this;
    }

    /**
     * Is max r standard boolean.
     *
     * @return the boolean
     */
    public boolean isMaxRStandard() {
        return maxRStandard;
    }

    /**
     * Sets max r standard.
     *
     * @param maxRStandard the max r standard
     * @return the max r standard
     */
    public ParticlesState setMaxRStandard(boolean maxRStandard) {
        this.maxRStandard = maxRStandard;
        return this;
    }

    /**
     * Gets friction.
     *
     * @return the friction
     */
    public double getFriction() {
        return friction;
    }

    /**
     * Sets friction.
     *
     * @param friction the friction
     * @return the friction
     */
    public ParticlesState setFriction(double friction) {
        this.friction = friction;
        return this;
    }

    /**
     * Gets g.
     *
     * @return the g
     */
    public double getG() {
        return g;
    }

    /**
     * Sets g.
     *
     * @param g the g
     * @return the g
     */
    public ParticlesState setG(double g) {
        this.g = g;
        return this;
    }

    /**
     * Is mol attract boolean.
     *
     * @return the boolean
     */
    public boolean isMolAttract() {
        return molAttract;
    }

    /**
     * Sets mol attract.
     *
     * @param molAttract the mol attract
     * @return the mol attract
     */
    public ParticlesState setMolAttract(boolean molAttract) {
        this.molAttract = molAttract;
        return this;
    }

    /**
     * Is grav attract boolean.
     *
     * @return the boolean
     */
    public boolean isGravAttract() {
        return gravAttract;
    }

    /**
     * Sets grav attract.
     *
     * @param gravAttract the grav attract
     * @return the grav attract
     */
    public ParticlesState setGravAttract(boolean gravAttract) {
        this.gravAttract = gravAttract;
        return this;
    }

    /**
     * Is walls active boolean.
     *
     * @return the boolean
     */
    public boolean isWallsActive() {
        return wallsActive;
    }

    /**
     * Sets walls active.
     *
     * @param wallsActive the walls active
     * @return the walls active
     */
    public ParticlesState setWallsActive(boolean wallsActive) {
        this.wallsActive = wallsActive;
        return this;
    }

    /**
     * Gets info.
     *
     * @return the info
     */
    public ParticlesInfo getInfo() {
        return info;
    }

    /**
     * Sets info.
     *
     * @param info the info
     */
    public void setInfo(ParticlesInfo info) {
        this.info = info;
    }

    /**
     * Gets graphics.
     *
     * @return the graphics
     */
    public GraphicsContext getGraphics() {
        return graphics;
    }

    /**
     * Sets graphics.
     *
     * @param graphics the graphics
     * @return the graphics
     */
    public ParticlesState setGraphics(GraphicsContext graphics) {
        this.graphics = graphics;
        return this;
    }

    /**
     * Is restart requested boolean.
     *
     * @return the boolean
     */
    public boolean isRestartRequested() {
        return restartRequested;
    }

    /**
     * Sets restart requested.
     *
     * @param restartRequested the restart requested
     * @return the restart requested
     */
    public ParticlesState setRestartRequested(boolean restartRequested) {
        this.restartRequested = restartRequested;
        return this;
    }


    /**
     * Sets name.
     *
     * @param name the name
     * @return the name
     */
    public ParticlesState setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return name;
    }

}
