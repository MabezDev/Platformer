package com.mabezdev.space2d.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;

/**
 * Created by Mabez on 20/12/2015.
 */
public class FileWriter {

    private static BufferedWriter writer;
    private static int ROWS;
    private static int COLUMNS;
    private static String path;
    private static int[][] data;

    public FileWriter(int[][] dataset, String location){
        path = location;
        data = dataset;
        ROWS = data.length;
        COLUMNS = data[0].length;
        try {
            writer = new BufferedWriter(new java.io.FileWriter(path));
        } catch (IOException e){
            e.printStackTrace();
        }
        writeToFile();
    }

    private static void writeToFile() {
        try {
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
