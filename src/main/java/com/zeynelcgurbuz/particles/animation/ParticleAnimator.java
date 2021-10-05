package com.zeynelcgurbuz.particles.animation;

import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.HashSet;
import java.util.Set;

public class ParticleAnimator implements Animator {

    private final AnimationTimer animator;
    private final Set<Animatable> particles;
    private final SimpleBooleanProperty active;
    private boolean autoStartStop;
    private long previousFrame;
    private double timeBetweenLastTwoFrame;

    public ParticleAnimator() {
        setAutoStartStop(false);
        particles = new HashSet<>();
        active = new SimpleBooleanProperty(false);
        previousFrame = 0;

        animator = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(previousFrame > 0){
                    timeBetweenLastTwoFrame = now - previousFrame;
                }
                previousFrame = now;
                notifyNewFrame();
            }
        };
    }

    public ParticleAnimator(boolean autoStartStop){
        this();
        setAutoStartStop(autoStartStop);
    }

    @Override
    public void start() {
        if (particles.size() > 0 && !active.get()) {
            animator.start();
            active.set(true);
        }
    }

    @Override
    public void pause() {
        if (particles.size() > 0 && active.get()) {
            animator.stop();
            active.set(false);
        }
    }

    @Override
    public void stop() {
        pause();
        particles.clear();
    }

    @Override
    public void attach(Animatable animatable) {
        if (animatable != null) {
            particles.add(animatable);
        }
        handleStartStop();
    }

    @Override
    public void attachAll(Set<Animatable> animatables) {
        if (animatables != null) {
            for (Animatable animatable : animatables) {
                if (animatable != null) {
                    particles.add(animatable);
                }
            }
        }
        handleStartStop();
    }

    @Override
    public void detach(Animatable animatable) {
        particles.remove(animatable);
        handleStartStop();
    }

    @Override
    public void detachAll(Set<Animatable> animatables) {
        particles.removeAll(animatables);
        handleStartStop();
    }

    @Override
    public void detachAll() {
        particles.clear();
        handleStartStop();
    }

    @Override
    public void notifyNewFrame() {
        for (Animatable animatable : particles){
            animatable.update(timeBetweenLastTwoFrame);
        }
    }

    public void setAutoStartStop(boolean autoStartStop){
        this.autoStartStop = autoStartStop;
    }


    /**
     * Handles start and stop of the animation.
     */
    private void handleStartStop() {
        if (autoStartStop) {
            active.set(particles.size() > 0);
            if (active.get()) {
                animator.start();
            } else {
                animator.stop();
            }
        }
    }
}
