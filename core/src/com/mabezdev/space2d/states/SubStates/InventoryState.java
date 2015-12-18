package com.mabezdev.space2d.states.SubStates;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mabezdev.space2d.Variables;
import com.mabezdev.space2d.managers.GameStateManager;
import com.mabezdev.space2d.util.Log;

import java.awt.*;

/**
 * Created by Mabez on 18/12/2015.
 */
public class InventoryState extends BaseSubState {

    // temp need to set up a data structure to get my inventory, (inventory loader class needs to created(takes text input))
    private int index;
    private ShapeRenderer sr;

    public InventoryState(GameStateManager gsm) {
        super(gsm);
        Log.print("Inventory Screen open!");
        index = 0;
        sr = new ShapeRenderer();

    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void render() {
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(Color.BLACK);
        sr.rect(40,50,Variables.WIDTH -80, Variables.HEIGHT -100);
        sr.end();
        batch.begin();
        {
            batch.setColor(Color.WHITE);
            menuFont.draw(batch,"Inventory",Variables.WIDTH/2, Variables.HEIGHT/2);
        }
        batch.end();
    }

    @Override
    public void handleInput() {
    }

    @Override
    public void dispose() {
        Log.print("Inventory Screen closed!");
    }
}
