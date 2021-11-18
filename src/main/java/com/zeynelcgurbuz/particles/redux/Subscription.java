package com.zeynelcgurbuz.particles.redux;

/**
 * The interface Subscription, to be able to unsubscribe.
 */
@FunctionalInterface
public interface Subscription {

    /**
     * Unsubscribe, from the subscription.
     */
    void unsubscribe();
}
