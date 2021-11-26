package com.zeynelcgurbuz.particles.calculator;

import com.zeynelcgurbuz.particles.Particle;
import com.zeynelcgurbuz.particles.store.ParticlesState;

import java.util.ArrayList;

/**
 * The interface calculator, can be thought of command pattern.
 */
public interface Calculator {

    /**
     * Performs the necessary calculations for the given particle
     *
     * @param index     the index of the particle to be calculated
     * @param particles the all particles
     * @param state     the particles state
     */
    void calculate(int index, ArrayList<Particle> particles, ParticlesState state);
}
