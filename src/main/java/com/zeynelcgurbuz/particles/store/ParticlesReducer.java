package com.zeynelcgurbuz.particles.store;

import com.zeynelcgurbuz.particles.ColorManager;
import com.zeynelcgurbuz.particles.ParticlesInfo;
import com.zeynelcgurbuz.particles.Vector;
import com.zeynelcgurbuz.particles.redux.Action;
import com.zeynelcgurbuz.particles.redux.Reducer;
import com.zeynelcgurbuz.particles.store.actions.AppCloseAction;
import com.zeynelcgurbuz.particles.store.actions.BoundariesChangedAction;
import com.zeynelcgurbuz.particles.store.actions.GenerateRandomParticlesInfoAction;
import com.zeynelcgurbuz.particles.store.actions.MouseDragAction;
import com.zeynelcgurbuz.particles.store.actions.MouseDragStopAction;
import com.zeynelcgurbuz.particles.store.actions.RestartAction;
import com.zeynelcgurbuz.particles.store.actions.RestartFulfilledAction;
import com.zeynelcgurbuz.particles.store.actions.SetGraphicsContextAction;
import com.zeynelcgurbuz.particles.store.actions.SetSaveOnCloseAction;
import com.zeynelcgurbuz.particles.store.actions.SetStateAction;

import java.util.Random;

public class ParticlesReducer implements Reducer<ParticlesState> {
    @Override
    public ParticlesState reduce(ParticlesState oldState, Action action) {
        if (action instanceof SetStateAction) {
            return ((SetStateAction) action).getState().setGraphics(oldState.getGraphics());
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
        } else if (action instanceof MouseDragStopAction) {
            return oldState.shallowCopy().setMouseDragPosition(new Vector());
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


    private void setRandomTypes(ParticlesState state) {
        Random random = new Random();
        state.setInfo(new ParticlesInfo(state.getColorCount()));
        for (int i = 0; i < state.getInfo().size(); i++) {
            state.getInfo().setColor(i, ColorManager.next().toString());

            for (int j = 0; j < state.getInfo().size(); j++) {
                int sign = 1;
                if (state.isNegateSelfAttraction() && i == j) sign = -1;
                //set attractions
                if (state.isAttractionStandard()) {
                    state.getInfo().setAttraction(i, j,
                            sign *
                                    (state.getAttractionMean() + (state.getAttractionStd() * random.nextGaussian())));
                } else {
                    state.getInfo().setAttraction(i, j,
                            (state.getAttractionMin() + (state.getAttractionMax() - state.getAttractionMin()) *
                                    random.nextDouble()));
                }
                //set maxRs
                if (state.isMaxRStandard()) {
                    state.getInfo().setAttraction(i, j,
                            (state.getMaxRMean() + (state.getMaxRStd() * random.nextGaussian())));
                } else {
                    state.getInfo().setMaxDistance(i, j,
                            (state.getMaxRLower() + (state.getMaxRUpper() - state.getMaxRLower()) *
                                    random.nextDouble()));
                }
                //set minRs
                if (state.isMinRStandard()) {
                    state.getInfo().setAttraction(i, j,
                            (state.getMinRMean() + (state.getMinRStd() * random.nextGaussian())));
                } else {
                    state.getInfo().setMinDistance(i, j,
                            (state.getMinRLower() + (state.getMinRUpper() - state.getMinRLower()) *
                                    random.nextDouble()));
                }
            }
        }
    }


}
