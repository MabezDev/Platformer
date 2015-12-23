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

    public State currentState;
    private BaseState state;
    private SubState currentSubState;
    private BaseSubState subState;
    protected OrthographicCamera camera;

    public GameStateManager(OrthographicCamera camera) {
        this.camera = camera;
        ResourceManager.loadFont("defaultFont",36,"orangejuice2.ttf");
        setCurrentState(State.MENU);
        setSubState(SubState.NONE, null,null);

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

    public void setSubState(SubState s, Object params, int[] id){
        this.currentSubState = s;
        if(subState!=null){
            subState.dispose();
        }
        //check if already in inventory if are, then dont open new state

        switch (currentSubState){
            case INVENTORY:
                if(params!=null) {
                    subState = new InventoryState(this, (InventoryManager) params,id[0]);
                }
                break;
            case CHEST:
                if(params!=null) {
                    subState = new InventoryState(this, (InventoryManager) params,id[0]);
                }
                break;
            case PAUSED:
                subState = new Paused(this);
                break;
            case NONE:
                subState = null;
                break;
        }
    }

    public BaseSubState getSubStateObject(){
        return subState;
    }

    public SubState getCurrentSubState(){
        return currentSubState;
    }

    public void update(float dt) {
        state.update(dt);
        state.handleInput();
        if(!currentSubState.equals(SubState.NONE)){
            subState.update(dt);
            subState.handleInput();
        }
    }

    public void render() {
        state.render();
        if(!currentSubState.equals(SubState.NONE)){
            subState.render();
        }
    }

    public void dispose(){
        if(!(subState==null)){
            subState.dispose();
        }
        state.dispose();


    }
}
