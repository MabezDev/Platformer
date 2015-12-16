package com.mabezdev.space2d.tiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mabezdev.space2d.managers.ResourceManager;

/**
 * Created by Mabez on 16/12/2015.
 */
public class DirtTile extends Tile {

    private TextureRegion dirt;
    private float x;
    private float y;

    public DirtTile(float x, float y){
        this.x = x;
        this.y = y;
        dirt = new TextureRegion(ResourceManager.getTexture("tileset"),0,0,32,32);
    }

    @Override
    public void render(SpriteBatch sb){
        sb.draw(dirt,x,y);
    }

}
