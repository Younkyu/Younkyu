package com.example.younkyu.soundplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

/**
 * Created by Younkyu on 2017-02-28.
 */

public class App {

    // 서비스 플레이 액션 정의
    public static final String ACTION_PLAY = "com.example.younkyu.soundplayer.action.play";
    public static final String ACTION_PAUSE = "com.example.younkyu.soundplayer.action.pause";
    public static final String ACTION_RESTART = "com.example.younkyu.soundplayer.action.restart";
    public static final String ACTION_STOP = "com.example.younkyu.soundplayer.action.stop";

    // 플레이어
    public static MediaPlayer soundPlayer;

    public static void initSound(Context context) {
        Uri soundUri = null; //TODO uri 설정
        soundPlayer = MediaPlayer.create(context, soundUri);

    }

    public static void playSound() {
        soundPlayer.start();
    }

    public static void puaseSound() {

        soundPlayer.pause();
    }

    public static void restartSound() {

        soundPlayer.start();
    }

    public static void stop() {

        if(soundPlayer != null) {
            soundPlayer.release();
            soundPlayer = null;
        }
    }

}
