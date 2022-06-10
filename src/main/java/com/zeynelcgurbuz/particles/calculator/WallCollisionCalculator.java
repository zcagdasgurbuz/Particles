package com.zeynelcgurbuz.particles.calculator;

import com.zeynelcgurbuz.particles.Particle;
import com.zeynelcgurbuz.particles.Vector;
import com.zeynelcgurbuz.particles.store.ParticlesState;

import java.util.ArrayList;

/**
 * Wall collision calculator.
 */
public class WallCollisionCalculator implements Calculator {

    @Override
    public void calculate(int index, ArrayList<Particle> particles, ParticlesState state) {
        Particle particle = particles.get(index);
        double radius = particle.getRadius();
        Vector positionVector = particle.getPosition();
        Vector velocityVector = particle.getVelocity();
        if (positionVector.x < radius) {
            positionVector.x = radius;
            velocityVector.x = -velocityVector.x;
        }
        if (positionVector.y < radius) {
            positionVector.y = radius;
            velocityVector.y = -velocityVector.y;
        }
        if (positionVector.x + radius > state.getWidth() ) {
            positionVector.x = state.getWidth() - radius;
            velocityVector.x = -velocityVector.x;
        }
        if (positionVector.y + radius > state.getHeight()) {
            positionVector.y = state.getHeight() - radius;
            velocityVector.y = -velocityVector.y;
        }

        particle.setVelocity(velocityVector);
        particle.setPosition(positionVector);
    }
}
