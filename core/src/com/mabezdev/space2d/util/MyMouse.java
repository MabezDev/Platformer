package com.mabezdev.space2d.util;

/**
 * Created by Mabez on 24/12/2015.
 */
public class MyMouse {

    private static final int NUM_KEYS = 2;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    private static boolean[] buttons = new boolean[NUM_KEYS];
    private static boolean[] pbuttons = new boolean[NUM_KEYS];

    public static void update(){
        for(int i =0;i<NUM_KEYS;i++){
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
}
