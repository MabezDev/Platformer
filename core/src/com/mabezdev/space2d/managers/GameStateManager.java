package com.mabezdev.space2d.managers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mabezdev.space2d.states.BaseState;
import com.mabezdev.space2d.states.MenuState;
import com.mabezdev.space2d.states.OptionsState;
import com.mabezdev.space2d.states.PlayState;

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

    public State currentState;
    private BaseState state;
    protected OrthographicCamera camera;

    public GameStateManager(OrthographicCamera camera) {
        this.camera = camera;
        ResourceManager.loadFont("defaultFont",36,"orangejuice2.ttf");
        setCurrentState(State.MENU);

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

    public void update(float dt) {
        state.update(dt);
        state.handleInput();
    }

    public void render() {
        state.render();
    }
}
