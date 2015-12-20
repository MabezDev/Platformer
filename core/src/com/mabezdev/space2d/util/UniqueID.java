package com.mabezdev.space2d.util;

/**
 * Created by Mabez on 20/12/2015.
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Scott on 20/08/2015.
 */
public class UniqueID {

    private static List<Integer> ids = new ArrayList<Integer>();
    private static final int RANGE = 10000;

    private static int index = 0;

    static {
        for (int i = 0; i < RANGE; i++) {
            ids.add(i);
        }
        Collections.shuffle(ids);
    }

    private UniqueID() {
    }

    public static int getIdentifier() {
        if (index > ids.size() - 1) index = 0;
        return ids.get(index++);
    }
}