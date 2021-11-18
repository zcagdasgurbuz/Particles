package com.zeynelcgurbuz.particles.redux;

/**
 * The interface Subscriber.
 *
 * @param <S> the type of the store.
 */
@FunctionalInterface
public interface Subscriber<S> {

    /**
     * To be called on change notification
     *
     * @param state the changed state
     */
    void onChange(S state);
}
