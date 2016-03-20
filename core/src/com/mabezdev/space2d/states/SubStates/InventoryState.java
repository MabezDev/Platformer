package com.mabezdev.space2d.states.SubStates;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mabezdev.space2d.Variables;
import com.mabezdev.space2d.entities.Player;
import com.mabezdev.space2d.managers.GameStateManager;
import com.mabezdev.space2d.managers.ResourceManager;
import com.mabezdev.space2d.states.PlayState;
import com.mabezdev.space2d.tiles.items.Empty;
import com.mabezdev.space2d.tiles.items.Item;
import com.mabezdev.space2d.tiles.items.Shovel;
import com.mabezdev.space2d.tiles.items.Sword;
import com.mabezdev.space2d.util.Log;
import com.mabezdev.space2d.util.MyMouse;
import com.mabezdev.space2d.world.InventoryManager;

/**
 * Created by Mabez on 18/12/2015.
 */
public class InventoryState extends BaseSubState{

    //this UI interacts with the player then sends the actions to the InventoryManager to actually update the content

    // temp need to set up a data structure to get my inventory, (inventory loader class needs to created(takes text input))
    protected static final int gap = 1;
    protected TextureRegion[] loadedTextures;
    protected Item[][] texturedInventory;
    protected InventoryManager inventoryManager;
    protected int ROWS;
    protected int COLUMNS;
    protected float FRAME_WIDTH;
    protected float FRAME_HEIGHT;
    protected Texture itemSet;
    protected TextureRegion frame;
    protected TextureRegion selectorImage;
    protected Player accessor;
    protected float updateTime = 0;
    protected float updateTimer = 1f;
    protected Vector2 selector;
    protected Vector2 index;
    protected boolean canAccessorMove;
    //private Item selectedItem;
    protected boolean dataSetHasChanged = true;

    public enum Items {
        EMPTY(0),
        SHOVEL(1),
        SWORD(2);

        private int itemID;

        Items(int itemID){
            this.itemID = itemID;
        }
    }

    public InventoryState(GameStateManager gsm,InventoryManager viewInv,float x, float y,boolean canMove,String inventoryPath) {
        super(gsm);
        this.inventoryManager = viewInv;
        this.ROWS = inventoryManager.getRows();
        this.COLUMNS = inventoryManager.getColumns();
        this.WIDTH = GSManager.getCamera().viewportWidth;
        this.HEIGHT = GSManager.getCamera().viewportHeight;
        this.FRAME_WIDTH = Variables.INVENTORY_WIDTH;
        this.FRAME_HEIGHT = Variables.INVENTORY_HEIGHT;
        ResourceManager.loadTexture("inventory",inventoryPath);
        ResourceManager.loadTexture("selector","tilesets/selector.png");

        selector = new Vector2(Integer.MAX_VALUE,Integer.MAX_VALUE);
        index = new Vector2(0,0);
        itemSet = ResourceManager.getTexture("items");//load the item set in
        frame = new TextureRegion(ResourceManager.getTexture("inventory"));
        selectorImage = new TextureRegion(ResourceManager.getTexture("selector"));
        batch = PlayState.getSpriteBatch();
        canAccessorMove = canMove;

        this.accessor = PlayState.getPlayer();
        this.x = x;
        this.y = y;
        this.accessor.setCanMove(canAccessorMove);
        Log.print("Inventory Screen open!");
        loadItems();
    }

    public InventoryState(GameStateManager gsm){
        super(gsm);
        itemSet = ResourceManager.getTexture("items");//load the item set in
        index = new Vector2(0,0);
        this.loadItems();
    }


    protected Item[][] generateTextured(int[][] inventory){ // should only be called once at the start
        //generate the textures from the IDS in inventory
        Item[][] temp = new Item[ROWS][COLUMNS];
        for(int i = 0;i < ROWS; i++){
            for(int j = 0;j < COLUMNS;j++){
                if(inventory[i][j] == Items.SHOVEL.itemID){
                    temp[i][j] = new Shovel((this.x  + gap) + (j* (Variables.ITEMTILEWIDTH + gap)), (this.y + gap) + (i*(Variables.ITEMTILEHEIGHT + gap)) ,Items.SHOVEL.itemID,loadedTextures[Items.SHOVEL.itemID]);
                }
                if(inventory[i][j] == Items.EMPTY.itemID){
                    temp[i][j] = new Empty((this.x  + gap) + (j* (Variables.ITEMTILEWIDTH + gap)), (this.y + gap) + (i*(Variables.ITEMTILEHEIGHT + gap)) ,Items.EMPTY.itemID,loadedTextures[Items.EMPTY.itemID]);
                }
                if(inventory[i][j] == Items.SWORD.itemID){
                    temp[i][j] = new Sword((this.x  + gap) + (j* (Variables.ITEMTILEWIDTH + gap) ), (this.y + gap) + (i*(Variables.ITEMTILEHEIGHT + gap)) ,Items.SWORD.itemID,loadedTextures[Items.SWORD.itemID]);
                }
                temp[i][j].setRow(i);
                temp[i][j].setColumn(j);
            }
        }
        return temp;
    }

