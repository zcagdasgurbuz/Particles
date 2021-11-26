package com.zeynelcgurbuz.particles.calculator;

import com.zeynelcgurbuz.particles.Particle;
import com.zeynelcgurbuz.particles.Vector;
import com.zeynelcgurbuz.particles.store.ParticlesState;

import java.util.ArrayList;

/**
 * Particle collision calculator.  Calculations from https://www.vobarian.com/collisions/2dcollisions2.pdf
 */
public class ParticleCollisionCalculator implements Calculator{

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
}
