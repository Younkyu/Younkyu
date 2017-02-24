package com.younkyu.android.photomusicplayerservice;

import java.util.ArrayList;

/**
 * Created by USER on 2017-02-12.
 */

public class DataLoaderPhoto {

    public static DataLoaderPhoto instance = new DataLoaderPhoto();
    public static ArrayList<Photos> data = new ArrayList<>();

    private void DataLoader ( ) {

    }

    public static DataLoaderPhoto getInstance() {
        return  instance;
    }

    public static void addData(Photos pt) {
        data.add(pt);
    }

    public static ArrayList<Photos> getData() {
      return data;
    }

    public static void removeData() {
        data.clear();
    }


}
