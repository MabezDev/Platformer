package com.mabezdev.space2d.world;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Mabez on 15/12/2015.
 */
public class MapGenerator {

    //filler class currently

    private static String[] lines = new String[5];
    private static BufferedWriter myWriter;

    public MapGenerator(String name) throws IOException{
        lines[0] = "1,1,1,1,1,1,1,1,1,1,1,1,1,1,";
        lines[1] = "1,1,1,1,1,1,1,1,1,1,1,1,1,1,";
        lines[2] = "1,1,1,1,2,2,1,1,1,1,1,1,1,1,";
        lines[3] = "1,1,1,1,1,1,1,1,1,1,1,1,1,1,";
        lines[4] = "1,1,1,1,1,1,1,1,1,1,1,1,1,1,";

        myWriter = new BufferedWriter(new FileWriter(name));
    }

    public static void generateFile() throws IOException{
        for(int i = 0; i < lines.length;i++){
            myWriter.write(lines[i]+"\n");
        }
        myWriter.close();
    }
}
