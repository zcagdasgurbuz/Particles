package com.zeynelcgurbuz.particles;

import com.zeynelcgurbuz.particles.animation.Animatable;
import com.zeynelcgurbuz.particles.animation.Animator;
import com.zeynelcgurbuz.particles.calculator.GravitationalAttractionCalculator;
import com.zeynelcgurbuz.particles.calculator.MolecularAttractionCalculator;
import com.zeynelcgurbuz.particles.calculator.ParticleCollisionCalculator;
import com.zeynelcgurbuz.particles.calculator.PositionCalculator;
import com.zeynelcgurbuz.particles.calculator.RecursiveCalculator;
import com.zeynelcgurbuz.particles.calculator.SimpleCollisionCalculator;
import com.zeynelcgurbuz.particles.calculator.WallCollisionCalculator;
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
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Cosmos, where particle interactions take place
 */
public class Cosmos implements Animatable, Subscriber<ParticlesState> {

    /**
     * Recursive task particles limit
     */
    private final int REC_LIMIT = 5;
    /**
     * Recursive task executive service.
     */
    private final ForkJoinPool forkJoinPool;
    /**
     * Random number generator.
     */
    private final Random random = new Random();
    /**
     * All particles store here.
     */
    private final ArrayList<Particle> particles;
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
     * The molecular attraction calculator.
     */
    private final MolecularAttractionCalculator molecularAttractionCalculator;
    /**
     * The gravitational attraction calculator.
     */
    private final GravitationalAttractionCalculator gravitationalAttractionCalculator;
    /**
     * The wall collision calculator.
     */
    private final WallCollisionCalculator wallCollisionCalculator;
    /**
     * The particle collision calculator.
     */
    private final ParticleCollisionCalculator particleCollisionCalculator;
    /**
     * The particle collision calculator.
     */
    private final SimpleCollisionCalculator simpleCollisionCalculator;


    /**
     * Instantiates a new Cosmos.
     *
     * @param store the store
     */
    public Cosmos(Store<ParticlesState> store) {

        particles = new ArrayList<>();
        this.store = store;
        storeSubscription = store.subscribe(this);
        this.state = store.getState();
        //if no particle type info generate one
        if (state.getInfo() == null) {
            store.dispatch(new GenerateRandomParticlesInfoAction());
        }
        setRandomParticles();

        forkJoinPool = new ForkJoinPool();
        molecularAttractionCalculator = new MolecularAttractionCalculator();
        gravitationalAttractionCalculator = new GravitationalAttractionCalculator();
        wallCollisionCalculator = new WallCollisionCalculator();
        particleCollisionCalculator = new ParticleCollisionCalculator();
        simpleCollisionCalculator = new SimpleCollisionCalculator();
    }

    /**
     * Sets animator of this animatable cosmos.
     *
     * @param animator the animator
     */
    public void setAnimator(Animator animator) {
        animator.attach(this);
    }


    @Override
    public void onChange(ParticlesState state) {
        this.state = state;
        if (state.isRestartRequested()) {
            setRandomParticles();
            store.dispatch(new RestartFulfilledAction());
        }
    }

    @Override
    public void update(double timePassedFromLastFrame) {

        RecursiveCalculator moleculerAttractionCalc = new RecursiveCalculator(0,
                particles.size(), REC_LIMIT, molecularAttractionCalculator, particles, state);
        RecursiveCalculator gravitationalAttractionCalc = new RecursiveCalculator(0,
                particles.size(), REC_LIMIT, gravitationalAttractionCalculator, particles, state);
        RecursiveCalculator wallCollisionCalc = new RecursiveCalculator(0,
                particles.size(), REC_LIMIT, wallCollisionCalculator, particles, state);
        RecursiveCalculator particlesCollisionCalc = new RecursiveCalculator(0,
                particles.size(), REC_LIMIT, particleCollisionCalculator, particles, state);
        RecursiveCalculator simpleCollisionCalc = new RecursiveCalculator(0,
                particles.size(), REC_LIMIT, simpleCollisionCalculator, particles, state);

        PositionCalculator positionCalculator = new PositionCalculator(particles, state);

        if (state.isMolAttract()) {
            forkJoinPool.invoke(moleculerAttractionCalc);
        }
        if (state.isGravAttract()) {
            forkJoinPool.invoke(gravitationalAttractionCalc);
        }
        if (state.isWallsActive())
            forkJoinPool.invoke(wallCollisionCalc);

        forkJoinPool.submit(positionCalculator);
        if(state.isElasticCollision()) {
            forkJoinPool.invoke(particlesCollisionCalc);
        }
        else {
            forkJoinPool.invoke(simpleCollisionCalc);
        }

        drawCosmos();
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
            int type = i % state.getInfo().size();
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
     * Draws all the particles using graphics context in the state.
     */
    private void drawCosmos() {
        GraphicsContext graphics = state.getGraphics();
        Vector initialPosition = new Vector();
        Vector forceField = state.getForceFieldPosition();
        //clear previous frame
        graphics.clearRect(0, 0,
                graphics.getCanvas().getWidth(), graphics.getCanvas().getHeight());

        if (!state.isWallsActive()){
            initialPosition = store.getState().getMouseDragPosition();
        } else {
            if(forceField.x != 0 && forceField.y != 0){
                graphics.setStroke(Color.WHITE);
                graphics.strokeOval(forceField.x - 30, forceField.y - 30, 60,60);
            }
        }

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
}
