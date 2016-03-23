package com.mabezdev.space2d.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mabezdev.space2d.Variables;
import com.mabezdev.space2d.tiles.DirtTile;
import com.mabezdev.space2d.tiles.Sky;
import com.mabezdev.space2d.tiles.StoneTile;
import com.mabezdev.space2d.tiles.Tile;
import com.mabezdev.space2d.util.FileLoader;
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
    private int[][] GRID;
    private String path;
    private String[] lines;
    private int ROWS;
    private int COLUMNS;

    //Tile type with id's setups
    private enum TILES{

        SKY(0),
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

    public MapLoader(FileLoader myfileLoader){ //FileLoader can be replaced with OnlineStreaming version when it gets to
        GRID = myfileLoader.getData();
        ROWS = myfileLoader.getRows();
        COLUMNS = myfileLoader.getColumns();
    }

    public Tile[][] getMap(){
        //compare ints in GRID to TILES to get the Texture
        Tile[][] tempTiles = new Tile[ROWS][COLUMNS];
        for(int i = 0; i < ROWS;i++){
            for(int j= 0;j < COLUMNS;j++){
                int tile = GRID[i][j];
                //choose tile based on ID
                //improve efficiency by loadng one of each object then reusing it instead of a new object for each tile?
                if(tile == TILES.SKY.getId()){
                    tempTiles[i][j] = new Sky(j* Variables.TILEWIDTH,i*Variables.TILEHEIGHT,Variables.TILEWIDTH,Variables.TILEHEIGHT,TILES.SKY.getId());
                }
                if(tile == TILES.DIRT.getId()){
                    tempTiles[i][j] = new DirtTile(j* Variables.TILEWIDTH,i*Variables.TILEHEIGHT,Variables.TILEWIDTH,Variables.TILEHEIGHT,TILES.DIRT.getId());
                }
                if(tile == TILES.STONE.getId()){
                    tempTiles[i][j] = new StoneTile(j * Variables.TILEWIDTH,i*Variables.TILEHEIGHT,Variables.TILEWIDTH,Variables.TILEHEIGHT, TILES.STONE.getId());
                }
            }
        }
        return tempTiles;
    }

    public int getRows(){
        return ROWS;
    }
    public int getColumns(){
        return COLUMNS;
    }

}
