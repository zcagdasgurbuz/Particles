package com.zeynelcgurbuz.particles.calculator;

import com.zeynelcgurbuz.particles.Particle;
import com.zeynelcgurbuz.particles.Vector;
import com.zeynelcgurbuz.particles.store.ParticlesState;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
    protected Void call() {
        for (Particle particle : particles) {
            Vector positionVector1 = particle.getPosition();
            Vector forceField = state.getForceFieldPosition();
            Vector previousForceField = state.getPreForceFieldPosition();
            if (forceField.x != 0 && forceField.y != 0) {
                double x = 0.0;
                double y = 0.0;
                if (previousForceField.x != 0 && previousForceField.y != 0) {
                    x = forceField.x - previousForceField.x;
                    y = forceField.y - previousForceField.y;
                }
                //meh
                if (positionVector1.x > (forceField.x - 30) && positionVector1.x < (forceField.x + 30) &&
                        positionVector1.y > (forceField.y - 30) && positionVector1.y < (forceField.y + 30)) {
                    positionVector1.x += x;
                    positionVector1.y += y;
                    particle.setPosition(positionVector1);
                } else {
                    particle.setPosition(particle.getPosition().add(particle.getVelocity()));
                }
            } else {
                particle.setPosition(particle.getPosition().add(particle.getVelocity()));
            }

            //friction
            particle.setVelocity(particle.getVelocity().scale(1.0 - state.getFriction()));
        }
        return null;
    }
}
