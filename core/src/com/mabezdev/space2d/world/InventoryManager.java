package com.mabezdev.space2d.world;

import com.mabezdev.space2d.util.FileLoader;
import com.mabezdev.space2d.util.FileWriter;
import com.mabezdev.space2d.util.Log;


/**
 * Created by Mabez on 18/12/2015.
 */
public class InventoryManager {

    private int ROWS;
    private int COLUMNS;
    private int[][] valInventory;
    private String path;
    private FileLoader fileLoader;

    //each entity should have an inventory manager attached to it

    public InventoryManager(FileLoader fileLoader){ //FileLoader can be replaced with OnlineStreaming version when it gets to
        valInventory = fileLoader.getData();
        ROWS = fileLoader.getRows();
        COLUMNS = fileLoader.getColumns();
        path = fileLoader.getFilePath();
        this.fileLoader = fileLoader;
    }

    public int[][] getInventory(){
        return valInventory;
    }


    public void addToInventory(int itemID, int row,int column){
        valInventory[row][column] = itemID;
    }

    public void removeFromInventory(int row,int column){
        valInventory[row][column] = 0; //zero itemID will be empty
    }

    public void saveInventory(){
        new FileWriter(valInventory,path);
    }


    public int getRows() {
        return ROWS;
    }

    public int getColumns() {
        return COLUMNS;
    }



}
