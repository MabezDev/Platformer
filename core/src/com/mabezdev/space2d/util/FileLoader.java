package com.mabezdev.space2d.util;

import com.badlogic.gdx.Gdx;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Mabez on 18/12/2015.
 */
public class FileLoader {

    private static BufferedReader reader;
    private String path;
    private String[] lines;
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
            reader = new BufferedReader(new FileReader(Gdx.files.getLocalStoragePath() + path));
            ArrayList<String> lines1 = new ArrayList<String>();
            String line;
            while ((line = reader.readLine())!=null) {
                lines1.add(line);
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
