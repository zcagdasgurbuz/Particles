package com.zeynelcgurbuz.particles.redux;

public interface Reducer<S> {

    S reduce(S state, Action action);

}
