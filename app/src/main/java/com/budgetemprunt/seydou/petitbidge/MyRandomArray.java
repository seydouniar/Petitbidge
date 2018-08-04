package com.budgetemprunt.seydou.petitbidge;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;

public class MyRandomArray {
    private ArrayList list;
    public  MyRandomArray(){
        this.list = new ArrayList<Integer>();
    }
    public  ArrayList<Integer> generates(){
        for (int i = 0;i<10;i++){
            list.add(i);
        }
        Collections.shuffle(list);
        return list;
    }
}
