package com.mabezdev.space2d.tiles.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Mabez on 20/03/2016.
 */
public class Weapon extends Item {

    protected int damage;
    protected int totalDurability;
    protected int currentDurability;
    protected boolean isBroken = false;
    protected float attackTime;

    public Weapon(float x, float y, int itemID, TextureRegion image, float attackTime, int damage) {
        super(x, y, itemID,image);
        this.attackTime = attackTime;
        this.damage = damage;
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

    public float getAttackTime(){
        return attackTime;
    }

    public void reduceDurability(int amount){
        currentDurability -= amount;
        if(currentDurability < 0){
            isBroken = true;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void doAction() {

    }
}
