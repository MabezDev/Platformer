package com.mabezdev.space2d.tiles.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mabezdev.space2d.Variables;

/**
 * Created by Mabez on 19/12/2015.
 */
public class Shovel extends Weapon {

    public Shovel(float x, float y, int itemID, TextureRegion image, float attackTime, int damage) {
        super(x, y, itemID,image, attackTime ,damage);
    }

    @Override
    public void doAction() {

    }

    @Override
    public void update(float dt) {

    }
}
