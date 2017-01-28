package com.younkyu.android.remindapplication;

import android.util.Log;

/**
 * Created by USER on 2017-01-26.
 */

public class Logger {

    public static final boolean DEBUG_MODE = true;

    public static void print(String log ,String className ) {
        if(DEBUG_MODE)
        Log.d(log,className);

    }


}
