package com.mabezdev.space2d.states.SubStates;

import com.mabezdev.space2d.managers.GameStateManager;
import com.mabezdev.space2d.states.BaseState;

/**
 * Created by Mabez on 15/12/2015.
 */
public abstract class BaseSubState extends BaseState{

    //need x and y as it wont fill the screen as its a sub state
    protected float x;
    protected float y;
    protected float WIDTH;
    protected float HEIGHT;

    public BaseSubState(GameStateManager gsm) {
        super(gsm);
    }

}
