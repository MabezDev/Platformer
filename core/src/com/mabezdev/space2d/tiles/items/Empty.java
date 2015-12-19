package com.mabezdev.space2d.tiles.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Mabez on 19/12/2015.
 */
public class Empty extends ItemTile {

    @Override
    public void render(SpriteBatch sb) {

    }

    public Empty(float x, float y, int itemID) {
        super(x, y, itemID);
    }

    @Override
    public int getItemID() {
        return super.getItemID();
    }
}
