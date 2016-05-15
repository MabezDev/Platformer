package com.mabezdev.space2d.tiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mabezdev.space2d.managers.ResourceManager;

/**
 * Created by Mabez on 23/03/16.
 */
public class Sky extends Tile {


    public Sky(float x, float y, float width, float height, int ID){
        this.x = x;
        this.y = y;
        this.TILEWIDTH = width;
        this.TILEHEIGHT = height;
        this.ID = ID;
        //Create this asset on desktop - Could be temp black
        //this.TileImage = new TextureRegion(ResourceManager.getTexture("tileset"),64,0,32,32);

    }
    @Override
    public void render(SpriteBatch sb) {
        /*sb.begin();
        {
            //sb.draw();
        }
        sb.end();*/
    }

    @Override
    public void update(float dt) {

    }
}
