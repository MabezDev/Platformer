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

    private Tile currentTile;
    private float tempX;
    private float tempY;


    protected float dx;
    protected float dy;

    public enum Direction{
        UP,
        LEFT,
        RIGHT,
        DOWN
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
        //might need to add thi code to Entity calss so every entity can handle collision
        tempX = x;
        tempY = y;

        tempX += dx * dt;
        tempY += dy * dt;

        //player and tile now smooth but ugs need to fixed like that fact it looks like you are walking on the tile when ur not
        Tile next = null;
        Tile nextY = null;

        int currentRow = PlayState.getRowOfEntity(this);
        int currentColumn = PlayState.getColumnOfEntity(this);

        boolean up = false;
        boolean down = false;
        boolean right = false;
        boolean left = false;
        boolean blocking = false;

        /*
            This collision detection algorithm checks a the 8 Tiles around the player  to see if moving into the next tile will be solid
            if it is solids then don't update the players position.
            Small diagram, P = Player, # = Tiles being checked.
            # # #
            # P #
            # # #
         */

        //check tile to the right
        if((tempX + ENTITY_WIDTH/2) > (currentTile.getX() + currentTile.getTileWidth())){
            if(currentColumn < Variables.WORLD_COLUMNS - 1) {
                next = PlayState.getTile(currentRow, currentColumn + 1);
                right = true;
                blocking = true;
            }
            //check left
        } else if(tempX - ENTITY_WIDTH/2 < ((currentTile.getX() - currentTile.getTileWidth()))){
            if(currentColumn > 0) {
                next = PlayState.getTile(currentRow, currentColumn - 1);
                left = true;
                blocking = true;
            }
        } else {
            x = tempX;
        }
        //check tile above
        if(tempY + ENTITY_HEIGHT/2 > (currentTile.getY() + currentTile.getTileHeight())) {
            if(currentRow < Variables.WORLD_ROWS - 1){
                nextY = PlayState.getTile(currentRow + 1,currentColumn);
                up = true;
                blocking = true;
            }
            //check tile below
        } else if(tempY - ENTITY_HEIGHT/2 < (currentTile.getY() - currentTile.getTileHeight())){
            if(currentRow > 0){
                nextY = PlayState.getTile(currentRow - 1,currentColumn);
                down = true;
                blocking = true;
            }
        } else {
            y = tempY;
        }

        //make sure that we are not already colliding with up,down,left,or,right
        if(!blocking) {
            //check tile above and to the right
            if (up && right) {
                if (currentColumn < Variables.WORLD_COLUMNS - 1 && currentRow < Variables.WORLD_ROWS - 1) {
                    next = PlayState.getTile(currentRow + 1, currentColumn + 1);
                }
                //check top left tile
            } else if (up && left) {
                if (currentColumn > 0 && currentRow < Variables.WORLD_ROWS - 1) {
                    next = PlayState.getTile(currentRow + 1, currentColumn - 1);
                }
                //check bottom right tile
            } else if (down && right) {
                if (currentColumn < Variables.WORLD_COLUMNS - 1 && currentRow > 0) {
                    next = PlayState.getTile(currentRow - 1, currentColumn + 1);
                }
                //check bottom left tile
            } else if (down && left) {
                if (currentColumn > 0 && currentRow > 0) {
                    next = PlayState.getTile(currentRow - 1, currentColumn - 1);
                }
            }
        }
        if(next!=null){
            if(next.isSolid()){
                //don't move
                if(tempX > x){
                    Log.print("BLOCKING RIGHT");
                } else if(tempX < x){
                    Log.print("BLOCKING LEFT");
                }
            } else {
                x = tempX;
            }
        }
        if(nextY!=null){
            if(nextY.isSolid()){
                //don't move
                if(tempY > y){
                    Log.print("BLOCKING UP");
                } else if(tempY < y){
                    Log.print("BLOCKING DOWN");
                }
            } else {
                y = tempY;
            }
        }
    }



    public abstract void render(SpriteBatch sb);

    public abstract void update(float dt);
}
