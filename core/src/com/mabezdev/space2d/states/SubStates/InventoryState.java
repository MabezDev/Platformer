package com.mabezdev.space2d.states.SubStates;

import com.mabezdev.space2d.Variables;
import com.mabezdev.space2d.managers.GameStateManager;
import com.mabezdev.space2d.util.Log;

/**
 * Created by Mabez on 18/12/2015.
 */
public class InventoryState extends BaseSubState {

    private String[] options = {"Yay","MySword"};
    private int index;

    public InventoryState(GameStateManager gsm) {
        super(gsm);
        Log.print("Inventory Screen!");
        index = 0;

    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void render() {
        batch.begin();
        {
            displayMenu(options,index, Variables.WIDTH/2,Variables.HEIGHT/2);
        }
        batch.end();
    }

    @Override
    public void handleInput() {
    }

    @Override
    public void dispose() {
    }
}
