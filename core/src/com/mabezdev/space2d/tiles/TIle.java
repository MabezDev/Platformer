package com.mabezdev.space2d.tiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mabezdev.space2d.Variables;

/**
 * Created by Mabez on 15/12/2015.
 */
public abstract class Tile {

    private boolean isSolid = false;
    public static int ID;
    protected float x;
    protected float y;
    protected TextureRegion TileImage;



    public abstract void render(SpriteBatch sb);

    public Rectangle getBounds(){
        return new Rectangle(x,y, Variables.TILEWIDTH,Variables.TILEHEIGHT);
    }


    //only the tile can decide whether is is solid or not,
    //might have to make this public for things like doors, where is can and cannot be solid
    protected void setSolid(boolean b){
        isSolid = b;
    }

    public boolean isSolid(){
        return isSolid;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public static int getID() {
        return ID;
    }

    public static void setID(int ID) {
        Tile.ID = ID;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
