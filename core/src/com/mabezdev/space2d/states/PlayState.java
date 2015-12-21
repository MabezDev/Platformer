package com.mabezdev.space2d.states;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mabezdev.space2d.Variables;
import com.mabezdev.space2d.entities.Chest;
import com.mabezdev.space2d.entities.Entity;
import com.mabezdev.space2d.entities.Player;
import com.mabezdev.space2d.entities.StaticEntity;
import com.mabezdev.space2d.managers.GameStateManager;
import com.mabezdev.space2d.managers.ResourceManager;
import com.mabezdev.space2d.tiles.Tile;
import com.mabezdev.space2d.util.FileLoader;
import com.mabezdev.space2d.util.Log;
import com.mabezdev.space2d.world.MapLoader;
import com.mabezdev.space2d.world.MapGenerator;
import org.ietf.jgss.GSSManager;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Mabez on 15/12/2015.
 */
public class PlayState extends BaseState {

    protected static final float unitScale = 1/4f;
    private MapLoader mapLoader;
    private MapGenerator mapGenerator;
    private String worldFile = "world.txt";
    private static SpriteBatch sb;
    private static OrthographicCamera camera;
    private static int ROWS;
    private static int COLUMNS;
    private static float WORLD_WIDTH;
    private static float WORLD_HEIGHT;
    private static Tile[][] world;
    private static ArrayList<Entity> entities;
    private static Player player;
    private static final float cameraLerp = 0.1f;


    public PlayState(GameStateManager gsm) {
        super(gsm);

        ResourceManager.loadTexture("tileset","tilesets/tilesets.png");
        ResourceManager.loadTexture("player","tilesets/playerset.png");
        ResourceManager.loadTexture("interactive","tilesets/interactives.png");
        ResourceManager.loadTexture("items","tilesets/items.png");

        sb = new SpriteBatch();
        camera = GSManager.getCamera();
        entities = new ArrayList<Entity>();
        player = new Player(0,0);
        //set to ortho to scale down the player view
        camera.setToOrtho(false, Variables.WIDTH*unitScale, Variables.HEIGHT*unitScale);
        //Get the map into memory!
        world = loadMap();
        WORLD_WIDTH = COLUMNS * Variables.TILEWIDTH;
        WORLD_HEIGHT = ROWS * Variables.TILEHEIGHT;

        Variables.GAME_CAMERA_VIEWPORT_HEIGHT = camera.viewportHeight;
        Variables.GAME_CAMERA_VIEWPORT_WIDTH = camera.viewportWidth;

        Variables.WORLD_WIDTH = (WORLD_WIDTH);
        Variables.WORLD_HEIGHT = (WORLD_HEIGHT);
        Variables.WORLD_ROWS = ROWS;
        Variables.WORLD_COLUMNS = COLUMNS;

        Chest myChest2 = new Chest(1*Variables.TILEWIDTH,1*Variables.TILEHEIGHT, Chest.chestState.CLOSED);
        entities.add(myChest2);
        entities.add(player);
    }

    public static SpriteBatch getSpriteBatch() {
        return sb;
    }

    public static Player getPlayer(){
        return player;
    }

    public static GameStateManager getGSM(){
        return GSManager;
    }

    private Tile[][] loadMap(){
        try {
            mapGenerator = new MapGenerator(worldFile);
            mapGenerator.generateFile();
        }catch (IOException e){
            Log.print(e.toString());
        }
        mapLoader = new MapLoader(new FileLoader(worldFile));
        ROWS = mapLoader.getRows();
        COLUMNS = mapLoader.getColumns();
        return mapLoader.getMap();
    }

    @Override
    public void update(float dt) {
        for(int j=0; j < entities.size();j++){
            //update all entities
            Entity e =  entities.get(j);
            e.update(dt);
            Tile currentTile = world[getRowOfEntity(player)][getColumnOfEntity(player)];
            player.setCurrentTile(currentTile);

            //make sure we are not checking player against itself
            if(!e.equals(player)) {
                //cast the entity to a StaticEntity
                StaticEntity t = (StaticEntity) e;
                // if player is in the same tile as the StaticEntity
                if (getColumnOfEntity(player) == getColumnOfEntity(t) && getRowOfEntity(player) == getRowOfEntity(t)) {
                    //player is on top of object, then get if the player is pressing the action button
                    if (player.getAction()) {
                        //player wants do action so StaticEntity.doAction()
                        t.doAction();
                    }
                }
            }
        }





        updateCamera();
    }

    public static int getColumnOfEntity(Entity e){
        float x =  e.getX() - e.ENTITY_WIDTH/2;
        int nearestX = (int)(Math.floor((x/Variables.TILEWIDTH))) + 1;
        return nearestX;
    }

    public static int getRowOfEntity(Entity e){
        float y =  e.getY() - e.ENTITY_HEIGHT/2;
        int nearestY = (int) (Math.floor((y/Variables.TILEHEIGHT))) + 1;
        return nearestY;
    }

    public static Tile getTile(int x,int y){
        return world[x][y];
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
            //todo add comparator to make sure player is always draw last(over everything)
            for(int j=0; j < entities.size();j++) {
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
