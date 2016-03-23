package com.mabezdev.space2d.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mabezdev.space2d.Variables;
import com.mabezdev.space2d.states.PlayState;
import com.mabezdev.space2d.tiles.Tile;
import com.mabezdev.space2d.util.Log;

/**
 * Created by Mabez on 14/12/2015.
 */
public abstract class Entity {

    protected float x;
    protected float y;

    protected static float DEFAULT_SPEED;
    protected static float  DEFAULT_RETARDATION ;
    protected static float  DEFAULT_ACCELERATION;

    public static float  ENTITY_WIDTH;
    public static float  ENTITY_HEIGHT;
    public static String NAME;

    public static final String STATIC = "STATIC_ENTITY";
    public static final String NORMAL = "NORMAL_ENTITY";

    protected Tile currentTile;
    private float tempX;
    private float tempY;

    protected int currentHealth;
    protected int maxHealth;
    protected boolean isDead = false;

    protected float dx;
    protected float dy;

    public enum Direction{
        UP,
        LEFT,
        RIGHT,
        DOWN
    }

    public Tile getCurrentTile(){
        return currentTile;
    }

    public void removeHealth(int amount){
        currentHealth -= amount;
        if(currentHealth <= 0){
            isDead = true;
        }
    }

    public String getType(){
        return NORMAL;
    }


    public boolean isDead(){
        return isDead;
    }

    public float getDx(){
        return dx;
    }
    public float getDy(){
        return dy;
    }

    public Rectangle getBounds(){
        return new Rectangle(x,y, ENTITY_WIDTH,ENTITY_HEIGHT);
    }

    public void setCurrentTile(Tile t){
        currentTile = t;
    }


    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void move(Direction direction){

        switch (direction){
            case UP:
                if(dy <= DEFAULT_SPEED){
                    dy += DEFAULT_ACCELERATION;
                }
                dy = DEFAULT_SPEED;
                break;
            case DOWN:
                if(dy <= -DEFAULT_SPEED){
                    dy -= DEFAULT_ACCELERATION;
                }
                dy = -DEFAULT_SPEED;
                break;
            case RIGHT:
                if(dx <= DEFAULT_SPEED){
                    dx += DEFAULT_ACCELERATION;
                }
                dx = DEFAULT_SPEED;
                break;
            case LEFT:
                if(dx <= -DEFAULT_SPEED){
                    dx -= DEFAULT_ACCELERATION;
                }
                dx = -DEFAULT_SPEED;
                break;
            default:
                dx = 0;
                dy = 0;
                break;




        }
    }

    protected void handleMapBoundaries(float dt){
        //check map bounds an update player position. Very smooth, I might add
        if(x < 0){
            x += x*-1;
        }else if(x > Variables.WORLD_WIDTH  - this.ENTITY_WIDTH) {
            x -= dx * dt;
        }

        if(y < 0){
            y += y * -1;
        } else if(y > Variables.WORLD_HEIGHT - this.ENTITY_HEIGHT) {
            y -= dy * dt;
        }
    }

    protected void handleCollisions(float dt){
        tempX = x;
        tempY = y;

        tempX += dx * dt;
        tempY += dy * dt;

        /*
        Optimized the collision handling but it still doesn't look how I want it too.
         */

        Tile next = null;

        float xOffset = -ENTITY_WIDTH/2;
        float yOffset = -ENTITY_HEIGHT/2;

        next = PlayState.getTileFromCoordinates(tempX + xOffset,tempY +yOffset);

        if(next.isSolid()){
            //Don't update the position if it is invalid to do so.
        } else {
            y = tempY;
            x = tempX;
        }

    }
    public abstract void render(SpriteBatch sb);

    public abstract void update(float dt);
}
