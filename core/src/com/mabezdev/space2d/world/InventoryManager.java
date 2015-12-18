package com.mabezdev.space2d.world;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mabezdev.space2d.tiles.InventoryTile;
import com.mabezdev.space2d.util.FileLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Mabez on 18/12/2015.
 */
public class InventoryManager {

    private TextureRegion[][] inventory;
    private int ROWS;
    private int COLUMNS;
    private int[][] valInventory;

    //each entity should have an inventory manager attacked to it

    public InventoryManager(FileLoader fileLoader){ //FileLoader can be replaced with OnlineStreaming version when it gets to
        valInventory = fileLoader.getData();
        ROWS = fileLoader.getRows();
        COLUMNS = fileLoader.getColumns();
    }

    public int[][] getInventory(){
        return null;
    }

    public void updateInventory(InventoryTile t,int index){

    }

    public void saveInventory(){

    }



}
