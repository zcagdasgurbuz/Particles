package com.zeynelcgurbuz.particles.calculator;

import com.zeynelcgurbuz.particles.Particle;
import com.zeynelcgurbuz.particles.Vector;
import com.zeynelcgurbuz.particles.store.ParticlesState;

import java.util.ArrayList;

/**
 * Gravitational attraction calculator.
 */
public class GravitationalAttractionCalculator implements Calculator {
    @Override
    public void calculate(int index, ArrayList<Particle> particles, ParticlesState state) {
        Particle particle1 = particles.get(index);
        Particle particle2;
        Vector attractionForceVector = new Vector();
        for (int idx = 0; idx < index; idx++) {
            particle2 = particles.get(idx);
            attractionForceVector.add(calculateAttractionVector(particle1, particle2, state));
        }
        for (int idx = index + 1; idx < particles.size(); idx++) {
            particle2 = particles.get(idx);
            attractionForceVector.add(calculateAttractionVector(particle1, particle2, state));
        }
        particle1.setVelocity(particle1.getVelocity()
                .add(attractionForceVector));
    }

    /**
     * Helper, calculates attraction vector.
     *
     * @param particle1 the particle 1
     * @param particle2 the particle 2
     * @param state     the state
     * @return the vector
     */
    private static Vector calculateAttractionVector(Particle particle1, Particle particle2, ParticlesState state) {
        Vector distance = particle2.getPosition().subtract(particle1.getPosition());
        double r = distance.length();
        double force = (6.7 * state.getG() * particle2.getMass()) / (r * r);
        return distance.unitVector().scale(force);
    }

}
