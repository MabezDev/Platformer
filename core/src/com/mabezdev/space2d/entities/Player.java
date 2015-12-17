package com.mabezdev.space2d.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mabezdev.space2d.Variables;
import com.mabezdev.space2d.managers.ResourceManager;
import com.mabezdev.space2d.util.Log;

import javax.print.attribute.standard.MediaSize;

/**
 * Created by Mabez on 14/12/2015.
 */
public class Player extends Entity {

    private TextureRegion playerImage;
    private boolean Action;
    private float dt;


    public Player(float x, float y){
        setX(x);
        setY(y);
        NAME = "PLAYER";
        DEFAULT_ACCELERATION = 5f;
        DEFAULT_RETARDATION = 3f;
        DEFAULT_SPEED = 30f;
        ENTITY_HEIGHT = 32;
        ENTITY_WIDTH = 32;
        setAction(false);

        //eventually will be TextureRegion[] filled with animation states
        playerImage = new TextureRegion(ResourceManager.getTexture("player"),0,0,32,32);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.draw(playerImage,x,y);
    }

    @Override
    public void update(float dt) {
        this.dt = dt;
        //collect user input
        handleInput();


        y += dy * dt;
        x += dx * dt;

        //check map bounds an update player position. Very smooth, I might add
        /*if(x < 0){
            x += x*-1;
        }else if(x > Variables.WORLD_WIDTH  - this.ENTITY_WIDTH) {
            x -= dx * dt;
        }

        if(y < 0){
            y += y * -1;
        } else if(y > Variables.WORLD_HEIGHT - this.ENTITY_HEIGHT) {
            y -= dy * dt;
        }*/

        //decelerate player with drag
        handleRetardation();
    }

    public void handleCollision(float startx,float endx,float starty, float endy){
        float dt = this.dt;
        if(x < startx) {
            x -= (dx * dt) + dt;
        } else if( x < endx){
            x -= (dx * dt) - dt;
        }

        if(y < starty){
            y -= (dy * dt) + dt;
        } else if(y < endy){
            y -= (dy * dt) - dt;
        }
    }



    private void handleRetardation(){
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
        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            this.move(Direction.DOWN);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            setAction(true);
        }else{
            setAction(false);
        }
    }

    private void setAction(boolean b){
        Action = b;
    }

    public boolean getAction(){
        return Action;
    }
}
