package com.mabezdev.space2d.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.mabezdev.space2d.states.PlayState;

/**
 * Created by Mabez on 24/12/2015.
 */
public class MyMouse {

    private static final int NUM_KEYS = 4;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int MWHEEL_UP = 3;
    public static final int MWHEEL_DOWN = 2;
    private static boolean[] buttons = new boolean[NUM_KEYS];
    private static boolean[] pbuttons = new boolean[NUM_KEYS];

    public static void update(){
        for(int i =0;i<NUM_KEYS;i++){
            Gdx.input.getInputProcessor().scrolled(0); //reset scroll wheel
            pbuttons[i] = buttons[i];
        }
    }

    public static void setKeyState(int k,boolean b){
        buttons[k]= b;
    }


    public static boolean isDown(int k){
        return buttons[k];

    }
    public static boolean isPressed(int k){
        return buttons[k] && !pbuttons[k];
    }

    public static Vector3 getMouse(){
        return PlayState.getGSM().getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
    }
}
