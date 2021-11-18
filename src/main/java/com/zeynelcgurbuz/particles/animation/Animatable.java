package com.zeynelcgurbuz.particles.animation;

/**
 * The interface Animatable, can be thought of as an observer of a Subject which is the animator.
 */
public interface Animatable {
    /**
     * Method will be called every notification time.
     *
     * @param timePassedFromLastFrame the time passed from last frame
     */
    void update(double timePassedFromLastFrame);
}
