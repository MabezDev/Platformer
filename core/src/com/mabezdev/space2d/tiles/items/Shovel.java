package com.mabezdev.space2d.tiles.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Mabez on 19/12/2015.
 */
public class Shovel extends Item {

    public Shovel(float x, float y, int itemID, TextureRegion image) {
        super(x, y, itemID,image);
    }

    @Override
    public void doAction() {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.draw(tileImage,x,y);
    }
}
