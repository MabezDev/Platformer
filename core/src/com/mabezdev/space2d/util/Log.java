package com.mabezdev.space2d.util;

/**
 * Created by Mabez on 15/12/2015.
 */
public class Log {

    public static void print(String toPrint){
        System.out.println(toPrint);
    }
    public static void print(int toPrint){
        System.out.println(toPrint);
    }
    public static void print(float toPrint){
        System.out.println(toPrint);
    }
    public static void print(boolean toPrint){
        System.out.println(toPrint);
    }
    public static void print(Object toPrint){
        System.out.println(toPrint);
    }
    public static void print(String[] toPrint){
        for(int i = 0; i < toPrint.length;i++){
            System.out.println(toPrint[i]);
        }
    }
    public static void print(int[] toPrint){
        for(int i = 0; i < toPrint.length;i++){
            System.out.println(toPrint[i]);
        }
    }
    public static void print(boolean[] toPrint){
        for(int i = 0; i < toPrint.length;i++){
            System.out.println(toPrint[i]);
        }
    }



}
