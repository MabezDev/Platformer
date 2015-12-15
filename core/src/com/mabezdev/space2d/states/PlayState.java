package com.mabezdev.space2d.states;


import com.mabezdev.space2d.managers.GameStateManager;
import com.mabezdev.space2d.util.Log;
import com.mabezdev.space2d.world.Map;
import com.mabezdev.space2d.world.MapGenerator;

import java.io.IOException;

/**
 * Created by Mabez on 15/12/2015.
 */
public class PlayState extends BaseState {

    protected static final float unitScale = 1/32f;
    private Map world;
    private int[][] GRID;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        try {
            MapGenerator ez = new MapGenerator("text.txt");
            ez.generateFile();
            world = new Map("text.txt");
            GRID = world.getMap();
        }catch (IOException e){
            Log.print(e.toString());
            //Log.print("No file Found!");
        }
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render() {

    }

    @Override
    public void handleInput() {

    }

    @Override
    public void dispose() {

    }
}
