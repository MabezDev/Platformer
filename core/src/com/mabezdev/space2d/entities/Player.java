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
import com.mabezdev.space2d.states.SubStates.HotBarState;
import com.mabezdev.space2d.states.SubStates.InventoryState;
import com.mabezdev.space2d.states.SubStates.Paused;
import com.mabezdev.space2d.tiles.items.Item;
import com.mabezdev.space2d.util.FileLoader;
import com.mabezdev.space2d.util.MyMouse;
import com.mabezdev.space2d.util.UniqueID;
import com.mabezdev.space2d.world.InventoryManager;

/**
 * Created by Mabez on 14/12/2015.
 */
public class Player extends Players {

    private TextureRegion playerImage;
    private boolean Action;
    private boolean Inventory;
    private boolean canMove;
    private InventoryManager playerManager;
    private int inventoryID;
    private static final String inventoryFile = "myInventory.txt";
    private static final String barFile = "itemBar.txt";
    private boolean isPaused;
    private int pausedID;
    private InventoryManager bar;
    private int barID;
    private static Item selectedItem;
    private HotBarState hotBarState;
    private boolean canAttack;
    private boolean isAttacking;

    private float attackTimer = 0f;
    private float attackDuration = .3f;


    public Player(float x, float y,int health){
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
        canAttack = false;
        isAttacking = false;
        maxHealth = health;
        currentHealth = health;

        //eventually will be TextureRegion[] filled with animation states -> Animation manager that holdes the regions we just tell the manager
        //what stae were in and it will give up the regions to draw
        playerImage = new TextureRegion(ResourceManager.getTexture("player"),0,0,32,32);
        playerManager = new InventoryManager(new FileLoader(inventoryFile));
        bar = new InventoryManager(new FileLoader(barFile));
        hotBarState = new HotBarState(PlayState.getGSM(),bar);

        barID = 20000;
        inventoryID = 20000;
        pausedID = 200000;
    }

    public boolean isAttacking(){
        return isAttacking;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.draw(playerImage,x,y);
        if(hotBarState.getSelectedHotBarItem()!=null) {
            if(isAttacking){
                //make shift animation just rotates the weapon
                sb.draw(hotBarState.getSelectedHotBarItem().getTileImage(), x + 24, y, Variables.GAME_ITEM_SIZE, Variables.GAME_ITEM_SIZE,16,16,1,1,-45);
            } else {
                sb.draw(hotBarState.getSelectedHotBarItem().getTileImage(), x + 16, y + 16, Variables.GAME_ITEM_SIZE, Variables.GAME_ITEM_SIZE);
            }
        }

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

        if(isAttacking){
            if(attackTimer > attackDuration){
                attackTimer = 0;
                isAttacking = false;
            } else {
                attackTimer += dt;
            }
        }

        if(hotBarState.getSelectedHotBarItem()!=null){
            canAttack = true;
            attackDuration = hotBarState.getSelectedHotBarItem().getAttackTime();
        } else {
            canAttack = false;
            attackDuration = 1.0f;
        }

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
                        (PlayState.getGSM().getCamera().position.y - (Variables.INVENTORY_HEIGHT/3)),false,"tilesets/inventory.png"),inventoryID);
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
        if(!PlayState.getGSM().isActive(barID)) {
            barID = UniqueID.getIdentifier();
            //PlayState.getGSM().addSubState(new InventoryState(PlayState.getGSM(),bar,(PlayState.getGSM().getCamera().position.x - (Variables.INVENTORY_WIDTH / 2)),
            // PlayState.getGSM().getCamera().position.y - 60,true,"tilesets/hotbar.png"),barID);
            PlayState.getGSM().addSubState(hotBarState,barID);
        }
    }

    public InventoryManager getPlayerManager(){
        return playerManager;
    }

    public void setCanMove(boolean b){
        canMove = b;
    }


    public static Item getSelectedItem() {
        return selectedItem;
    }

    public static void setSelectedItem(Item mySelectedItem) {
        selectedItem = mySelectedItem;
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

    private void attack(){
        isAttacking = true;
        //do code for hitting objects here
    }



    private void handleInput(){
        if(canAttack){
            if(MyMouse.isPressed(MyMouse.LEFT)){
                attack();
            }
        }
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
