package com.younkyu.android.albumplayer;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by USER on 2017-02-05.
 */

public class Checkdata {

    private static ArrayList<Photo> datas = new ArrayList<>();
    private static ArrayList<Photo> datas2 = new ArrayList<>();

    private Context context;

    public Checkdata(Context context) {
        this.context = context;
    }

    public static ArrayList<Photo> get(Context context) {
        datas = DataLoader.get(context);

        int i = 0;
        while(i<datas.size()) {
            if(datas.get(i).mola != null) {
                datas2.add(datas.get(i));
            }
            i++;
        }
        return datas2;
    }

    public static void remove(Context context) {

        datas2.clear();
    }

}
