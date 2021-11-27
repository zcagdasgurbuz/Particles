package com.zeynelcgurbuz.particles.calculator;

import com.zeynelcgurbuz.particles.Particle;
import com.zeynelcgurbuz.particles.Vector;
import com.zeynelcgurbuz.particles.store.ParticlesState;

import java.util.ArrayList;

/**
 * Wall collision calculator.
 */
public class WallCollisionCalculator implements Calculator {

    /**
     * The previous force field.
     */
    private Vector previousForceField = new Vector();

    @Override
    public void calculate(int index, ArrayList<Particle> particles, ParticlesState state) {
        Particle particle = particles.get(index);
        double radius = particle.getRadius();
        Vector forceField = state.getForceFieldPosition();
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
        if (forceField.x != 0 && forceField.y != 0) {
            double x = 0.0;
            double y = 0.0;
            if (previousForceField.x != 0 && previousForceField.y != 0) {
                x = forceField.x - previousForceField.x;
                y = forceField.y - previousForceField.y;
            }
            if (positionVector1.x > (forceField.x - 30) && positionVector1.x < (forceField.x + 30) &&
                    positionVector1.y > (forceField.y - 30) && positionVector1.y < (forceField.y + 30)) {
                positionVector1.x += x;
                positionVector1.y += y;
                //positionVector1.x = forceField.x;
                //positionVector1.y = forceField.y;
                //velocityVector1.x = -velocityVector1.x;
                //velocityVector1.y = -velocityVector1.y;
            }
            if (index == particles.size() - 1)
                previousForceField = new Vector(forceField);
        } else {
            previousForceField = new Vector();
        }
        particle.setPosition(positionVector1);
        particle.setVelocity(velocityVector1);
    }
}
