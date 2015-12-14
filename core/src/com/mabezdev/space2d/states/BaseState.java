package com.mabezdev.space2d.states;

import com.mabezdev.space2d.managers.GameStateManager;

/**
 * Created by Mabez on 14/12/2015.
 */
public abstract class BaseState {

    public static String stateName;
    private GameStateManager GSManager;

    public BaseState(GameStateManager gsm){
        this.GSManager = gsm;
    }


    public abstract void update(float dt);
    public abstract void render();
    public abstract void handleInput();
    public abstract void dispose();


}
