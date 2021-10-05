package com.zeynelcgurbuz.particles.redux;

import java.util.ArrayList;
import java.util.List;

public class Store<S> {

    private S currentState;

    private Reducer<S> reducer;

    private List<Subscriber<S>> subscribers = new ArrayList<>();

    public Store(S initialState, Reducer<S> rootReducer) {
        this.currentState = initialState;
        this.reducer = rootReducer;
    }

    public S getState() {
        return this.currentState;
    }

    public void dispatch(Object action) {
        this.currentState = reducer.reduce(this.currentState, (Action) action);
        notifySubscribers();
    }

    private void notifySubscribers() {
        subscribers.forEach(subscriber -> subscriber.onChange(this.currentState));
    }

    public Subscription subscribe(Subscriber<S> subscriber) {
        subscribers.add(subscriber);

        subscriber.onChange(this.currentState);

        return () -> {
            subscribers.remove(subscriber);
        };
    }

}