    protected void loadItems(){
        int numOfItems = Items.values().length;
        TextureRegion[] temp = new TextureRegion[numOfItems];
        for(int i=0;i<temp.length;i++){
            temp[i] = new TextureRegion(itemSet,i* 32,0,32,32);//8,8 forces texture to that size, 32 is actual res of item
        }
        loadedTextures = temp;
    }

    public void notifyDataSetChange(){
        dataSetHasChanged = true;
    }


    @Override
    public void update(float dt) {

        if(dataSetHasChanged) {//only generate when we have to.
            inventoryManager.saveInventory();
            //todo : replace this with functions to add and remove single Item objects fromt he texturedinventory array
            //todo : generate should only be called on the initalization of this inventory state to populate the array
            texturedInventory = generateTextured(inventoryManager.getInventory());//generate the textures that the user sees
            inventoryManager.refreshData();
            dataSetHasChanged = false;
        }
        //make sure we only generate a new
        updateCursor();
        //update the position of the inventory screen if it can move
        if(canAccessorMove) {
            this.x = (PlayState.getGSM().getCamera().position.x - (Variables.INVENTORY_WIDTH / 2));
            this.y = PlayState.getGSM().getCamera().position.y - 60;
        }

    }

    protected void updateCursor(){
        float mouseX = MyMouse.getMouse().x;
        float mouseY = MyMouse.getMouse().y;
        for(int i = 0; i < texturedInventory.length;i++){
            for(int j =0;j < texturedInventory[0].length;j++){
                //if current mouse x & y are in a item slot
                //render a highlight (black hollow box) around that shape also update the index
                float itemX = texturedInventory[i][j].getX();
                float itemY = texturedInventory[i][j].getY();
                if(mouseX > itemX && mouseX < (itemX + 8) && mouseY > itemY && mouseY < (itemY + 8)) {
                    //Log.print(i+","+j);
                    //draw selector at
                    selector = new Vector2(itemX, itemY);
                    index = new Vector2(i, j);
                } else {
                    //keep the selector on the current item
                    selector.x = texturedInventory[(int)index.x][(int)index.y].getX();
                    selector.y = texturedInventory[(int)index.x][(int)index.y].getY();
                }
            }

        }
    }

    @Override
    public void render() {
        batch.begin();
        {
            batch.draw(frame,x,y);
            for(int i = 0;i < ROWS; i++){
                for(int j = 0;j < COLUMNS;j++){
                    texturedInventory[i][j].render(batch); // draw the textured inv
                }
            }
            drawSelector();



        }
        batch.end();
    }

    protected void drawSelector(){
        if(accessor.getSelectedItem()==null) {
            batch.draw(selectorImage, selector.x, selector.y);
        } else {
            //selected item shall follow the mouse
            accessor.getSelectedItem().setX(MyMouse.getMouse().x);
            accessor.getSelectedItem().setY(MyMouse.getMouse().y);
            accessor.getSelectedItem().render(batch);
        }
    }

    protected Item getItemOnClick(){
        Log.print("Grabbing: " + texturedInventory[(int) index.x][(int) index.y].getItemID());
        //we have changed the contents of the current UI
        return texturedInventory[(int) index.x][(int) index.y];
    }

    @Override
    public void handleInput() {
        handleMouse();
    }

    protected void handleMouse(){
        if(MyMouse.isPressed(MyMouse.LEFT)) {
            Log.print(MyMouse.getMouse().x + "," + MyMouse.getMouse().y);
            //don't remove items if we cannot put them somewhere
            if(PlayState.getGSM().numberOfSubStates() > 1 && !PlayState.getPlayer().getIsPaused()){
                if (MyMouse.getMouse().x > getX() && MyMouse.getMouse().x < (getX() + FRAME_WIDTH) && MyMouse.getMouse().y > getY() && MyMouse.getMouse().y < (getY() + FRAME_HEIGHT)) {
                    //make sure were clicking on the right window
                    notifyDataSetChange();
                    if (accessor.getSelectedItem() == null) {

                        //if no item selected then that mean our next click will be to get an item
                        int id = getItemOnClick().getItemID();
                        if (id != 0) {
                            accessor.setSelectedItem(getItemOnClick());
                            inventoryManager.removeFromInventory(accessor.getSelectedItem());
                        }
                    } else {

                        if (inventoryManager.getInventory()[(int) index.x][(int) index.y] == 0) {
                            // adding to a blank space
                            inventoryManager.addToInventory(accessor.getSelectedItem(), (int) index.x, (int) index.y);
                            accessor.setSelectedItem(null);
                        } else {
                            // item swapping done here
                            Item toSwap = texturedInventory[(int) index.x][(int) index.y];
                            inventoryManager.addToInventory(accessor.getSelectedItem(), (int) index.x, (int) index.y);
                            accessor.setSelectedItem(toSwap);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void dispose() {
        if(accessor.getSelectedItem()!=null){
            // if the item is never put in either player or back in the chest, the put it back in the chest
            //todo if the chest is full there will be a problem
            //inventoryManager.addToInventory(selectedItem);
        }
        // set the player back to moving
        if(canAccessorMove==false) {
            this.accessor.setCanMove(true);
        }
        // save the changes to the file
        this.inventoryManager.saveInventory();
        Log.print("Inventory Screen closed!");
    }
}
