package com.example.younkyu.soundplayer.fragments.util.fragment;

/**
 * Created by Younkyu on 2017-03-02.
 */

public class TimeUtil {

    // 시간 포맷 변경 00:00
    public static String covertMiliToTime(long mili){
        long min = mili / 1000 / 60;
        long sec = mili / 1000 % 60;

        return String.format("%02d", min) + ":" + String.format("%02d", sec);
    }

}
