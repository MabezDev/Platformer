package com.mabezdev.space2d.states.SubStates;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mabezdev.space2d.Variables;
import com.mabezdev.space2d.entities.Player;
import com.mabezdev.space2d.managers.GameStateManager;
import com.mabezdev.space2d.managers.ResourceManager;
import com.mabezdev.space2d.states.PlayState;
import com.mabezdev.space2d.tiles.items.Item;
import com.mabezdev.space2d.util.FileLoader;
import com.mabezdev.space2d.util.Log;
import com.mabezdev.space2d.world.InventoryManager;

/**
 * Created by Mabez on 24/12/2015.
 */
public class HotBarState extends BaseSubState {

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

    @Override
    public void update(float dt) {

    }

    @Override
    public void render() {

    }

    @Override
    public void handleInput() {

    }

    @Override
    public void dispose() {
        Log.print("Closed item hot bar!");
    }
}
