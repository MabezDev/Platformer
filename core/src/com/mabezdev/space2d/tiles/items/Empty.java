package com.mabezdev.space2d.tiles.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mabezdev.space2d.Variables;

/**
 * Created by Mabez on 19/12/2015.
 */
public class Empty extends Item {

    @Override
    public void update(float dt) {

    }

    @Override
    public void doAction() {

    }

    public Empty(float x, float y, int itemID, TextureRegion image) {
        super(x, y, itemID,image);
    }

    @Override
    public String getItemType(){
        return Variables.ITEM;
    }

}
