package com.mabezdev.space2d.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mabezdev.space2d.Variables;
import com.mabezdev.space2d.managers.GameStateManager;
import com.mabezdev.space2d.managers.ResourceManager;
import com.mabezdev.space2d.states.PlayState;
import com.mabezdev.space2d.states.SubStates.BaseSubState;
import com.mabezdev.space2d.states.SubStates.InventoryState;
import com.mabezdev.space2d.states.SubStates.Paused;
import com.mabezdev.space2d.util.FileLoader;
import com.mabezdev.space2d.util.UniqueID;
import com.mabezdev.space2d.world.InventoryManager;

/**
 * Created by Mabez on 14/12/2015.
 */
public class Player extends Entity {

    private TextureRegion playerImage;
    private boolean Action;
    private boolean Inventory;
    private boolean canMove;
    private InventoryManager playerManager;
    private int inventoryID;
    private static final String inventoryFile = "myInventory.txt";
    private boolean isPaused;
    private int pausedID;


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
        playerManager = new InventoryManager(new FileLoader(inventoryFile));
        inventoryID = 20000;
        pausedID = 200000;
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

        if(isPaused) {
            if (PlayState.getGSM().getCurrentSubState() == GameStateManager.SubState.NONE) {
                pausedID = UniqueID.getIdentifier();
                PlayState.getGSM().addSubState(new Paused(PlayState.getGSM()),pausedID);
                PlayState.getGSM().setCurrentSubState(GameStateManager.SubState.PAUSED);
            }
        } else {
            if(PlayState.getGSM().getCurrentSubState()== GameStateManager.SubState.PAUSED){
                PlayState.getGSM().removeSubState(pausedID);
                PlayState.getGSM().setCurrentSubState(GameStateManager.SubState.NONE);
                pausedID = 200000;
            }
        }

        if(Inventory){
            if(PlayState.getGSM().getCurrentSubState()== GameStateManager.SubState.NONE){
                inventoryID = UniqueID.getIdentifier();
                PlayState.getGSM().addSubState(new InventoryState(PlayState.getGSM(),playerManager,(PlayState.getGSM().getCamera().position.x - (Variables.INVENTORY_WIDTH/2)),
                        (PlayState.getGSM().getCamera().position.y - (Variables.INVENTORY_HEIGHT/3))),inventoryID);
                PlayState.getGSM().setCurrentSubState(GameStateManager.SubState.INVENTORY);
            }
        } else {
            if(PlayState.getGSM().getCurrentSubState() == GameStateManager.SubState.INVENTORY){
                if(PlayState.getGSM().isActive(inventoryID)){
                    PlayState.getGSM().removeSubState(inventoryID);
                    PlayState.getGSM().setCurrentSubState(GameStateManager.SubState.NONE);
                    inventoryID = 20000;
                }
            }
        }
    }

    public InventoryManager getPlayerManager(){
        return playerManager;
    }

    public void setCanMove(boolean b){
        canMove = b;
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
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.I)){
                setInventory(!Inventory);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                isPaused = !isPaused;

        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            setAction(true);
        } else {
            setAction(false);
        }

    }



    public boolean getIsPaused(){
        return isPaused;
    }

    public void setIsPaused(boolean b){
        isPaused = b;
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
