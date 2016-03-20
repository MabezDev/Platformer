package com.mabezdev.space2d.tiles.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Mabez on 19/12/2015.
 */
public class Sword extends Weapon {

    public Sword(float x, float y, int itemID, TextureRegion image, float attackTime, int damage) {
        super(x, y, itemID,image, attackTime ,damage);
    }

    @Override
    public void doAction() {
        // not sure this will be useful but keep it here for now
    }

    @Override
    public void update(float dt) {

    }
}
