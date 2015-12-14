package com.mabezdev.space2d.managers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mabezdev.space2d.states.BaseState;
import com.mabezdev.space2d.states.MenuState;

/**
 * Created by Mabez on 14/12/2015.
 */
public class GameStateManager {

    public enum State {
        MENU,
        PLAY,
        GAMEOVER
    }

    public State currentState;
    private BaseState state;
    private ResourceManager myResourceManager = new ResourceManager();
    protected OrthographicCamera camera;

    public GameStateManager(OrthographicCamera camera) {
        this.camera = camera;

        ResourceManager.loadFont("defaultFont",36,"orangejuice2.ttf");



        setCurrentState(State.MENU);

    }

    public void setCurrentState(State s) {
        this.currentState = s;
        switch ((this.currentState)) {
            case MENU:
                state = new MenuState(this);
                break;
            case PLAY:
                break;
            case GAMEOVER:
                break;
        }
    }

    public State getCurrentState() {
        return currentState;
    }

    public void update(float dt) {
        state.update(dt);
    }

    public void render() {
        state.render();
    }
}
