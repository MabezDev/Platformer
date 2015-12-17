package com.mabezdev.space2d.states;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.mabezdev.space2d.Variables;
import com.mabezdev.space2d.entities.Entity;
import com.mabezdev.space2d.entities.Player;
import com.mabezdev.space2d.managers.GameStateManager;
import com.mabezdev.space2d.managers.ResourceManager;
import com.mabezdev.space2d.tiles.Tile;
import com.mabezdev.space2d.util.Log;
import com.mabezdev.space2d.util.Maths;
import com.mabezdev.space2d.world.MapLoader;
import com.mabezdev.space2d.world.MapGenerator;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mabez on 15/12/2015.
 */
public class PlayState extends BaseState {

    protected static final float unitScale = 1/4f;
    private MapLoader mapLoaderLoader;
    private MapGenerator mapGenerator;
    private String worldFile = "world.txt";
    private SpriteBatch sb;
    private OrthographicCamera camera;
    private static int ROWS;
    private static int COLUMNS;
    private static float WORLD_WIDTH;
    private static float WORLD_HEIGHT;
    private Tile[][] world;
    private ArrayList<Entity> entities;
    private Player player;
    private static final float cameraLerp = 0.1f;


    public PlayState(GameStateManager gsm) {
        super(gsm);

        ResourceManager.loadTexture("tileset","tilesets/tilesets.png");
        ResourceManager.loadTexture("player","tilesets/playerset.png");

        sb = new SpriteBatch();
        camera = GSManager.getCamera();
        entities = new ArrayList<Entity>();
        player = new Player(0,0);
        //set to ortho to scale down the player view
        camera.setToOrtho(false, Variables.WIDTH*unitScale, Variables.HEIGHT*unitScale);
        //Get the map into memory!
        loadMap();
        WORLD_WIDTH = COLUMNS * Variables.TILEWIDTH;
        WORLD_HEIGHT = ROWS * Variables.TILEHEIGHT;

        Variables.WORLD_WIDTH = (WORLD_WIDTH);
        Variables.WORLD_HEIGHT = (WORLD_HEIGHT);
        entities.add(player);


    }

    private void loadMap(){
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
        for(int j=0; j < entities.size();j++){
            entities.get(j).update(dt);
        }
        //Log.print(getColumnOfEntity(player)+","+getRowOfEntity(player));
        //Log.print(world[getRowOfEntity(player)][getColumnOfEntity(player)].ID);
        updateCamera();
    }

    private int getColumnOfEntity(Entity e){
        float x =  e.getX() - e.ENTITY_WIDTH/2;
        int nearestX = (int)(Math.floor((x/Variables.TILEWIDTH))) + 1;
        return nearestX;
    }

    private int getRowOfEntity(Entity e){
        float y =  e.getY() - e.ENTITY_HEIGHT/2;
        int nearestY = (int) (Math.floor((y/Variables.TILEHEIGHT))) + 1;
        return nearestY;
    }

    private void updateCamera(){
        float newPosx = player.getX() + (player.ENTITY_WIDTH/2);
        float newPosy = player.getY() + (player.ENTITY_HEIGHT/2);

        float minCameraX = camera.zoom * (camera.viewportWidth / 2);
        float maxCameraX = WORLD_WIDTH - minCameraX;
        float minCameraY = camera.zoom * (camera.viewportHeight / 2);
        float maxCameraY = WORLD_HEIGHT - minCameraY;
        camera.position.set(Math.min(maxCameraX, Math.max(newPosx, minCameraX)),
                Math.min(maxCameraY, Math.max(newPosy, minCameraY)),
                0);
        camera.update();
    }

    @Override
    public void render() {
        // we have render the start of our map!
        sb.begin();
        //set the projection matrix to match the cameras!
        sb.setProjectionMatrix(camera.combined);
        {
            //draw all the tiles to make the world
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLUMNS; j++) {
                    world[i][j].render(sb);
                }
            }
            //draw entities next so they are on top of the world
            for(int j=0; j < entities.size();j++){
                entities.get(j).render(sb);
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
