package com.mabezdev.space2d.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mabezdev.space2d.managers.ResourceManager;
import com.mabezdev.space2d.util.Log;

import javax.print.attribute.standard.MediaSize;

/**
 * Created by Mabez on 14/12/2015.
 */
public class Player extends Entity {

    private TextureRegion playerImage;


    public Player(float x, float y){
        setX(x);
        setY(y);
        NAME = "PLAYER";
        DEFAULT_ACCELERATION = 5f;
        DEFAULT_RETARDATION = 3f;
        DEFAULT_SPEED = 30f;
        ENTITY_HEIGHT = 32;
        ENTITY_WIDTH = 32;

        //eventually will be TextureRegion[] filled with animation states
        playerImage = new TextureRegion(ResourceManager.getTexture("player"),0,0,32,32);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.draw(playerImage,x,y);
    }

    @Override
    public void update(float dt) {
        handleInput();

        //handle retardation
        if(dx !=0){
            if(dx > 0){
                dx -= DEFAULT_RETARDATION;
            }else {
                dx += DEFAULT_RETARDATION;
            }
        }
        if(dy !=0){
            if(dy > 0){
                dy -= DEFAULT_RETARDATION;
            }else {
                dy += DEFAULT_RETARDATION;
            }
        }

        x += dx * dt;
        y += dy * dt;
    }



    private void handleInput(){
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            this.move(Direction.RIGHT);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            this.move(Direction.LEFT);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            this.move(Direction.UP);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            this.move(Direction.DOWN);
        }

    }
}
