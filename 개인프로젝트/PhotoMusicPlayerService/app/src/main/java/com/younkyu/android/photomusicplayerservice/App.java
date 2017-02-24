package com.younkyu.android.photomusicplayerservice;

import android.media.MediaPlayer;

/**
 * Created by Younkyu on 2017-02-24.
 */

public class App {

    public static MediaPlayer player = null;

    public static final int PLAY = 0;
    public static final int PAUSE = 1;
    public static final int STOP = 2;

    public static int isPlaying = STOP;
    // boolean isPlaying;
    public static int position = 0; // 현재 위치


    //액션플래그
    public static final String ACTION_PLAY="com.younkyu.android.photomusicplayerservice.Action.Play";
    //액션플래그
    public static final String ACTION_PAUSE="com.younkyu.android.photomusicplayerservice.Action.Puase";
    //액션플래그
    public static final String ACTION_STOP="com.younkyu.android.photomusicplayerservice.Action.STOP";

}
