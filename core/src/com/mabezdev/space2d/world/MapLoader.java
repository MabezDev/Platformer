package com.mabezdev.space2d.world;

import com.badlogic.gdx.Gdx;
import com.mabezdev.space2d.Variables;
import com.mabezdev.space2d.tiles.DirtTile;
import com.mabezdev.space2d.tiles.StoneTile;
import com.mabezdev.space2d.tiles.Tile;
import com.mabezdev.space2d.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Mabez on 15/12/2015.
 */
public class MapLoader {

    private float WIDTH;
    private float HEIGHT;
    private static int[][] GRID;
    private static String path;
    private static String[] lines;
    private static int ROWS;
    private static int COLUMNS;
    private static Scanner reader;

    //Tile type with id's setups
    private enum TILES{

        GRASS(0),
        DIRT(1),
        STONE(2);

        private int id;

        private TILES(int id){
            this.id = id;
        }

        public int getId(){
            return id;
        }
    }

    public MapLoader(String path) throws IOException{
        this.path = path;
        reader = new Scanner(new File(Gdx.files.getLocalStoragePath()+path));
        lines = getLines();
        ROWS = getRows();
        COLUMNS = getUnprocessedColumns()/2;
        GRID = new int[ROWS][COLUMNS];
        loadMap();
    }

    private static void loadMap() throws IOException{
        for(int i = 0; i < ROWS;i++){
            String[] separated = lines[i].split(",");
            for(int j= 0;j < COLUMNS;j++){
                GRID[i][j] = Integer.parseInt(separated[j]);
            }
        }
    }

    public static Tile[][] getMap(){
        //compare ints in GRID to TILES to get the Texture
        Tile[][] tempTiles = new Tile[ROWS][COLUMNS];
        for(int i = 0; i < ROWS;i++){
            for(int j= 0;j < COLUMNS;j++){
                int tile = GRID[i][j];
                //choose tile based on ID
                //improve efficiency by loadng one of each object then reusing it instead of a new object for each tile?
                if(tile == TILES.DIRT.getId()){
                    tempTiles[i][j] = new DirtTile(j* Variables.TILEWIDTH,i*Variables.TILEHEIGHT, TILES.DIRT.getId());
                }
                if(tile == TILES.STONE.getId()){
                    tempTiles[i][j] = new StoneTile(j * Variables.TILEWIDTH,i*Variables.TILEHEIGHT, TILES.STONE.getId());
                }
            }
        }
        return tempTiles;
    }

    public static int getRows(){
        return lines.length;
    }
    public static int getColumns(){
        return COLUMNS;
    }
    private static int getUnprocessedColumns(){
        return lines[0].length();
    }

    private static String[] getLines ()throws IOException {
        ArrayList<String> lines1 = new ArrayList<String>();
        while (reader.hasNextLine()) {
            lines1.add(reader.nextLine());
        }
        reader.close();
        return lines1.toArray(new String[lines1.size()]);
    }

    public static void dispose() throws IOException {

    }

}
