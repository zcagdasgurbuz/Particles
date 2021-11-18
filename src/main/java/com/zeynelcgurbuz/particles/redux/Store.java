package com.zeynelcgurbuz.particles.redux;

import java.util.ArrayList;
import java.util.List;

/**
 * The store of the Redux Pattern
 *
 * @param <S> the type of the state.
 */
public class Store<S> {

    /**
     * The current/most updated state.
     */
    private S currentState;

    /**
     * The reducer of the redux pattern.
     */
    private final Reducer<S> reducer;

    /**
     * The subscribers of the store.
     */
    private final List<Subscriber<S>> subscribers = new ArrayList<>();

    /**
     * Constructor
     *
     * @param initialState the initial state
     * @param rootReducer  the root reducer
     */
    public Store(S initialState, Reducer<S> rootReducer) {
        this.currentState = initialState;
        this.reducer = rootReducer;
    }

    /**
     * Retrieves the current state.
     *
     * @return the state
     */
    public S getState() {
        return this.currentState;
    }

    /**
     * Will dispatch an action, to be handled in the reducer and
     *
     * @param action the action to be dispatched
     */
    public void dispatch(Action action) {
        this.currentState = reducer.reduce(this.currentState, action);
        notifySubscribers();
    }

    /**
     * Notifies every subscriber by calling their onChange methods.
     */
    private void notifySubscribers() {
        subscribers.forEach(subscriber -> subscriber.onChange(this.currentState));
    }

    /**
     * Subscribes a subscriber to the store. -calls onchage once
     *
     * @param subscriber the subscriber
     * @return the subscription
     */
    public Subscription subscribe(Subscriber<S> subscriber) {
        subscribers.add(subscriber);
        subscriber.onChange(this.currentState);
        //instantiate a new subscription and return
        return () -> subscribers.remove(subscriber);
    }

}
