package com.mabezdev.space2d.tiles.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mabezdev.space2d.Variables;

/**
 * Created by Mabez on 15/05/2016.
 */
public class UtilityItem extends Item {

    public UtilityItem(float x, float y, int itemID, TextureRegion tileImage) {
        super(x, y, itemID, tileImage);
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void doAction() {

    }

    @Override
    public String getItemType() {
        return Variables.ITEM_UTILITY;
    }
}
