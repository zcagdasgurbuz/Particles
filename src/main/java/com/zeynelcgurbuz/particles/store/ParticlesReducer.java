package com.zeynelcgurbuz.particles.store;

import com.zeynelcgurbuz.particles.ColorManager;
import com.zeynelcgurbuz.particles.ParticlesInfo;
import com.zeynelcgurbuz.particles.redux.Action;
import com.zeynelcgurbuz.particles.redux.Reducer;
import com.zeynelcgurbuz.particles.store.actions.BoundariesChangedAction;
import com.zeynelcgurbuz.particles.store.actions.ForceFieldAction;
import com.zeynelcgurbuz.particles.store.actions.GenerateRandomParticlesInfoAction;
import com.zeynelcgurbuz.particles.store.actions.MouseDragAction;
import com.zeynelcgurbuz.particles.store.actions.RestartAction;
import com.zeynelcgurbuz.particles.store.actions.RestartFulfilledAction;
import com.zeynelcgurbuz.particles.store.actions.SetGraphicsContextAction;
import com.zeynelcgurbuz.particles.store.actions.SetStateAction;

import java.util.Random;

/**
 * The reducer function, handles the actions.
 */
public class ParticlesReducer implements Reducer<ParticlesState> {
    @Override
    public ParticlesState reduce(ParticlesState oldState, Action action) {
        if (action instanceof SetStateAction) {
            return ((SetStateAction) action).getState().setGraphics(oldState.getGraphics())
                    .setWidth(oldState.getWidth()).setHeight(oldState.getHeight());
        } else if (action instanceof RestartAction) {
            ParticlesState state = oldState.copy();
            setRandomTypes(state);
            state.setRestartRequested(true);
            return state;
        } else if (action instanceof RestartFulfilledAction) {
            return oldState.shallowCopy().setRestartRequested(false);
        } else if (action instanceof SetGraphicsContextAction) {
            return oldState.shallowCopy().setGraphics(((SetGraphicsContextAction) action).getGraphics());
        } else if (action instanceof MouseDragAction) {
            MouseDragAction mouseDragAction = (MouseDragAction) action;
            return oldState.shallowCopy().setMouseDragPosition(mouseDragAction.getValue());
        } else if (action instanceof ForceFieldAction) {
            ForceFieldAction forceFieldAction = (ForceFieldAction) action;
            return oldState.shallowCopy()
                    .setPreForceFieldPosition(oldState.getForceFieldPosition())
                    .setForceFieldPosition(forceFieldAction.getValue());
        } else if (action instanceof BoundariesChangedAction) {
            return oldState.shallowCopy().setWidth(((BoundariesChangedAction) action).getWidth())
                    .setHeight(((BoundariesChangedAction) action).getHeight());
        } else if (action instanceof GenerateRandomParticlesInfoAction) {
            ParticlesState state = oldState.copy();
            setRandomTypes(state);
            return state;
        }
        return oldState;
    }

    /**
     * Sets random types on given state, helper.
     *
     * @param state the state
     */
    private void setRandomTypes(ParticlesState state) {
        Random random = new Random();
        state.setInfo(new ParticlesInfo(state.getColorCount()));
        for (int i = 0; i < state.getInfo().size(); i++) {
            state.getInfo().setColor(i, ColorManager.next().toString());

            for (int j = 0; j < state.getInfo().size(); j++) {
                //set attractions
                if (state.isAttractionStandard()) {
                    state.getInfo().setAttraction(i, j, state.isNegateSelfAttraction() && i == j ?
                            -Math.abs(state.getAttractionMean() + (state.getAttractionStd() * random.nextGaussian())) :
                            (state.getAttractionMean() + (state.getAttractionStd() * random.nextGaussian())));
                } else {
                    state.getInfo().setAttraction(i, j, state.isNegateSelfAttraction() && i == j ?
                            -Math.abs(state.getAttractionMin() + (state.getAttractionMax() - state.getAttractionMin()) *
                                    random.nextDouble()) :
                            (state.getAttractionMin() + (state.getAttractionMax() - state.getAttractionMin()) *
                                    random.nextDouble()));
                }
                //set maxRs
                if (state.isMaxRStandard()) {
                    state.getInfo().setMaxDistance(i, j,
                            (state.getMaxRMean() + (state.getMaxRStd() * random.nextGaussian())));
                } else {
                    state.getInfo().setMaxDistance(i, j,
                            (state.getMaxRLower() + (state.getMaxRUpper() - state.getMaxRLower()) *
                                    random.nextDouble()));
                }
                //set minRs
                if (state.isMinRStandard()) {
                    state.getInfo().setMinDistance(i, j,
                            (state.getMinRMean() + (state.getMinRStd() * random.nextGaussian())));
                } else {
                    state.getInfo().setMinDistance(i, j,
                            (state.getMinRLower() + (state.getMinRUpper() - state.getMinRLower()) *
                                    random.nextDouble()));
                }
            }
        }
        //if reciprocal, adjust..
        if(state.isReciprocalR() || state.isReciprocalAttraction()){
            int size = state.getInfo().size();
            double temp;
            for (int i = 0; i < size; i++) {
                for(int j = 0; j < size; j++){
                    if(state.isReciprocalR()){
                        temp = state.getInfo().getMinDistance(i, j);
                        state.getInfo().setMinDistance(j, i, temp);
                        temp = state.getInfo().getMaxDistance(i, j);
                        state.getInfo().setMaxDistance(j, i, temp);
                    }
                    if(state.isReciprocalAttraction()){
                        temp = state.getInfo().getAttraction(i, j);
                        state.getInfo().setAttraction(j, i, temp);
                    }
                }
            }
        }
    }
}



























