package com.zeynelcgurbuz.particles.calculator;

import com.zeynelcgurbuz.particles.Particle;
import com.zeynelcgurbuz.particles.store.ParticlesState;
import javafx.concurrent.Task;

import java.util.ArrayList;

/**
 * The type Position calculator.
 */
public class PositionCalculator extends Task<Void> {

    /**
     * The all particles.
     */
    private ArrayList<Particle> particles;
    /**
     * The State.
     */
    private ParticlesState state;

    /**
     * Instantiates a new Position calculator.
     *
     * @param particles the particles
     * @param state     the state
     */
    public PositionCalculator(ArrayList<Particle> particles, ParticlesState state){
        this.particles = particles;
        this.state = state;
    }

    @Override
    protected Void call() throws Exception {
        for (Particle particle1 : particles) {
            //update position
            particle1.setPosition(particle1.getPosition().add(particle1.getVelocity()));
            //friction
            particle1.setVelocity(particle1.getVelocity().scale(1.0 - state.getFriction()));
        }
        return null;
    }
}
