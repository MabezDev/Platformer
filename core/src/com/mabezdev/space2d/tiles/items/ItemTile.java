package com.mabezdev.space2d.tiles.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mabezdev.space2d.tiles.Tile;

/**
 * Created by Mabez on 18/12/2015.
 */
public  abstract class ItemTile extends Tile {

    private int itemID;

    public ItemTile(float x, float y, int itemID){
        this.x = x;
        this.y = y;
        this.itemID = itemID;
    }

    public int getItemID() {
        return itemID;
    }


}
