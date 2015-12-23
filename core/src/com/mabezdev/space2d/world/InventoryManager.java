package com.mabezdev.space2d.world;

import com.mabezdev.space2d.tiles.items.Item;
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

    public InventoryManager(FileLoader fileLoader){ //FileLoader can be replaced with DataStreamLoader( for online)
        valInventory = fileLoader.getData();
        ROWS = fileLoader.getRows();
        COLUMNS = fileLoader.getColumns();
        path = fileLoader.getFilePath();
        this.fileLoader = fileLoader;
    }

    public int[][] getInventory(){
        return valInventory;
    }

    public void refreshData(){
        valInventory = fileLoader.getData();
        Log.print("Refreshing data from : "+fileLoader.getFilePath());
    }

    @Deprecated
    public void addToInventory(int itemID, int row,int column){
        valInventory[row][column] = itemID;
    }

    public void addToInventory(Item i){
        for(int x=0; x < valInventory.length;x++) {
            for(int y=0; y < valInventory[0].length;y++) {
                if(valInventory[x][y]==0){
                    Log.print("added the item!");
                    valInventory[x][y] = i.getItemID();
                    break;
                    //break so we only add one!
                } else {
                    continue;
                }
            }
        }
    }

    public void removeFromInventory(int row,int column){
        valInventory[row][column] = 0; //zero itemID will be empty
    }

    public void removeFromInventory(Item i){
        valInventory[i.getRow()][i.getColumn()] = 0;
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
