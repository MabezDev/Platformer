package com.mabezdev.space2d.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mabezdev.space2d.Variables;
import com.mabezdev.space2d.managers.ResourceManager;
import com.mabezdev.space2d.states.PlayState;
import com.mabezdev.space2d.tiles.Tile;
import com.mabezdev.space2d.util.Log;

/**
 * Created by Mabez on 14/12/2015.
 */
public class Player extends Entity {

    private TextureRegion playerImage;
    private boolean Action;
    private float dt;
    private Tile currentTile;
    private float tempX;
    private float tempY;


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

        tempX = x;
        tempY = y;

        tempX += dx * dt;
        tempY += dy * dt;

        //player and tile now smooth but ugs need to fixed like that fact it looks like you are walking on the tile when ur not
        Tile next = null;
        Tile nextY = null;
        int currentRow = PlayState.getRowOfEntity(this);
        int currentColumn = PlayState.getColumnOfEntity(this);
        //check tile to the left
        if(tempX > (currentTile.getX() + Variables.TILEWIDTH/2)){
            if(currentColumn < Variables.WORLD_COLUMNS - 1) {
                next = PlayState.getTile(currentRow, currentColumn + 1);
            }
        } else if(tempX < (currentTile.getX() - Variables.TILEWIDTH/2 + ENTITY_WIDTH/2) ){
            if(currentColumn > 0) {
                next = PlayState.getTile(currentRow, currentColumn - 1);
            }
        } else {
            x = tempX;
        }

        if(next!=null){
            if(next.isSolid()){
                //don't move
            } else {
                Log.print("Updating x");
                x = tempX;
            }
        }

        if(tempY > (currentTile.getY() + Variables.TILEWIDTH/2)) {
            if(currentRow < Variables.WORLD_ROWS - 1){
                nextY = PlayState.getTile(currentRow + 1,currentColumn);
            }
        } else if(tempY < currentTile.getY()){
                if(currentRow > 0){
                    nextY = PlayState.getTile(currentRow - 1,currentColumn);
                }
        } else {
            y = tempY;
        }

        if(nextY!=null){
            if(nextY.isSolid()){
                //don't move
            } else {
                Log.print("Updating y");
                y = tempY;
            }
        }



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

        //decelerate player with drag
        handleRetardation();
    }

    public void setCurrentTile(Tile t){
        currentTile = t;
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
