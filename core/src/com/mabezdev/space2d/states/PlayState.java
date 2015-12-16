package com.mabezdev.space2d.states;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mabezdev.space2d.managers.GameStateManager;
import com.mabezdev.space2d.managers.ResourceManager;
import com.mabezdev.space2d.tiles.Tile;
import com.mabezdev.space2d.util.Log;
import com.mabezdev.space2d.world.Map;
import com.mabezdev.space2d.world.MapGenerator;

import java.io.IOException;

/**
 * Created by Mabez on 15/12/2015.
 */
public class PlayState extends BaseState {

    protected static final float unitScale = 1/32f;
    private Map world;
    private MapGenerator mapGenerator;
    private String worldFile = "world.txt";
    private SpriteBatch sb;
    private static int ROWS;
    private static int COLUMNS;
    private Tile[][] GRID;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        ResourceManager.loadTexture("tileset","tilesets/tileset.png");
        sb = new SpriteBatch();
        try {
            mapGenerator = new MapGenerator(worldFile);
            mapGenerator.generateFile();
            world = new Map(worldFile);
            GRID = world.getMap();
        }catch (IOException e){
            Log.print(e.toString());
        }
        ROWS = Map.getRows();
        COLUMNS = Map.getColumns();

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render() {
        // we have render the start of our map!
        sb.begin();
        {
            Log.print(COLUMNS);
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    GRID[i][j].render(sb);
                }
            }
        }
        sb.end();
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void dispose() {

    }
}
