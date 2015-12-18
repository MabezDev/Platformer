package com.mabezdev.space2d.tiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mabezdev.space2d.managers.ResourceManager;

/**
 * Created by Mabez on 17/12/2015.
 */
public class StoneTile extends Tile {

    public StoneTile(float x, float y,float width,float height,int ID){
        this.x = x;
        this.y = y;
        this.ID = ID;
        this.TILEWIDTH = width;
        this.TILEHEIGHT = height;
        TileImage = new TextureRegion(ResourceManager.getTexture("tileset"),32,0,32,32);
        setSolid(true);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.draw(TileImage,x,y);
    }

}
