package com.mabezdev.space2d.util;

import com.badlogic.gdx.Gdx;
import com.mabezdev.space2d.tiles.InventoryTile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Mabez on 18/12/2015.
 */
public class FileLoader {

    private static Scanner reader;
    private static String path;
    private static String[] lines;


    public FileLoader(String path){
        this.path = path;
        try {
            reader = new Scanner(new File(Gdx.files.getLocalStoragePath() + path));
            lines = getLines();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String[] getLines()throws IOException {
        ArrayList<String> lines1 = new ArrayList<String>();
        while (reader.hasNextLine()) {
            lines1.add(reader.nextLine());
        }
        Log.print(lines1);
        return lines1.toArray(new String[lines1.size()]);
    }

    public int[][] getData(){
        int[][] temp = new int[getRows()][getColumns()];
        for(int i=0; i < getRows();i++){
            String[] separated = lines[i].split(",");
            for(int j=0; j< getColumns(); j++){
                temp[i][j] = Integer.parseInt(separated[j]);
            }
        }
        return temp;
    }

    public static int getColumns(){
        int length = 0;
        char[] oneLine = lines[0].toCharArray();
        for(int i = 0;i < lines[0].length() -1;i++){
            if(oneLine[i]!=','){
                length++;
            }
        }
        return length;
    }

    public static int getRows(){
        return lines.length;
    }

}
