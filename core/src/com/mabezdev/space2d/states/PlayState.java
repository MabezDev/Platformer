package com.mabezdev.space2d.states;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mabezdev.space2d.managers.GameStateManager;
import com.mabezdev.space2d.managers.ResourceManager;
import com.mabezdev.space2d.tiles.Tile;
import com.mabezdev.space2d.util.Log;
import com.mabezdev.space2d.world.MapLoader;
import com.mabezdev.space2d.world.MapGenerator;

import java.io.IOException;

/**
 * Created by Mabez on 15/12/2015.
 */
public class PlayState extends BaseState {

    protected static final float unitScale = 1/32f;
    private MapLoader mapLoaderLoader;
    private MapGenerator mapGenerator;
    private String worldFile = "world.txt";
    private SpriteBatch sb;
    private static int ROWS;
    private static int COLUMNS;
    private Tile[][] world;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        ResourceManager.loadTexture("tileset","tilesets/tileset.png");
        sb = new SpriteBatch();
        try {
            mapGenerator = new MapGenerator(worldFile);
            mapGenerator.generateFile();
            mapLoaderLoader = new MapLoader(worldFile);
            world = mapLoaderLoader.getMap();
        }catch (IOException e){
            Log.print(e.toString());
        }
        ROWS = MapLoader.getRows();
        COLUMNS = MapLoader.getColumns();

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render() {
        // we have render the start of our map!
        sb.begin();
        {
            //draw world
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    world[i][j].render(sb);
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
