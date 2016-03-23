package com.mabezdev.space2d.states;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mabezdev.space2d.Variables;
import com.mabezdev.space2d.entities.*;
import com.mabezdev.space2d.managers.GameStateManager;
import com.mabezdev.space2d.managers.ResourceManager;
import com.mabezdev.space2d.tiles.Tile;
import com.mabezdev.space2d.tiles.items.Item;
import com.mabezdev.space2d.util.FileLoader;
import com.mabezdev.space2d.util.Log;
import com.mabezdev.space2d.world.MapLoader;
import com.mabezdev.space2d.world.MapGenerator;

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
    private static OrthographicCamera hudCamera;
    private static int ROWS;
    private static int COLUMNS;
    private static float WORLD_WIDTH;
    private static float WORLD_HEIGHT;
    private static Tile[][] world;
    private static ArrayList<Entity> entities;
    private static ArrayList<Players> players;
    private static Player player;
    private static final float cameraLerp = 0.1f;
    private boolean isPaused;



    public PlayState(GameStateManager gsm) {
        super(gsm);

        ResourceManager.loadTexture("tileset","tilesets/tilesets.png");
        ResourceManager.loadTexture("player","tilesets/playerset.png");
        ResourceManager.loadTexture("interactive","tilesets/interactives.png");
        ResourceManager.loadTexture("items","tilesets/items.png");

        sb = new SpriteBatch();
        camera = GSManager.getCamera();
        entities = new ArrayList<Entity>();
        players = new ArrayList<Players>();
        player = new Player(20,40,20);
        Enemy player2 = new Enemy(15,15,20);
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

        hudCamera = new OrthographicCamera(Variables.GAME_CAMERA_VIEWPORT_WIDTH,Variables.GAME_CAMERA_VIEWPORT_HEIGHT);

        Chest myChest2 = new Chest(1*Variables.TILEWIDTH,1*Variables.TILEHEIGHT, Chest.chestState.CLOSED);
        entities.add(myChest2);
        players.add(player);
        players.add(player2);
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

    public static OrthographicCamera getHudCamera(){
        return hudCamera;
    }

    @Override
    public void update(float dt) {
        /*
        This whole algorithm needs to be redone to support multiple enemies and the player working
        Enemeis should just be giving data to the playstate not taking in any input from our client
        Eventually they will be streamed data coming from other clients
         */
        /*for (int j = 0; j < players.size(); j++){
            //update all entities
            Tile currentTile = world[getRowOfEntity(players.get(j))][getColumnOfEntity(players.get(j))];
            players.get(j).setCurrentTile(currentTile);
            players.get(j).update(dt);
            for(Entity e : entities) {
                //cast the entity to a StaticEntity
                StaticEntity t = (StaticEntity) e;
                t.update(dt);
                // if player is in the same tile as the StaticEntity
                if (getColumnOfEntity(players.get(j)) == getColumnOfEntity(t) && getRowOfEntity(players.get(j)) == getRowOfEntity(t)) {
                    //player is on top of object, then get if the player is pressing the action button
                    if (player.getAction()) {
                        //player wants do action so StaticEntity.doAction()
                        Log.print("Called");
                        t.doAction();
                    }
                }
            }
        }*/

        for(Players p : players){
            p.update(dt);
            if(p.equals(player)){
                Player mainPlayer = (Player) p;
                mainPlayer.handleInput();
            } else {
                //check player attacks
                Log.print("Player: ("+player.getCurrentTile().getX()+","+player.getCurrentTile().getY());
                Log.print("Enemy: ("+p.getCurrentTile().getX()+","+p.getCurrentTile().getY());
                if(player.getCurrentTile().equals(p.getCurrentTile())){
                    Log.print("In same tile");
                    if(player.isAttacking()){
                        p.removeHealth(player.getAttackDamage());
                        Log.print("Hitting player for "+player.getAttackDamage()+" damage.");
                    }
                }
            }

            for(Entity e : entities){
                e.update(dt);
                if(e.getType().equals(Entity.STATIC)){
                    StaticEntity s = (StaticEntity) e;
                    if(s.getCurrentTile().equals(p.getCurrentTile())){
                        if(player.getAction()){
                            s.doAction();
                        }
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

    public static Tile getTileFromCoordinates(float x, float y){
        int nearestX = (int)(Math.floor((x/Variables.TILEWIDTH))) + 1;
        int nearestY = (int) (Math.floor((y/Variables.TILEHEIGHT))) + 1;
        return world[nearestY][nearestX];
    }

    public static int getRowOfEntity(Entity e){
        float y =  e.getY() - e.ENTITY_HEIGHT/2;
        int nearestY = (int) (Math.floor((y/Variables.TILEHEIGHT))) + 1;
        return nearestY;
    }

    public static Tile getTile(int x,int y){
        return world[x][y];
    }

    public static int[] getTilePosition(Tile tile){
        for(int i=0; i < world.length; i++){
            for (int j = 0; j < world[0].length; j++) {
                if(world[i][j]==tile){
                    return new int[] {i,j};
                }
            }
        }
        return null;
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

            for(int k=0; k < players.size(); k++){
                players.get(k).render(sb);
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
