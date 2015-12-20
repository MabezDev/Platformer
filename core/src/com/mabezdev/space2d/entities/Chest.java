package com.mabezdev.space2d.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mabezdev.space2d.Variables;
import com.mabezdev.space2d.managers.GameStateManager;
import com.mabezdev.space2d.managers.ResourceManager;
import com.mabezdev.space2d.states.PlayState;
import com.mabezdev.space2d.states.SubStates.BaseSubState;
import com.mabezdev.space2d.states.SubStates.InventoryState;
import com.mabezdev.space2d.util.FileLoader;
import com.mabezdev.space2d.util.Log;
import com.mabezdev.space2d.util.UniqueID;
import com.mabezdev.space2d.world.InventoryManager;

/**
 * Created by Mabez on 17/12/2015.
 */
public class Chest extends StaticEntity {

    private TextureRegion[] chestTextures;
    private TextureRegion currentTexture;
    private chestState currentState;
    private boolean interactedWith;
    private boolean isLocked;
    private boolean isOpen;
    private int inventoryID;

    public enum chestState{
        OPEN,
        CLOSED,
        LOCKED
    }

    public Chest(float x, float y,chestState initial){
        this.ENTITY_WIDTH = Variables.TILEWIDTH;
        this.ENTITY_HEIGHT = Variables.TILEHEIGHT;
        this.NAME = "CHEST";

        this.x = x;
        this.y = y;

        isOpen = false;
        isLocked = false;
        interactedWith = false;
        chestTextures = new TextureRegion[2];
        chestTextures[0] = new TextureRegion(ResourceManager.getTexture("interactive"),0,0,32,32);
        chestTextures[1] = new TextureRegion(ResourceManager.getTexture("interactive"),32,0,32,32);
        setState(initial);
        inventoryID = 20000;
    }



    @Override
    public void render(SpriteBatch sb) {
        sb.draw(currentTexture,x,y);
    }

    @Override
    public void doAction(){
        if(!isLocked) {
            isOpen = !isOpen;
            if (isOpen) {
                setState(chestState.OPEN);
            } else {
                setState(chestState.CLOSED);
            }
        } else {
            Log.print("This chest is locked!");
        }
    }

    @Override
    public void update(float dt) {
        if(currentState == chestState.OPEN){
            if(PlayState.getGSM().getCurrentSubState()== GameStateManager.SubState.NONE){
                int[] id = {UniqueID.getIdentifier()};
                inventoryID = id[0];
                PlayState.getGSM().setSubState(GameStateManager.SubState.CHEST,new InventoryManager(new FileLoader("chestInventory.txt")),id);
            }
        } else if(currentState == chestState.CLOSED) {
            if(PlayState.getGSM().getCurrentSubState() == GameStateManager.SubState.CHEST){
                if(PlayState.getGSM().getSubStateObject().getStateID() == inventoryID){
                    PlayState.getGSM().setSubState(GameStateManager.SubState.NONE,null, null);
                    inventoryID = 20000;
                }
            }
        }
    }

    public void setState(chestState b){
        currentState = b;
        switch (currentState){
            case OPEN:
                currentTexture = chestTextures[1];
                isOpen = true;
                break;
            case CLOSED:
                currentTexture = chestTextures[0];
                isOpen = false;
                break;
            case LOCKED:
                currentTexture = chestTextures[0];
                isLocked = true;
                break;

        }
    }

    public chestState getChestState(){
        return currentState;
    }
}
