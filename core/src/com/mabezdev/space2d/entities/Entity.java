package com.mabezdev.space2d.entities;

/**
 * Created by Mabez on 14/12/2015.
 */
public abstract class Entity {

    private float x;
    private float y;

    private float dx;
    private float dy;

    private float maxSpeed;


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
}
