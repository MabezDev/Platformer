package com.mabezdev.space2d.states.SubStates;


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
import com.mabezdev.space2d.world.InventoryManager;

/**
 * Created by Mabez on 18/12/2015.
 */
public class InventoryState extends BaseSubState{

    //this UI interacts with the player then sends the actions to the InventoryManager to actually update the content

    // temp need to set up a data structure to get my inventory, (inventory loader class needs to created(takes text input))
    private static final int gap = 1;
    private int[][] inventory;
    private TextureRegion[] loadedTextures;
    private Item[][] texturedInventory;
    private InventoryManager inventoryManager;
    private int ROWS;
    private int COLUMNS;
    private Texture itemSet;
    private TextureRegion frame;
    private TextureRegion selectorImage;
    private Player accessor;
    private float updateTime = 0;
    private float updateTimer = 1f;
    private Vector2 selector;
    private Vector2 index;
    //private Item selectedItem;

    public enum Items {
        EMPTY(0),
        SHOVEL(1),
        SWORD(2);

        private int itemID;

        Items(int itemID){
            this.itemID = itemID;
        }
    }

    public InventoryState(GameStateManager gsm,InventoryManager viewInv,float x, float y) {
        super(gsm);
        this.inventoryManager = viewInv;
        this.ROWS = inventoryManager.getRows();
        this.COLUMNS = inventoryManager.getColumns();
        this.WIDTH = GSManager.getCamera().viewportWidth;
        this.HEIGHT = GSManager.getCamera().viewportHeight;

        ResourceManager.loadTexture("inventory","tilesets/inventory.png");
        ResourceManager.loadTexture("selector","tilesets/selector.png");

        selector = new Vector2(17,82);
        index = new Vector2(4,0);
        itemSet = ResourceManager.getTexture("items");//load the item set in
        frame = new TextureRegion(ResourceManager.getTexture("inventory"));
        selectorImage = new TextureRegion(ResourceManager.getTexture("selector"));
        batch = PlayState.getSpriteBatch();

        this.accessor = PlayState.getPlayer();
        this.x = x;
        this.y = y;
        this.accessor.setCanMove(false);
        Log.print("Inventory Screen open!");
        loadItems();
        //need to make my own input processor as having one for each stops me from doing what i need
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown (int x, int y, int pointer, int button) {
                Vector3 test = new Vector3(x,y,0);
                Vector3 unprojected = PlayState.getGSM().getCamera().unproject(test);
                if(unprojected.x > getX() && unprojected.x < getX() + Variables.INVENTORY_WIDTH && unprojected.y > getY() && unprojected.y < getY() + Variables.INVENTORY_HEIGHT){
                    //make sure were clicking on the right window
                    Log.print("Touch down at "+unprojected.x+","+unprojected.y);
                    if (button == Input.Buttons.LEFT) {
                        if (PlayState.getSelectedItem() == null) {
                            //if no item selected then that mean our next click will be to get an item
                            PlayState.setSelectedItem(getItemOnClick());
                            inventoryManager.removeFromInventory(PlayState.getSelectedItem());
                        } else {
                            if (inventory[(int) index.x][(int) index.y] == 0) {
                                // adding to a blank space
                                inventoryManager.addToInventory(PlayState.getSelectedItem(), (int) index.x, (int) index.y);
                                PlayState.setSelectedItem(null);
                            } else {
                                // item swapping done here
                                Item toSwap = texturedInventory[(int) index.x][(int) index.y];
                                inventoryManager.addToInventory(PlayState.getSelectedItem(), (int) index.x, (int) index.y);
                                PlayState.setSelectedItem(toSwap);
                            }
                        }
                }

                    return true;
                }
                return false;
            }
        });
    }


    private Item[][] generateTextured(){
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

    private void loadItems(){
        int numOfItems = Items.values().length;
        TextureRegion[] temp = new TextureRegion[numOfItems];
        for(int i=0;i<temp.length;i++){
            temp[i] = new TextureRegion(itemSet,i* 32,0,32,32);//8,8 forces texture to that size, 32 is actual res of item
        }
        loadedTextures = temp;
    }



    @Override
    public void update(float dt) {
        if(updateTime > updateTimer){
            //save what this user has done
            inventoryManager.saveInventory();
            //load in any updates if any(from other users)
            inventoryManager.refreshData();
            updateTime = 0;
        } else {
            updateTime += dt;
        }
        inventory = inventoryManager.getInventory();//get the actual inventory
        texturedInventory = generateTextured();//generate the textures that the user sees
        updateCursor();

    }

    private void updateCursor(){
        // tad buggy needs fixing
        float mouseX = getMouse().x;
        float mouseY = getMouse().y;
        for(int i = 0; i < inventory.length;i++){
            for(int j =0;j < inventory[0].length;j++){
                //if current mouse x & y are in a item slot
                //render a highlight (black hollow box) around that shape also update the index
                float itemX = texturedInventory[i][j].getX();
                float itemY = texturedInventory[i][j].getY();
                if(mouseX > itemX && mouseX < (itemX + 8) && mouseY > itemY && mouseY < (itemY + 8)){
                    //draw selector at
                    selector = new Vector2(itemX,itemY);
                    index = new Vector2(i,j);
                }
            }

        }
    }

    private Vector3 getMouse(){
        return PlayState.getGSM().getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
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
            if(PlayState.getSelectedItem()==null) {
                batch.draw(selectorImage, selector.x, selector.y);
            } else {
                //selected item shall follow the mouse
                PlayState.getSelectedItem().setX(getMouse().x);
                PlayState.getSelectedItem().setY(getMouse().y);
                PlayState.getSelectedItem().render(batch);
            }



        }
        batch.end();
    }

    private Item getItemOnClick(){
        Log.print("Grabbing: " + texturedInventory[(int) index.x][(int) index.y].getItemID());
        return texturedInventory[(int) index.x][(int) index.y];
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void dispose() {
        if(PlayState.getSelectedItem()!=null){
            // if the item is never put in either player or back in the chest, the put it back in the chest
            //todo if the chest is full there will be a problem
            //inventoryManager.addToInventory(selectedItem);
        }
        //remove our custom listener
        Gdx.input.setInputProcessor(null);
        // set the player back to moving
        this.accessor.setCanMove(true);
        // save the changes to the file
        this.inventoryManager.saveInventory();
        Log.print("Inventory Screen closed!");
    }
}
