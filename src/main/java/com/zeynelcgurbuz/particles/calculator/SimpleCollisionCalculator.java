package com.zeynelcgurbuz.particles.calculator;

import com.zeynelcgurbuz.particles.Particle;
import com.zeynelcgurbuz.particles.Vector;
import com.zeynelcgurbuz.particles.store.ParticlesState;

import java.util.ArrayList;

/**
 * Particle collision calculator.  Calculations from https://www.vobarian.com/collisions/2dcollisions2.pdf
 */
public class SimpleCollisionCalculator implements Calculator {

    @Override
    public void calculate(int index, ArrayList<Particle> particles, ParticlesState state) {
        for (int otherIdx = 0; otherIdx < particles.size(); otherIdx++) {
            Particle particle1 = particles.get(index);
            if (index != otherIdx) {
                Particle particle2 = particles.get(otherIdx);
                double expectedR = particle1.getRadius() + particle2.getRadius();
                //Step 1: n=〈 x2−x1, y2−y1〉
                Vector normalVector = Vector.subtract(particle1.getPosition(), particle2.getPosition());
                //if true, we have a collision.
                if (normalVector.length() < expectedR) {
                    // Move the particles to collision point, because they intersect at this point.
                    double rDifference = (expectedR - normalVector.length()) / 2;
                    Vector unitNormalVector = new Vector(normalVector).unitVector();
                    particle1.setPosition(particle1.getPosition().add(unitNormalVector.scale(rDifference)));
                    particle2.setPosition(particle2.getPosition().add(unitNormalVector.scale(-rDifference)));
                }
            }
        }
    }
}
