package com.mabezdev.space2d.tiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mabezdev.space2d.Variables;
import com.mabezdev.space2d.managers.ResourceManager;

/**
 * Created by Mabez on 16/12/2015.
 */
public class DirtTile extends Tile {

    public DirtTile(float x, float y,float width,float height,int ID){
        this.ID = ID;
        this.x = x;
        this.y = y;
        this.TILEWIDTH = width;
        this.TILEHEIGHT = height;
        TileImage = new TextureRegion(ResourceManager.getTexture("tileset"),0,0, 32,32);
        //here
    }



    @Override
    public void render(SpriteBatch sb){
        sb.draw(TileImage,x,y);
    }

}
