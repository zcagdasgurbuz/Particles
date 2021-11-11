package com.zeynelcgurbuz.particles;

import com.zeynelcgurbuz.particles.animation.Animatable;
import com.zeynelcgurbuz.particles.animation.Animator;
import com.zeynelcgurbuz.particles.redux.Store;
import com.zeynelcgurbuz.particles.redux.Subscriber;
import com.zeynelcgurbuz.particles.redux.Subscription;
import com.zeynelcgurbuz.particles.store.ParticlesState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class Cosmos implements Animatable, Subscriber<ParticlesState> {

    private static final int MAX_ATOMS = 1000;
    private static final int MIN_RADIUS = 2;

    private static final Random random = new Random();
/*
    private final double minRMean;
    private final double minRStd;
    private final double maxRMean;
    private final double maxRStd;
    private final boolean minRStandard;
    private final boolean attractionStandard;
    private final boolean maxRStandard;
    private final boolean negateSelfAttraction;*/


    private int startX;
    private int startY;
    private int width;
    private int height;
    private Vector initialPositionVector = new Vector();

/*    private double attractionMean;
    private double attractionStd;
    private double attractionMax;
    private double attractionMin;
    private double minRLower;
    private double minRUpper;
    private double maxRLower;
    private double maxRUpper;
    private double friction;*/

    private final ArrayList<Particle> particles;
    private GraphicsContext graphics;

/*
    private int particleCount;
    private int colorCount;
    private ParticlesInfo info;
*/


    private Store<ParticlesState> store;
    private ParticlesState state;
    private boolean updated;

    private Subscription storeSubscription;

/*    private double g;
    private boolean molAttract;
    private boolean gravAttract;
    private boolean walls;
    private int inRangeStyle;
    private int belowRangeStyle;
    private int outRangeStyle;*/


    public Cosmos(Store<ParticlesState> store, int width, int height) {
        this.store = store;
        storeSubscription = store.subscribe(this);

        particles = new ArrayList<>();
        //**************************


        //**************************
        setBoundaries(0, 0, width, height);

        this.state = store.getState();
        state.getInfo().setSizes(state.getColorCount());


        setRandomTypes();
        //**************************
        setRandomParticles();
    }

    @Override
    public void update(double timePassedFromLastFrame) {

        for (int thisIdx = 0; thisIdx < particles.size(); thisIdx++) {
            Particle particle1 = particles.get(thisIdx);

            //apply attractions
            if (state.isMolAttract()) applyAttractionMolecular(thisIdx);
            if (state.isGravAttract()) applyAttraction(thisIdx, timePassedFromLastFrame);
            if (state.isWallsActive()) performWallCollisions(thisIdx);

            performParticleCollisions(thisIdx);

            //double time = timePassedFromLastFrame/1000000000.0;


            //update position
            particle1.setPosition(particle1.getPosition().add(particle1.getVelocity()));
            //friction
            particle1.setVelocity(particle1.getVelocity().scale(1.0 - state.getFriction()));

        }

        drawCosmos();
    }

    private void applyAttractionMolecular(int indexOfParticle) {

        Particle particle1 = particles.get(indexOfParticle);
        for (int idx = 0; idx < particles.size(); idx++) {
            Particle particle2 = particles.get(idx);
            double particle1MaxAttractionRadius = state.getInfo().getMaxDistance(particle1.getType(), particle2.getType());
            double particle1MinAttractionRadius = state.getInfo().getMinDistance(particle1.getType(), particle2.getType());
            double particle2MaxAttractionRadius = state.getInfo().getMaxDistance(particle2.getType(), particle1.getType());
            double particle2MinAttractionRadius = state.getInfo().getMinDistance(particle2.getType(), particle1.getType());
            Vector delta = Vector.subtract(particle2.getPosition(), particle1.getPosition());
            double distance = delta.norm();
            delta.unitVector();

            if (indexOfParticle != idx
                    && distance <= particle1MaxAttractionRadius
                    && distance <= particle2MaxAttractionRadius
                    && distance >= particle1MinAttractionRadius
                    && distance >= particle2MinAttractionRadius) {
                applyForceBasedOnStyle(delta, particle1, particle2, distance, state.getInRangeStyle());
            } else if (indexOfParticle != idx && distance < particle1MinAttractionRadius
                    && distance < particle2MinAttractionRadius) {
                applyForceBasedOnStyle(delta, particle1, particle2, distance, state.getBelowRangeStyle());
            } else {
                applyForceBasedOnStyle(delta, particle1, particle2, distance, state.getOutRangeStyle());
            }
        }
    }

    private void applyForceBasedOnStyle(Vector vector, Particle particle1, Particle particle2, double distance, int style) {
        double force1 = state.getInfo().getAttraction(particle1.getType(), particle2.getType());
        double force2 = state.getInfo().getAttraction(particle2.getType(), particle1.getType());

        switch (style) {
            case 1:
                vector.scale(force1 + force2);
                particle1.setVelocity(particle1.getVelocity().add(vector));
                break;
            case 2:
                vector.scale(force1 - force2);
                particle1.setVelocity(particle1.getVelocity().add(vector));
                break;
            case 3:
                vector.scale(force2 - force1);
                particle1.setVelocity(particle1.getVelocity().add(vector));
                break;
            case 4:
                vector.scale((force1 * force2) / (distance * distance));
                particle1.setVelocity(particle1.getVelocity().add(vector));
                break;
            case 5:
                vector.scale(-(force1 * force2) / (distance * distance));
                particle1.setVelocity(particle1.getVelocity().add(vector));
                break;
            case 6:
                vector.scale((force1 + force2) / distance * distance);
                particle1.setVelocity(particle1.getVelocity().add(vector));
                break;
            case 7:
                vector.scale((force1 - force2) / (distance * distance));
                particle1.setVelocity(particle1.getVelocity().add(vector));
                break;
            case 8:
                vector.scale((force2 - force1) / (distance * distance));
                particle1.setVelocity(particle1.getVelocity().add(vector));
                break;
            case 9:
                vector.scale(1 / (distance * distance));
                particle1.setVelocity(particle1.getVelocity().add(vector));
                break;
            case 10:
                vector.scale(-1 / (distance * distance));
                particle1.setVelocity(particle1.getVelocity().add(vector));
                break;
            case 11:
                vector.scale(force1);
                particle1.setVelocity(particle1.getVelocity().add(vector));
                break;
            case 12:
                vector.scale(force2);
                particle1.setVelocity(particle1.getVelocity().add(vector));
                break;
            case 13:
                vector.scale(-force1);
                particle1.setVelocity(particle1.getVelocity().add(vector));
                break;
            case 14:
                vector.scale(-force2);
                particle1.setVelocity(particle1.getVelocity().add(vector));
                break;
            case 15:
                vector.scale(force1 / distance * distance);
                particle1.setVelocity(particle1.getVelocity().add(vector));
                break;
            case 16:
                vector.scale(force2 / (distance * distance));
                particle1.setVelocity(particle1.getVelocity().add(vector));
                break;
            case 17:
                vector.scale(-force1 / distance * distance);
                particle1.setVelocity(particle1.getVelocity().add(vector));
                break;
            case 18:
                vector.scale(-force2 / (distance * distance));
                particle1.setVelocity(particle1.getVelocity().add(vector));
                break;
            default:
                //
        }
    }


    //
    //https://www.vobarian.com/collisions/2dcollisions2.pdf
    private boolean performParticleCollisions(int indexOfParticle) {
        for (int otherIdx = 0; otherIdx < particles.size(); otherIdx++) {
            Particle particle1 = particles.get(indexOfParticle);
            if (indexOfParticle != otherIdx) {
                Particle particle2 = particles.get(otherIdx);

                double radius = particle1.getRadius() + particle2.getRadius();
                //Step 1: n=〈 x2−x1, y2−y1〉
                Vector normalVector = Vector.subtract(particle1.getPosition(), particle2.getPosition());
                //if true, we have a collision.
                if (normalVector.norm() < radius) {
                    // Move the particles to collision point, because they intersect at this point.
                    double rDifference = (radius - normalVector.norm()) / 2;
                    Vector unitNormalVector = new Vector(normalVector).unitVector();
                    particle1.setPosition(particle1.getPosition().add(unitNormalVector.scale(rDifference)));
                    particle2.setPosition(particle2.getPosition().add(unitNormalVector.scale(-rDifference)));

                    //Step 1:  find the unit vector of n, which we will call, also normal vector get recalculated,
                    //since we moved the particles to the collision point
                    normalVector = Vector.subtract(particle1.getPosition(), particle2.getPosition());
                    unitNormalVector = new Vector(normalVector).unitVector();
                    //Next we need the unit tangent vector. Just make the x component of the unit tangent vector equal
                    // to the negative of the y component of the unit normal vector, and make the y component of
                    // the unit tangent vector equal to the x component of the unit normal vector: ut = 〈−un y, un x〉
                    Vector unitTangentVector = new Vector(-unitNormalVector.y, unitNormalVector.x);
                    //Step:2 is skipped since we have velocity vectors.
                    //Step 3: we need to resolve the velocity vectors, v1 and v2, into normal and tangential components
                    //To do this, project the velocity vectors onto the unit normal and  unit tangent vectors by computing the dot product
                    //v1n= un⋅• v1, v1t= ut⋅• v1, v2n= un⋅• v2,  v2t= ut • v2
                    Vector velocityVector1 = particle1.getVelocity();
                    Vector velocityVector2 = particle2.getVelocity();
                    double v1n = unitNormalVector.dot(velocityVector1);
                    double v1t = unitTangentVector.dot(velocityVector1);
                    double v2n = unitNormalVector.dot(velocityVector2);
                    double v2t = unitTangentVector.dot(velocityVector2);
                    //Step 4:  Find the new tangential velocities (after the collision). The new tangential
                    //velocities are simply equal to the old ones, v1t = v1tFinal, v2t = v2tFinal

                    // Step 5, Find the final normal velocities. This is where we use the one-dimensional collision formulas
                    double m1 = particle1.getMass();
                    double m2 = particle2.getMass();
                    double v1nFinal = (v1n * (m1 - m2) + 2.0 * m2 * v2n) / (m1 + m2);
                    double v2nFinal = (v2n * (m2 - m1) + 2.0 * m1 * v1n) / (m1 + m2);

                    // Step 6: Convert the scalar normal and tangential velocities into vectors
                    Vector v1nVector = new Vector(unitNormalVector).scale(v1nFinal);
                    Vector v1tVector = new Vector(unitTangentVector).scale(v1t);
                    particle1.setVelocity(v1nVector.add(v1tVector));

                    Vector v2nVector = new Vector(unitNormalVector).scale(v2nFinal);
                    Vector v2tVector = new Vector(unitTangentVector).scale(v2t);
                    particle2.setVelocity(v2nVector.add(v2tVector));
                    return true;
                }
            }
        }
        return false;
    }

    private void applyAttraction(int indexOfParticle, double timePassedFromLastUpdate) {

        Particle particle1 = particles.get(indexOfParticle);
        Particle particle2;
        Vector attractionForceVector = new Vector();
        for (int idx = 0; idx < indexOfParticle; idx++) {
            particle2 = particles.get(idx);
            attractionForceVector.add(calculateAttractionVector(particle1, particle2));
        }
        for (int idx = indexOfParticle + 1; idx < particles.size(); idx++) {
            particle2 = particles.get(idx);
            attractionForceVector.add(calculateAttractionVector(particle1, particle2));
        }

        particle1.setVelocity(particle1.getVelocity()
                .add(attractionForceVector));
    }

    private Vector calculateAttractionVector(Particle particle1, Particle particle2) {
        Vector distance = particle2.getPosition().subtract(particle1.getPosition());
        double r = distance.norm();
        double force = (6.7 * state.getG() * particle2.getMass()) / (r * r);
        return distance.unitVector().scale(force);
    }


    // Check for collision with wall
    private void performWallCollisions(int indexOfParticle) {
        Particle particle = particles.get(indexOfParticle);
        double radius = particle.getRadius();
        Vector positionVector1 = particle.getPosition();
        Vector velocityVector1 = particle.getVelocity();
        if (positionVector1.x < startX + radius) {
            positionVector1.x = startX + radius;
            velocityVector1.x = -velocityVector1.x;
        }
        if (positionVector1.y < startY + radius) {
            positionVector1.y = startY + radius;
            velocityVector1.y = -velocityVector1.y;
        }
        if (positionVector1.x > width - radius) {
            positionVector1.x = width - radius;
            velocityVector1.x = -velocityVector1.x;
        }
        if (positionVector1.y > height - radius) {
            positionVector1.y = height - radius;
            velocityVector1.y = -velocityVector1.y;
        }
        particle.setPosition(positionVector1);
        particle.setVelocity(velocityVector1);
    }

    private void setRandomTypes() {

        for (int i = 0; i < state.getInfo().size(); i++) {
            state.getInfo().setColor(i, ColorManager.next());

            for (int j = 0; j < state.getInfo().size(); j++) {
                int sign = 1;
                if(state.isNegateSelfAttraction() && i == j) sign = -1;
                //set attractions
                if(state.isAttractionStandard()) {
                    state.getInfo().setAttraction(i, j,
                            sign *
                                    (state.getAttractionMean() + (state.getAttractionStd() * random.nextGaussian())));
                } else {
                    state.getInfo().setAttraction(i, j,
                            (state.getAttractionMin() + (state.getAttractionMax() - state.getAttractionMin()) *
                                    random.nextDouble()));
                }
                //set maxRs
                if(state.isMaxRStandard()){
                    state.getInfo().setAttraction(i, j,
                            (state.getMaxRMean() +  (state.getMaxRStd() * random.nextGaussian())));
                } else {
                    state.getInfo().setMaxDistance(i, j,
                            (state.getMaxRLower() + (state.getMaxRUpper() - state.getMaxRLower()) *
                                    random.nextDouble()));
                }
                //set minRs
                if(state.isMinRStandard()) {
                    state.getInfo().setAttraction(i, j,
                        (state.getMinRMean() + (state.getMinRStd() * random.nextGaussian())));
                } else {
                    state.getInfo().setMinDistance(i, j,
                            (state.getMinRLower() + (state.getMinRUpper() - state.getMinRLower()) *
                                    random.nextDouble()));
                }
            }
        }
    }

    public void setBoundaries(int startX, int startY, int width, int height) {
        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.height = height;
    }

    public void setRandomParticles() {
        if (particles.size() >= MAX_ATOMS) return;
        for (int i = 0; i < state.getParticleCount(); i++) {
            Particle particle = new Particle();
            particle.setPosition(
                    random.nextDouble() * (width - startX) + startX,
                    random.nextDouble() * (height - startY) + startY);
            int type = random.nextInt(state.getInfo().size());
            particle.setColor(state.getInfo().getColor(type));
            particle.setRadius(MIN_RADIUS);
            double vx = random.nextDouble() * 5;
            if (random.nextBoolean()) vx = -vx;
            double vy = random.nextDouble() * 5;
            if (random.nextBoolean()) vy = -vy;
            particle.setVelocity(vx, vy);
            //particle.setVelocity(0, 0);
            particles.add(particle);
        }


        //add a big particle
/*        Particle particle = new Particle();
        particle.setPosition(600,400);

        particle.setRadius(50);
        particle.setColor(Color.YELLOW);
        particle.setVelocity(0, 0);
        particles.add(particle);*/
    }

    public void setAnimator(Animator animator) {
        animator.attach(this);
    }

    public void setGraphics(GraphicsContext graphics) {
        this.graphics = graphics;
    }

    private void drawCosmos() {

        Vector initialPosition = store.getState().getMouseDragPosition();

        graphics.clearRect(0, 0,
                graphics.getCanvas().getWidth(), graphics.getCanvas().getHeight());
        for (Particle particle : particles) {
            graphics.setFill(particle.getColor());
            double radius = particle.getRadius();
            double diameter = radius + radius;

            graphics.fillOval(particle.getPosition().x - radius + initialPosition.x,
                    particle.getPosition().y - radius + initialPosition.y,
                    diameter, diameter);

        }
    }

    private void updatePositions() {
        if (particles != null && particles.size() > 0) {
            for (Particle particle : particles) {
                particle.setPosition(particle.getPosition().add(initialPositionVector));
            }
        }
    }

    @Override
    public void onChange(ParticlesState state) {
        //this.state = state;
/*        if(!Arrays.equals(state.getMouseDragCoordinates(), new double[2])){
            this.initialPositionVector.x = state.getMouseDragCoordinates()[0];
            this.initialPositionVector.y = state.getMouseDragCoordinates()[1];
            updatePositions();
            store.dispatch(new MouseDragStopAction());
        }*/
    }
}
