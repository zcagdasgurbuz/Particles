package com.zeynelcgurbuz.particles.redux;

@FunctionalInterface
public interface Subscriber<S> {
    void onChange(S state);
}
