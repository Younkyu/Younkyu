package com.example.younkyu.soundplayer;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import static com.example.younkyu.soundplayer.App.ACTION_PAUSE;
import static com.example.younkyu.soundplayer.App.ACTION_PLAY;
import static com.example.younkyu.soundplayer.App.ACTION_RESTART;
import static com.example.younkyu.soundplayer.App.PAUSE;
import static com.example.younkyu.soundplayer.App.PLAY;
import static com.example.younkyu.soundplayer.App.playStatus;
import static com.example.younkyu.soundplayer.App.soundPlayer;

public class PlayerService extends Service {
    private static final String TAG = "PlayerService";
    public PlayerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null){
            String action = intent.getAction();
            Log.i(TAG,"action=================================="+action);
            switch(action){
                case ACTION_RESTART:
                case ACTION_PLAY:
                    playStart();
                    break;
                case ACTION_PAUSE:
                    playPause();
                    break;
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void playStart(){
        soundPlayer.start();
        playStatus = PLAY;
    }

    private void playPause(){
        soundPlayer.pause();
        playStatus = PAUSE;
    }

}
