package com.zeynelcgurbuz.particles.animation;

import java.util.Set;

public interface Animator {
    void start();
    void pause();
    void stop();
    void attach(Animatable animatable);
    void attachAll(Set<Animatable> animatables);
    void detach(Animatable animatable);
    void detachAll(Set<Animatable> animatables);
    void detachAll();
    void notifyNewFrame();
}
