package com.mabezdev.space2d.util;

import java.io.BufferedWriter;
import java.io.IOException;
/**
 * Created by Mabez on 20/12/2015.
 */
public class FileWriter {

    private BufferedWriter writer;
    private int ROWS;
    private int COLUMNS;
    private String path;
    private int[][] data;

    public FileWriter(int[][] dataset, String location){
        path = location;
        data = dataset;
        ROWS = data.length;
        COLUMNS = data[0].length;
        writeToFile();
    }

    private void writeToFile() {
        Log.print("Saving data to "+path);
        try {
            writer = new BufferedWriter(new java.io.FileWriter(path));
            for (int i = 0; i < ROWS; i++) {
                String builder = "";
                for (int j = 0; j < COLUMNS; j++) {
                    builder += data[i][j] + ",";
                }
                builder+="\n";
                writer.write(builder);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
