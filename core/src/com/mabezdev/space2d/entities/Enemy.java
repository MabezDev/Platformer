package com.mabezdev.space2d.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mabezdev.space2d.managers.ResourceManager;
import com.mabezdev.space2d.states.PlayState;
import com.mabezdev.space2d.util.Log;

/**
 * Created by Mabez on 20/03/2016.
 */
public class Enemy extends Players {

    private TextureRegion image;

    public Enemy(float x, float y, int health) {
        this.currentHealth = health;
        this.maxHealth = health;
        setX(x);
        setY(y);

        ResourceManager.loadTexture("enemy","tilesets/playerset.png");

        image = new TextureRegion(ResourceManager.getTexture("enemy"),0,0,32,32);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.draw(image,x,y);
    }

    @Override
    public void update(float dt) {
        currentTile = PlayState.getTileFromBounds(getBounds());
    }
}
