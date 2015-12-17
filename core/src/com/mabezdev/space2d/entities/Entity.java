package com.mabezdev.space2d.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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

    protected float dx;
    protected float dy;

    public enum Direction{
        UP,
        LEFT,
        RIGHT,
        DOWN
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



    public abstract void render(SpriteBatch sb);

    public abstract void update(float dt);
}
