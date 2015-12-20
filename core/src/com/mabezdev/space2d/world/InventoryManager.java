package com.mabezdev.space2d.world;

import com.mabezdev.space2d.util.FileLoader;

/**
 * Created by Mabez on 18/12/2015.
 */
public class InventoryManager {

    private int ROWS;
    private int COLUMNS;
    private int[][] valInventory;

    //each entity should have an inventory manager attacked to it

    public InventoryManager(FileLoader fileLoader){ //FileLoader can be replaced with OnlineStreaming version when it gets to
        valInventory = fileLoader.getData();
        ROWS = fileLoader.getRows();
        COLUMNS = fileLoader.getColumns();
        //load tilesets for items
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

    }


    public int getRows() {
        return ROWS;
    }

    public int getColumns() {
        return COLUMNS;
    }



}
