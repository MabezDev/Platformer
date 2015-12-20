package com.mabezdev.space2d.util;

import com.badlogic.gdx.Gdx;

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
    private int ROWS;
    private int COLUMNS;

    public FileLoader(String path){
        this.path = path;
    }

    public String getFilePath(){
        return path;
    }

    public String[] getLines(){
        try {
            reader = new Scanner(new File(Gdx.files.getLocalStoragePath() + path));
            ArrayList<String> lines1 = new ArrayList<String>();
            while (reader.hasNextLine()) {
                lines1.add(reader.nextLine());
            }
            reader.close();
            return lines1.toArray(new String[lines1.size()]);
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public int[][] getData(){
        lines = getLines();
        ROWS = getRows();
        COLUMNS = getColumns();
        int[][] temp = new int[ROWS][COLUMNS];
        for(int i=0; i < ROWS;i++){
            String[] separated = lines[i].split(",");
            for(int j=0; j< COLUMNS; j++){
                temp[i][j] = Integer.parseInt(separated[j]);
            }
        }
        return temp;
    }

    public int getColumns(){
        int length = 0;
        char[] oneLine = lines[0].toCharArray();
        for(int i = 0;i < lines[0].length() -1;i++){
            if(oneLine[i]!=','){
                length++;
            }
        }
        return length;
    }

    public int getRows(){
        return lines.length;
    }

}
