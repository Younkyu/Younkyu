package com.younkyu.android.photomusicplayerservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import static com.younkyu.android.photomusicplayerservice.App.ACTION_PAUSE;
import static com.younkyu.android.photomusicplayerservice.App.ACTION_PLAY;
import static com.younkyu.android.photomusicplayerservice.App.ACTION_STOP;
import static com.younkyu.android.photomusicplayerservice.App.PAUSE;
import static com.younkyu.android.photomusicplayerservice.App.PLAY;
import static com.younkyu.android.photomusicplayerservice.App.isPlaying;
import static com.younkyu.android.photomusicplayerservice.App.player;

public class PlayerService extends Service {
    public PlayerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent != null ) {
            String action = intent.getAction();
            switch(action) {
                case ACTION_PLAY :
                    playStop();
                    break;
                case ACTION_PAUSE :
                    playPlay();
                    break;
                case ACTION_STOP :
                    playPause();
                    break;

            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void playStop() {
        //옵션추가
        isPlaying = PLAY;
        player.start();


    }

    private void playPlay() {
        player.setLooping(false); // 반복여부
        player.pause();
        isPlaying = PAUSE;

    }

    private void playPause() {
        player.start();
        isPlaying = PLAY;

    }
}
