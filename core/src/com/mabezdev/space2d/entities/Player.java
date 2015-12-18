package com.mabezdev.space2d.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mabezdev.space2d.Variables;
import com.mabezdev.space2d.managers.GameStateManager;
import com.mabezdev.space2d.managers.ResourceManager;
import com.mabezdev.space2d.states.PlayState;

/**
 * Created by Mabez on 14/12/2015.
 */
public class Player extends Entity {

    private TextureRegion playerImage;
    private boolean Action;
    private boolean Inventory;
    private boolean canMove;


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
        setInventory(false);
        canMove = true;
        //eventually will be TextureRegion[] filled with animation states
        playerImage = new TextureRegion(ResourceManager.getTexture("player"),0,0,32,32);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.draw(playerImage,x,y);
    }

    @Override
    public void update(float dt) {
        //collect user input
        this.handleInput();
        //handle collisions with solid tiles
        this.handleCollisions(dt);
        //handle map bounds
        this.handleMapBoundaries(dt);
        //decelerate player with drag
        this.handleRetardation();
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
        if(canMove) {
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                this.move(Direction.RIGHT);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                this.move(Direction.LEFT);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                this.move(Direction.UP);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                this.move(Direction.DOWN);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
                setAction(true);
            } else {
                setAction(false);
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.I)){
            setInventory(!Inventory);
            if(Inventory){
                PlayState.getGSM().setSubState(GameStateManager.SubState.INVENTORY);
                canMove = false;
            } else {
                PlayState.getGSM().setSubState(GameStateManager.SubState.NONE);
                canMove = true;
            }

        }
    }

    private void setAction(boolean b){
        Action = b;
    }

    private void setInventory(boolean b){
        Inventory = b;
    }

    public boolean getAction(){
        return Action;
    }
}
