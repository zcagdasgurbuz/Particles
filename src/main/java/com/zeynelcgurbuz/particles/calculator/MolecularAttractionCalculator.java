package com.zeynelcgurbuz.particles.calculator;

import com.zeynelcgurbuz.particles.Particle;
import com.zeynelcgurbuz.particles.Vector;
import com.zeynelcgurbuz.particles.store.ParticlesState;

import java.util.ArrayList;

/**
 * Molecular attraction calculator.
 */
public class MolecularAttractionCalculator implements Calculator {

    @Override
    public void calculate(int index, ArrayList<Particle> particles, ParticlesState state) {
        Particle particle1 = particles.get(index);
        for (int idx = 0; idx < particles.size(); idx++) {
            Particle particle2 = particles.get(idx);
            double particle1MaxAttractionRadius = state.getInfo().getMaxDistance(particle1.getType(), particle2.getType());
            double particle1MinAttractionRadius = state.getInfo().getMinDistance(particle1.getType(), particle2.getType());
            double particle2MaxAttractionRadius = state.getInfo().getMaxDistance(particle2.getType(), particle1.getType());
            double particle2MinAttractionRadius = state.getInfo().getMinDistance(particle2.getType(), particle1.getType());
            Vector delta = Vector.subtract(particle2.getPosition(), particle1.getPosition());
            double distance = delta.length();
            delta.unitVector();
            if (index != idx
                    && distance <= particle1MaxAttractionRadius
                    && distance <= particle2MaxAttractionRadius
                    && distance >= particle1MinAttractionRadius
                    && distance >= particle2MinAttractionRadius) {
                applyForceBasedOnStyle(delta, particle1, particle2,
                        particle1MaxAttractionRadius, particle1MinAttractionRadius,
                        particle2MaxAttractionRadius, particle2MinAttractionRadius,
                        distance, state.getInRangeStyle(), state);
            } else if (index != idx && distance < particle1MinAttractionRadius
                    && distance < particle2MinAttractionRadius) {
                applyForceBasedOnStyle(delta, particle1, particle2,
                        particle1MaxAttractionRadius, particle1MinAttractionRadius,
                        particle2MaxAttractionRadius, particle2MinAttractionRadius,
                        distance, state.getBelowRangeStyle(), state);
            } else if (index != idx && distance > particle1MaxAttractionRadius
                    && distance > particle2MaxAttractionRadius) {
                applyForceBasedOnStyle(delta, particle1, particle2,
                        particle1MaxAttractionRadius, particle1MinAttractionRadius,
                        particle2MaxAttractionRadius, particle2MinAttractionRadius,
                        distance, state.getOutRangeStyle(), state);
            }
        }
    }

    /**
     * Applies force to the first particle based on attraction style and other particle's attraction.
     *
     * @param unitVector                   unitVector of the difference between particles' positions
     * @param particle1                    the particle 1
     * @param particle2                    the particle 2
     * @param particle1MaxAttractionRadius the particle 1 max attraction radius
     * @param particle1MinAttractionRadius the particle 1 min attraction radius
     * @param particle2MaxAttractionRadius the particle 2 max attraction radius
     * @param particle2MinAttractionRadius the particle 2 min attraction radius
     * @param distance                     the distance between particles
     * @param style                        the style
     * @param state                        the particle state
     */
    private static void applyForceBasedOnStyle(Vector unitVector, Particle particle1, Particle particle2,
                                        double particle1MaxAttractionRadius, double particle1MinAttractionRadius,
                                        double particle2MaxAttractionRadius, double particle2MinAttractionRadius,
                                        double distance, int style, ParticlesState state) {

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
            case 19:
                //if the second particle is on  (minR - maxR) / 2 -middle of the attraction range-, applies att1
                //when the second particle goes further away from middle of the attraction range att1 reduces.
                double top1 = 2.0f * Math.abs(distance - 0.5f *
                        (particle1MaxAttractionRadius + particle1MinAttractionRadius));
                double bottom1 = particle1MaxAttractionRadius - particle1MinAttractionRadius;
                unitVector.scale(force1 * (1.0 - top1 / bottom1));
                particle1.setVelocity(particle1.getVelocity().add(unitVector));
                break;
            case 20:
                //if the second particle is on  (minR - maxR) / 2 -middle of the attraction range-, applies att2
                //when the second particle goes further away from middle of the attraction range att2 reduces.
                double top2 = 2.0f * Math.abs(distance - 0.5f *
                        (particle2MaxAttractionRadius + particle2MinAttractionRadius));
                double bottom2 = particle2MaxAttractionRadius - particle2MinAttractionRadius;
                unitVector.scale(force2 * (1.0 - top2 / bottom2));
                particle1.setVelocity(particle1.getVelocity().add(unitVector));
                break;
            case 21:
                unitVector.scale(particle1MinAttractionRadius * ((1.0 / (particle1MinAttractionRadius)) -
                        (1.0 / distance)));
                particle1.setVelocity(particle1.getVelocity().add(unitVector));
                break;
            case 22:
                unitVector.scale(particle2MinAttractionRadius * ((1.0 / particle2MinAttractionRadius) -
                        (1.0 / distance)));
                particle1.setVelocity(particle1.getVelocity().add(unitVector));
                break;
            default:
                //yo!
        }
    }


}
