package com.mabezdev.space2d.states.SubStates;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mabezdev.space2d.Variables;
import com.mabezdev.space2d.entities.Player;
import com.mabezdev.space2d.managers.GameStateManager;
import com.mabezdev.space2d.managers.ResourceManager;
import com.mabezdev.space2d.states.PlayState;
import com.mabezdev.space2d.tiles.items.Empty;
import com.mabezdev.space2d.tiles.items.Item;
import com.mabezdev.space2d.tiles.items.Shovel;
import com.mabezdev.space2d.tiles.items.Sword;
import com.mabezdev.space2d.util.FileLoader;
import com.mabezdev.space2d.util.Log;
import com.mabezdev.space2d.world.InventoryManager;

/**
 * Created by Mabez on 24/12/2015.
 */
public class HotBarState extends InventoryState {

    public HotBarState(GameStateManager gsm, InventoryManager mng) {
        super(gsm);
        Log.print("Opened item hot bar!");
        this.x = (PlayState.getGSM().getCamera().position.x - (Variables.INVENTORY_WIDTH / 2));
        this.y = 20;
        this.inventoryManager = mng;
        this.ROWS = inventoryManager.getRows();
        this.COLUMNS = inventoryManager.getColumns();
        this.WIDTH = GSManager.getCamera().viewportWidth;
        this.HEIGHT = GSManager.getCamera().viewportHeight;

        ResourceManager.loadTexture("inventory","tilesets/hotbar.png");
        ResourceManager.loadTexture("selector","tilesets/selector.png");

        selector = new Vector2(Integer.MAX_VALUE,Integer.MAX_VALUE);
        index = new Vector2(4,0);
        itemSet = ResourceManager.getTexture("items");//load the item set in
        frame = new TextureRegion(ResourceManager.getTexture("inventory"));
        selectorImage = new TextureRegion(ResourceManager.getTexture("selector"));
        batch = PlayState.getSpriteBatch();

        this.accessor = PlayState.getPlayer();
    }

    public void notifyDataSetHasChanged(){
        dataSetHasChanged = true;
    }

    @Override
    public void update(float dt) {
        if(dataSetHasChanged){
            Log.print("Generating");
            //refresh inventory
            inventoryManager.refreshData();
            //gen textures
            texturedInventory = generateTextured(inventoryManager.getInventory());
            Log.print(texturedInventory.length);
            //save inventory
            inventoryManager.saveInventory();
            //set set up for next notification
            dataSetHasChanged = false;
        }
        this.x = (PlayState.getGSM().getCamera().position.x - (Variables.INVENTORY_WIDTH / 2));
        this.y = (PlayState.getGSM().getCamera().position.y - (PlayState.getGSM().getCamera().viewportHeight/2 - 2));
        for(int i=0; i<ROWS;i++){
            for(int j=0; j < COLUMNS; j++){
                texturedInventory[i][j].setX((this.x  + gap) + (j* (Variables.ITEMTILEWIDTH + gap)));
                texturedInventory[i][j].setY((this.y + gap) + (i*(Variables.ITEMTILEHEIGHT + gap)));
            }
        }
    }

    @Override
    public void render() {
        batch.begin();
        {
            batch.draw(frame,x,y);
            for(int i=0; i<ROWS;i++){
                for(int j=0; j < COLUMNS; j++){
                    texturedInventory[i][j].render(batch);
                }
            }
        }
        batch.end();
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void dispose() {
        Log.print("Closed item hot bar!");
    }
}
