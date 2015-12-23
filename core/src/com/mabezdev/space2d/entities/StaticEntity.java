package com.mabezdev.space2d.entities;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mabezdev.space2d.tiles.Tile;

/**
 * Created by Mabez on 15/12/2015.
 */

public abstract class StaticEntity extends Entity {

    public abstract void doAction();

    @Override
    protected void handleCollisions(float dt){

    }

    @Override
    protected void handleMapBoundaries(float dt){


    }

    @Override
    public void move(Direction direction){

    }

    @Override
    public void setCurrentTile(Tile t){

    }




}
