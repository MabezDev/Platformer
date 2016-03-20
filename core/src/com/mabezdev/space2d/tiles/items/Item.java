package com.mabezdev.space2d.tiles.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mabezdev.space2d.Variables;
import com.mabezdev.space2d.tiles.Tile;

/**
 * Created by Mabez on 18/12/2015.
 */
public  abstract class Item extends Tile {

    private int itemID;
    protected TextureRegion tileImage;
    protected static final float WIDTH = Variables.ITEMTILEWIDTH;
    protected static final float HEIGHT = Variables.ITEMTILEHEIGHT;
    protected int ROW;
    protected int COLUMN;

    protected int damage;
    protected int totalDurability;
    protected int currentDurability;
    protected boolean isBroken = false;

    public Item(float x, float y, int itemID , TextureRegion tileImage){
        this.x = x;
        this.y = y;
        this.itemID = itemID;
        this.tileImage = tileImage;
    }

    public int getItemID() {
        return itemID;
    }

    public void setRow(int row){
        this.ROW = row;
    }

    public void setColumn(int column){
        this.COLUMN = column;
    }

    public int getColumn(){
        return COLUMN;
    }

    public int getRow(){
        return ROW;
    }

    public int getDamage(){
        return damage;
    }

    public int getTotalDurability(){
        return totalDurability;
    }

    public int getCurrentDurability(){
        return currentDurability;
    }

    public void reduceDurability(int amount){
        currentDurability -= amount;
        if(currentDurability < 0){
            isBroken = true;
        }
    }

    public  TextureRegion getTileImage(){
        return tileImage;
    }

    @Override
    public void render(SpriteBatch sb){
        sb.draw(tileImage,x,y,WIDTH,HEIGHT);
    }


    public abstract void doAction();//used to give each item a specific function, i.e A Weapon can attack



}
