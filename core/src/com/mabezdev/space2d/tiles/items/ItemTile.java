package com.mabezdev.space2d.tiles.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mabezdev.space2d.tiles.Tile;

/**
 * Created by Mabez on 18/12/2015.
 */
public  abstract class ItemTile extends Tile {

    private int itemID;
    protected TextureRegion tileImage;

    public ItemTile(float x, float y, int itemID , TextureRegion tileImage){
        this.x = x;
        this.y = y;
        this.itemID = itemID;
        this.tileImage = tileImage;
    }

    public int getItemID() {
        return itemID;
    }


}
