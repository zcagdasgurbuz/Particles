package com.zeynelcgurbuz.particles.animation;

import java.util.Set;

/**
 * The interface Animator, can be thought of as a Subject,
 */
public interface Animator {
    /**
     * Start the animation.
     */
    void start();

    /**
     * Pause the animation.
     */
    void pause();

    /**
     * Stop the animation.
     */
    void stop();

    /**
     * Attach the animatable to the dependents.
     *
     * @param animatable the animatable
     */
    void attach(Animatable animatable);

    /**
     * Attach all animatables to the dependents.
     *
     * @param animatables the animatables
     */
    void attachAll(Set<Animatable> animatables);

    /**
     * Detach.
     *
     * @param animatable the animatable
     */
    void detach(Animatable animatable);

    /**
     * Detach all passed animatables from the dependents
     *
     * @param animatables the animatables
     */
    void detachAll(Set<Animatable> animatables);

    /**
     * Detach all animatables from the dependents.
     */
    void detachAll();

    /**
     * Notifies all dependents.
     */
    void notifyNewFrame();

}
