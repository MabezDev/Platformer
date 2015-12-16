package com.mabezdev.space2d.tiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Mabez on 15/12/2015.
 */
public abstract class Tile {

    public final int WIDTH = 32;
    public final int HEIGHT = 32;

    public abstract void render(SpriteBatch sb);


}
