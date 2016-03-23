package com.mabezdev.space2d.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mabezdev.space2d.managers.ResourceManager;
import com.mabezdev.space2d.states.PlayState;

/**
 * Created by Mabez on 20/03/2016.
 */
public class Enemy extends Players {

    private Texture image;

    public Enemy(float x, float y, int health) {
        this.currentHealth = health;
        this.maxHealth = health;
        this.x = x;
        this.y = y;

        ResourceManager.loadTexture("enemy","tilesets/playerset.png");

        image = ResourceManager.getTexture("enemy");
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.draw(image,x,y);
    }

    @Override
    public void update(float dt) {
        currentTile = PlayState.getTileFromCoordinates(x,y);
    }
}
