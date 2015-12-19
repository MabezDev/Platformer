package com.mabezdev.space2d.states.SubStates;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mabezdev.space2d.Variables;
import com.mabezdev.space2d.managers.GameStateManager;
import com.mabezdev.space2d.managers.ResourceManager;
import com.mabezdev.space2d.tiles.Tile;
import com.mabezdev.space2d.tiles.items.Empty;
import com.mabezdev.space2d.tiles.items.ItemTile;
import com.mabezdev.space2d.tiles.items.Shovel;
import com.mabezdev.space2d.tiles.items.Sword;
import com.mabezdev.space2d.util.Log;
import com.mabezdev.space2d.world.InventoryManager;

/**
 * Created by Mabez on 18/12/2015.
 */
public class InventoryState extends BaseSubState{

    //this UI interacts with the player then sends the actions to the InventoryManager to actually update the content

    // temp need to set up a data structure to get my inventory, (inventory loader class needs to created(takes text input))
    private static final int gap = 2;
    private ShapeRenderer sr;
    private int[][] inventory;
    private TextureRegion[] loadedTextures;
    private ItemTile[][] texturedInventory;
    private InventoryManager inventoryManager;
    private int ROWS;
    private int COLUMNS;
    private Texture itemSet;
    private float WIDTH;
    private float HEIGHT;

    public enum Items {
        EMPTY(0),
        SHOVEL(1),
        SWORD(2);

        private int itemID;

        Items(int itemID){
            this.itemID = itemID;
        }
    }

    public InventoryState(GameStateManager gsm,InventoryManager viewInv, float x, float y,float x2, float y2) {
        super(gsm);
        this.inventoryManager = viewInv;
        this.x = x;
        this.y = y;
        this.x2 = x2;
        this.y2 = y2;
        this.ROWS = inventoryManager.getRows();
        this.COLUMNS = inventoryManager.getColumns();
        this.WIDTH = x2 - x;
        this.HEIGHT = y2 - y;
        itemSet = ResourceManager.getTexture("items");//load the item set in
        Log.print("Inventory Screen open!");
        sr = new ShapeRenderer();
        loadItems();
    }

    private ItemTile[][] generateTextured(){
        //generate the textures from the IDS in inventory
        ItemTile[][] temp = new ItemTile[ROWS][COLUMNS];
        for(int i = 0;i < ROWS; i++){
            for(int j = 0;j < COLUMNS;j++){
                if(inventory[i][j] == Items.SHOVEL.itemID){
                    temp[i][j] = new Shovel(x + (j* (8  + gap) - 10), y + (i*(8 + gap)) ,Items.SHOVEL.itemID,loadedTextures[Items.SHOVEL.itemID]);
                }
                if(inventory[i][j] == Items.EMPTY.itemID){
                    temp[i][j] = new Empty(x + (j* (8  + gap) - 10), y + (i*(8 + gap)) ,Items.EMPTY.itemID,loadedTextures[Items.EMPTY.itemID]);
                }
                if(inventory[i][j] == Items.SWORD.itemID){
                    temp[i][j] = new Sword(x + (j* (8  + gap) - 10), y + (i*(8 + gap)) ,Items.SWORD.itemID,loadedTextures[Items.SWORD.itemID]);
                }
            }
        }
        return temp;
    }

    private void loadItems(){
        int numOfItems = Items.values().length;
        TextureRegion[] temp = new TextureRegion[numOfItems];
        for(int i=0;i<temp.length;i++){
            temp[i] = new TextureRegion(itemSet,i* 8,0,8,8);
        }
        loadedTextures = temp;
    }



    @Override
    public void update(float dt) {
        inventory = inventoryManager.getInventory();//get the actual inventory
        texturedInventory = generateTextured();//generate the textures that the user sees
    }

    @Override
    public void render() {
        batch.setProjectionMatrix(GSManager.getCamera().combined);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(Color.BLACK);
        sr.rect(x,y, x2, y2);
        sr.end();
        batch.begin();
        {
            for(int i = 0;i < ROWS; i++){
                for(int j = 0;j < COLUMNS;j++){
                    texturedInventory[i][j].render(batch); // draw the textured inv
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
        Log.print("Inventory Screen closed!");
        this.inventoryManager.saveInventory();
    }
}
