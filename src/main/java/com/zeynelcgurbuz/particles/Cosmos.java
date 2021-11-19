package com.zeynelcgurbuz.particles;

import com.zeynelcgurbuz.particles.animation.Animatable;
import com.zeynelcgurbuz.particles.animation.Animator;
import com.zeynelcgurbuz.particles.redux.Store;
import com.zeynelcgurbuz.particles.redux.Subscriber;
import com.zeynelcgurbuz.particles.redux.Subscription;
import com.zeynelcgurbuz.particles.store.ParticlesState;
import com.zeynelcgurbuz.particles.store.actions.GenerateRandomParticlesInfoAction;
import com.zeynelcgurbuz.particles.store.actions.RestartFulfilledAction;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

/**
 * Cosmos, where particle interactions take place
 */
public class Cosmos implements Animatable, Subscriber<ParticlesState> {

    /**
     * Random number generator.
     */
    private final Random random = new Random();
    /**
     * All particles store here.
     */
    private final ArrayList<Particle> particles = new ArrayList<>();
    /**
     * The store.
     */
    private final Store<ParticlesState> store;
    /**
     * The store subscription.
     */
    private final Subscription storeSubscription;
    /**
     * The particles state.
     */
    private ParticlesState state;


    /**
     * Instantiates a new Cosmos.
     *
     * @param store the store
     */
    public Cosmos(Store<ParticlesState> store) {
        this.store = store;
        storeSubscription = store.subscribe(this);
        this.state = store.getState();
        //if no particle type info generate one
        if (state.getInfo() == null) {
            store.dispatch(new GenerateRandomParticlesInfoAction());
        }
        setRandomParticles();
    }

    @Override
    public void update(double timePassedFromLastFrame) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
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
                return null;
            }

            //ready to draw
            @Override
            protected void succeeded() {
                super.succeeded();
                Platform.runLater(() -> drawCosmos());
            }
        };
        new Thread(task).start();
    }

    /**
     * Apply molecular attraction.
     *
     * @param indexOfParticle the index of particle
     */
    private void applyAttractionMolecular(int indexOfParticle) {
        Particle particle1 = particles.get(indexOfParticle);
        for (int idx = 0; idx < particles.size(); idx++) {
            Particle particle2 = particles.get(idx);
            double particle1MaxAttractionRadius = state.getInfo().getMaxDistance(particle1.getType(), particle2.getType());
            double particle1MinAttractionRadius = state.getInfo().getMinDistance(particle1.getType(), particle2.getType());
            double particle2MaxAttractionRadius = state.getInfo().getMaxDistance(particle2.getType(), particle1.getType());
            double particle2MinAttractionRadius = state.getInfo().getMinDistance(particle2.getType(), particle1.getType());
            Vector delta = Vector.subtract(particle2.getPosition(), particle1.getPosition());
            double distance = delta.length();
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
            } else if (indexOfParticle != idx && distance > particle1MaxAttractionRadius
                    && distance > particle2MaxAttractionRadius) {
                applyForceBasedOnStyle(delta, particle1, particle2, distance, state.getOutRangeStyle());
            }
        }
    }

    /**
     * Applies force to the first particle based on attraction style and other particle's attraction.
     *
     * @param unitVector unitVector of the difference between particles' positions
     * @param particle1  the particle 1
     * @param particle2  the particle 2
     * @param distance   the distance between particles
     * @param style      the style
     */
    private void applyForceBasedOnStyle(Vector unitVector, Particle particle1, Particle particle2, double distance, int style) {
        double force1 = state.getInfo().getAttraction(particle1.getType(), particle2.getType());
        double force2 = state.getInfo().getAttraction(particle2.getType(), particle1.getType());

        switch (style) {
            case 1:
                unitVector.scale(force1 + force2);
                particle1.setVelocity(particle1.getVelocity().add(unitVector));
                break;
            case 2:
                unitVector.scale(force1 - force2);
                particle1.setVelocity(particle1.getVelocity().add(unitVector));
                break;
            case 3:
                unitVector.scale(force2 - force1);
                particle1.setVelocity(particle1.getVelocity().add(unitVector));
                break;
            case 4:
                unitVector.scale((force1 * force2) / (distance * distance));
                particle1.setVelocity(particle1.getVelocity().add(unitVector));
                break;
            case 5:
                unitVector.scale(-(force1 * force2) / (distance * distance));
                particle1.setVelocity(particle1.getVelocity().add(unitVector));
                break;
            case 6:
                unitVector.scale((force1 + force2) / distance * distance);
                particle1.setVelocity(particle1.getVelocity().add(unitVector));
                break;
            case 7:
                unitVector.scale((force1 - force2) / (distance * distance));
                particle1.setVelocity(particle1.getVelocity().add(unitVector));
                break;
            case 8:
                unitVector.scale((force2 - force1) / (distance * distance));
                particle1.setVelocity(particle1.getVelocity().add(unitVector));
                break;
            case 9:
                unitVector.scale(1 / (distance * distance));
                particle1.setVelocity(particle1.getVelocity().add(unitVector));
                break;
            case 10:
                unitVector.scale(-1 / (distance * distance));
                particle1.setVelocity(particle1.getVelocity().add(unitVector));
                break;
            case 11:
                unitVector.scale(force1);
                particle1.setVelocity(particle1.getVelocity().add(unitVector));
                break;
            case 12:
                unitVector.scale(force2);
                particle1.setVelocity(particle1.getVelocity().add(unitVector));
                break;
            case 13:
                unitVector.scale(-force1);
                particle1.setVelocity(particle1.getVelocity().add(unitVector));
                break;
            case 14:
                unitVector.scale(-force2);
                particle1.setVelocity(particle1.getVelocity().add(unitVector));
                break;
            case 15:
                unitVector.scale(force1 / distance * distance);
                particle1.setVelocity(particle1.getVelocity().add(unitVector));
                break;
            case 16:
                unitVector.scale(force2 / (distance * distance));
                particle1.setVelocity(particle1.getVelocity().add(unitVector));
                break;
            case 17:
                unitVector.scale(-force1 / distance * distance);
                particle1.setVelocity(particle1.getVelocity().add(unitVector));
                break;
            case 18:
                unitVector.scale(-force2 / (distance * distance));
                particle1.setVelocity(particle1.getVelocity().add(unitVector));
                break;
            default:
                //yo!
        }
    }

    /**
     * Perform particles collisions. Calculations from https://www.vobarian.com/collisions/2dcollisions2.pdf
     *
     * @param indexOfParticle the index of particle to be checked for collisions to other particles
     */
    private void performParticleCollisions(int indexOfParticle) {
        for (int otherIdx = 0; otherIdx < particles.size(); otherIdx++) {
            Particle particle1 = particles.get(indexOfParticle);
            if (indexOfParticle != otherIdx) {
                Particle particle2 = particles.get(otherIdx);
                double radius = particle1.getRadius() + particle2.getRadius();
                //Step 1: n=〈 x2−x1, y2−y1〉
                Vector normalVector = Vector.subtract(particle1.getPosition(), particle2.getPosition());
                //if true, we have a collision.
                if (normalVector.length() < radius) {
                    // Move the particles to collision point, because they intersect at this point.
                    double rDifference = (radius - normalVector.length()) / 2;
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
                }
            }
        }
    }

    /**
     * Apply gravitational attraction.
     *
     * @param indexOfParticle          the index of particle
     * @param timePassedFromLastUpdate the time passed from last update
     */
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

    /**
     * Helper, calculates attraction vector.
     *
     * @param particle1 the particle 1
     * @param particle2 the particle 2
     * @return the vector
     */
    private Vector calculateAttractionVector(Particle particle1, Particle particle2) {
        Vector distance = particle2.getPosition().subtract(particle1.getPosition());
        double r = distance.length();
        double force = (6.7 * state.getG() * particle2.getMass()) / (r * r);
        return distance.unitVector().scale(force);
    }


    /**
     * Perform wall collisions.
     *
     * @param indexOfParticle the index of particle
     */
