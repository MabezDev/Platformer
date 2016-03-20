package com.mabezdev.space2d.managers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mabezdev.space2d.states.BaseState;
import com.mabezdev.space2d.states.MenuState;
import com.mabezdev.space2d.states.OptionsState;
import com.mabezdev.space2d.states.PlayState;
import com.mabezdev.space2d.states.SubStates.BaseSubState;
import com.mabezdev.space2d.states.SubStates.InventoryState;
import com.mabezdev.space2d.states.SubStates.Paused;
import com.mabezdev.space2d.util.Log;
import com.mabezdev.space2d.world.InventoryManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mabez on 14/12/2015.
 */
public class GameStateManager {

    public enum State {
        MENU,
        PLAY,
        GAMEOVER,
        OPTIONS
    }

    public enum SubState {
        PAUSED,
        INVENTORY,
        CHEST,
        NONE
    }

    private State currentState;
    private BaseState state;

    private SubState currentSubState;
    protected OrthographicCamera camera;
    private HashMap<Integer,BaseSubState> subStates;

    public GameStateManager(OrthographicCamera camera) {
        this.camera = camera;
        subStates = new HashMap<Integer, BaseSubState>();
        ResourceManager.loadFont("defaultFont",36,"orangejuice2.ttf");
        setCurrentState(State.MENU);
        setCurrentSubState(SubState.NONE);
    }

    public OrthographicCamera getCamera(){
        return camera;
    }

    public void setCurrentState(State s) {
        this.currentState = s;
        if(state!=null){
            state.dispose();
        }
        switch ((this.currentState)) {
            case MENU:
                state = new MenuState(this);
                break;
            case PLAY:
                state = new PlayState(this);
                break;
            case OPTIONS:
                state = new OptionsState(this);
                break;
            case GAMEOVER:
                break;
        }
        System.out.println("Current State: "+state.stateName);
    }

    public State getCurrentState() {
        return currentState;
    }

    public void addSubState(BaseSubState stateObject,int id){
        subStates.put(id,stateObject);
    }

    public int numberOfSubStates(){
        return subStates.size();
    }

    public void removeSubState(int id){
        subStates.get(id).dispose();
        subStates.remove(id);
    }

    public BaseSubState getSubState(int id){
        return subStates.get(id);
    }

    public boolean isActive(int idToCheck){
        if(subStates.containsKey(idToCheck)){
            return true;
        }
        return false;
    }

    public void setCurrentSubState(SubState s){
        currentSubState = s;
    }

    public SubState getCurrentSubState(){
        return currentSubState;
    }

    public void removeAllSubStates(){
        for (int id : subStates.keySet()) {
            subStates.get(id).dispose();
        }
        setCurrentSubState(SubState.NONE);
        subStates.clear();
    }



    public void update(float dt) {
        state.update(dt);
        state.handleInput();
        for (int id : subStates.keySet()) {
           subStates.get(id).update(dt);
           subStates.get(id).handleInput();
        }
    }

    public void render() {
        state.render();
        for (int id : subStates.keySet()) {
            subStates.get(id).render();

        }
    }

    public void dispose(){
        state.dispose();
        for (int id : subStates.keySet()) {
            subStates.get(id).dispose();
        }
    }
}
