package com.mabezdev.space2d.util;

/**
 * Created by Mabez on 17/12/2015.
 */
public class Maths {

    public static int  roundUp(int numToRound, int multiple)
    {
        if (multiple == 0)
            return numToRound;

        int remainder = numToRound % multiple;
        if (remainder == 0)
            return numToRound;

        return numToRound + multiple - remainder;
    }
}
