package com.zeynelcgurbuz.particles.calculator;

import com.zeynelcgurbuz.particles.Particle;
import com.zeynelcgurbuz.particles.store.ParticlesState;

import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;

/**
 * The recursive calculator, is used for concurrent calculations
 */
public class RecursiveCalculator extends RecursiveAction {

    /**
     * Start particle index of the calculation.
     */
    private int start;
    /**
     * End particle index of the calculation
     */
    private int end;
    /**
     * The recursion limit.
     */
    private int recLimit;
    /**
     * The calculator to be used for recursive calculation.
     */
    private Calculator calculator;
    /**
     * The particles.
     */
    private ArrayList<Particle> particles;
    /**
     * The particles state.
     */
    private ParticlesState state;

    /**
     * Instantiates a new Recursive molecular attraction.
     *
     * @param start      Start particle index of the calculation, inclusive
     * @param end        End particle index of the calculation, exclusive
     * @param recLimit   the rec limit
     * @param calculator the calculator
     * @param particles  the particles
     * @param state      the state
     */
    public RecursiveCalculator(int start, int end, int recLimit, Calculator calculator,
                               ArrayList<Particle> particles, ParticlesState state){
        this.start = start;
        this.end = end;
        this.recLimit = recLimit;
        this.calculator = calculator;
        this.particles = particles;
        this.state = state;
    }

    @Override
    protected void compute() {
        int length  = end - start;
        if(length < recLimit){
            for(int idx = start; idx < end; idx++){
                calculator.calculate(idx, particles ,state);
            }
        } else {
/*            int q = (int)Math.round(length / 4.0);
            int h = (int)Math.round(length / 2.0);
            int qqq = (int)Math.round(length / 4.0  * 3.0);
            RecursiveCalculator q1 = new RecursiveCalculator(start, start + q, recLimit, calculator ,particles ,state);
            RecursiveCalculator q2 = new RecursiveCalculator(start + q, start + h, recLimit, calculator ,particles ,state);
            RecursiveCalculator q3 = new RecursiveCalculator(start + h, start + qqq, recLimit, calculator ,particles ,state);
            RecursiveCalculator q4 = new RecursiveCalculator(start + qqq, end, recLimit, calculator ,particles ,state);
            invokeAll(q1, q2, q3, q4);*/
            int mid = start + ((end - start) / 2);
            RecursiveCalculator left = new RecursiveCalculator(start, mid, recLimit, calculator ,particles ,state);
            RecursiveCalculator right = new RecursiveCalculator(mid, end, recLimit, calculator ,particles ,state);
            left.fork();
            right.compute();
            left.join();
        }
    }
}