// Check for collision with wall
    private void performWallCollisions(int indexOfParticle) {
        Particle particle = particles.get(indexOfParticle);
        double radius = particle.getRadius();
        Vector positionVector1 = particle.getPosition();
        Vector velocityVector1 = particle.getVelocity();
        if (positionVector1.x < radius) {
            positionVector1.x = radius;
            velocityVector1.x = -velocityVector1.x;
        }
        if (positionVector1.y < radius) {
            positionVector1.y = radius;
            velocityVector1.y = -velocityVector1.y;
        }
        if (positionVector1.x > state.getWidth() - radius) {
            positionVector1.x = state.getWidth() - radius;
            velocityVector1.x = -velocityVector1.x;
        }
        if (positionVector1.y > state.getHeight() - radius) {
            positionVector1.y = state.getHeight() - radius;
            velocityVector1.y = -velocityVector1.y;
        }
        particle.setPosition(positionVector1);
        particle.setVelocity(velocityVector1);
    }

    /**
     * Sets random particles based on current particles state
     */
    public void setRandomParticles() {
        particles.clear();
        for (int i = 0; i < state.getParticleCount(); i++) {
            Particle particle = new Particle();
            particle.setPosition(new Vector(
                    random.nextDouble() * state.getWidth(),
                    random.nextDouble() * state.getHeight()));
            int type = random.nextInt(state.getInfo().size());
            particle.setType(type);
            particle.setColor(Color.web(state.getInfo().getColor(type)));
            particle.setRadius(state.getFlatRadius());
            double vx = random.nextDouble() * 5;
            if (random.nextBoolean()) vx = -vx;
            double vy = random.nextDouble() * 5;
            if (random.nextBoolean()) vy = -vy;
            particle.setVelocity(new Vector(vx, vy));
            //particle.setVelocity(0, 0);
            particles.add(particle);
        }
    }


    /**
     * Sets animator of this animatable cosmos.
     *
     * @param animator the animator
     */
    public void setAnimator(Animator animator) {
        animator.attach(this);
    }

    /**
     * Draws all the particles using graphics context in the state.
     */
    private void drawCosmos() {
        GraphicsContext graphics = state.getGraphics();
        Vector initialPosition = new Vector();
        if (!state.isWallsActive()) initialPosition = store.getState().getMouseDragPosition();
        //clear previous frame
        graphics.clearRect(0, 0,
                graphics.getCanvas().getWidth(), graphics.getCanvas().getHeight());
        //draw all particles
        for (Particle particle : particles) {
            graphics.setFill(particle.getColor());
            double radius = particle.getRadius();
            double diameter = radius + radius;

            graphics.fillOval(particle.getPosition().x - radius + initialPosition.x,
                    particle.getPosition().y - radius + initialPosition.y,
                    diameter, diameter);
        }
    }

    @Override
    public void onChange(ParticlesState state) {
        this.state = state;
        if (state.isRestartRequested()) {
            setRandomParticles();
            store.dispatch(new RestartFulfilledAction());
        }
    }
}
