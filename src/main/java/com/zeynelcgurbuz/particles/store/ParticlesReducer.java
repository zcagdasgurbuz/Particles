package com.zeynelcgurbuz.particles.store;

import com.zeynelcgurbuz.particles.ColorManager;
import com.zeynelcgurbuz.particles.Vector;
import com.zeynelcgurbuz.particles.redux.Action;
import com.zeynelcgurbuz.particles.redux.Reducer;

import java.util.Arrays;
import java.util.Random;

public class ParticlesReducer implements Reducer<ParticlesState> {
    @Override
    public ParticlesState reduce(ParticlesState oldState, Action action) {
        if (action instanceof MouseDragAction) {
            MouseDragAction mouseDragAction = (MouseDragAction) action;
            return cloneState(oldState).setMouseDragPosition(mouseDragAction.getValue());
        } else if (action instanceof  MouseDragStopAction){
            return cloneState(oldState).setMouseDragPosition(new Vector());
        } else if (action instanceof  GenerateRandomParticlesInfoAction){
            ParticlesState state = oldState.clone();
            setRandomTypes(state);
            return state;
        }

        return oldState;
    }


    private ParticlesState cloneState(ParticlesState oldState){
        return new ParticlesState(new Vector(oldState.getMouseDragPosition()));
    }

    private void setRandomTypes(ParticlesState state) {
        Random random = new Random();
        for (int i = 0; i < state.getInfo().size(); i++) {
            state.getInfo().setColor(i, ColorManager.next());

            for (int j = 0; j < state.getInfo().size(); j++) {
                int sign = 1;
                if(state.isNegateSelfAttraction() && i == j) sign = -1;
                //set attractions
                if(state.isAttractionStandard()) {
                    state.getInfo().setAttraction(i, j,
                            sign *
                                    (state.getAttractionMean() + (state.getAttractionStd() * random.nextGaussian())));
                } else {
                    state.getInfo().setAttraction(i, j,
                            (state.getAttractionMin() + (state.getAttractionMax() - state.getAttractionMin()) *
                                    random.nextDouble()));
                }
                //set maxRs
                if(state.isMaxRStandard()){
                    state.getInfo().setAttraction(i, j,
                            (state.getMaxRMean() +  (state.getMaxRStd() * random.nextGaussian())));
                } else {
                    state.getInfo().setMaxDistance(i, j,
                            (state.getMaxRLower() + (state.getMaxRUpper() - state.getMaxRLower()) *
                                    random.nextDouble()));
                }
                //set minRs
                if(state.isMinRStandard()) {
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
