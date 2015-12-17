package com.mabezdev.space2d.tiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Mabez on 15/12/2015.
 */
public abstract class Tile {

    private boolean isSolid = false;
    public static int ID;

    public abstract void render(SpriteBatch sb);


    //only the tile can decide whether is is solid or not,
    //might have to make this public for things like doors, where is can and cannot be solid
    protected void setSolid(boolean b){
        isSolid = b;
    }

    public boolean isSolid(){
        return isSolid;
    }

}
