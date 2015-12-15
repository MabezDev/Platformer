package com.mabezdev.space2d.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mabezdev.space2d.util.Log;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Mabez on 15/12/2015.
 */
public class Map {

    private float WIDTH;
    private float HEIGHT;
    private static int[][] GRID;
    private static String path;
    private static int ROWS;
    private static int COLUMNS;
    private static Scanner reader;

    public Map(String path) throws IOException{
        this.path = path;
        reader = new Scanner(new File(Gdx.files.getLocalStoragePath()+path));
        ROWS = getRows();
        COLUMNS = getColumns()/2;
        //divide by two as once the commas are removed half the size will be left
        Log.print(ROWS);
        Log.print(COLUMNS);
        GRID = new int[ROWS][COLUMNS];
    }

    public static int[][] getMap() throws IOException{
        String[] lines = getLines();
        reader.close();
        Log.print(lines);
        for(int i = 0; i < ROWS;i++){
            String[] separated = lines[i].split(",");
            for(int j= 0;j < COLUMNS;j++){
                GRID[i][j] = Integer.parseInt(separated[j]);
            }
        }
        return GRID;
    }

    private static int getColumns() throws IOException{
        int columns = 0;
        if(reader.hasNextLine()) {
            columns = reader.nextLine().length();
        }
        return columns;
    }

    private static int getRows() throws IOException {
        int rows = 0;
        while (reader.hasNextLine()) {
            rows++;
            reader.nextLine();
        }
        return rows;
    }

        private static String[] getLines ()throws IOException {
            String[] lines = new String[ROWS];
            int index = 0;
            Log.print(reader.hasNextLine());
            while (reader.hasNext()) {
                lines[index] = reader.nextLine().toString();
                index++;
            }
            return lines;
        }

    public static void dispose() throws IOException {
        reader.close();
    }

}
