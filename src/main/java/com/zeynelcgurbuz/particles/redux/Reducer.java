package com.zeynelcgurbuz.particles.redux;

/**
 * The interface Reducer of Redux Pattern.
 *
 * @param <S> the type of the state
 */
public interface Reducer<S> {

    /**
     * Reducer function of the Redux pattern.
     *
     * @param oldState  the current state
     * @param action the action to be acted on.
     * @return the new state after action is performed.
     */
    S reduce(S oldState, Action action);
}
