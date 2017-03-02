package com.example.younkyu.newsoundplayer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.media.MediaPlayer;
import android.media.Rating;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class MediaPlayerService extends Service {

    public static final String ACTION_PLAY = "action_play";
    public static final String ACTION_PAUSE = "action_pause";
    public static final String ACTION_REWIND = "action_rewind";
    public static final String ACTION_FAST_FORWARD = "action_fast_forward";
    public static final String ACTION_NEXT = "action_next";
    public static final String ACTION_PREVIOUS = "action_previous";
    public static final String ACTION_STOP = "action_stop";

    private MediaPlayer mMediaPlayer;
    private MediaSessionManager mManager;

    // 2. Intent Action에 넘어온 명령어를 분기시키는 함수

    // 2. Intent Action 에 넘어온 명령어를 분기시키는 함수
    private void handleIntent( Intent intent ) {
        if( intent == null || intent.getAction() == null )
            return;
        String action = intent.getAction();
        if( action.equalsIgnoreCase( ACTION_PLAY ) ) {
            buildNotification(generateAction(android.R.drawable.ic_media_play, "Play", ACTION_PLAY));

        } else if( action.equalsIgnoreCase( ACTION_PAUSE ) ) {

        } else if( action.equalsIgnoreCase( ACTION_FAST_FORWARD ) ) {

        } else if( action.equalsIgnoreCase( ACTION_REWIND ) ) {

        } else if( action.equalsIgnoreCase( ACTION_PREVIOUS ) ) {

        } else if( action.equalsIgnoreCase( ACTION_NEXT ) ) {

        } else if( action.equalsIgnoreCase( ACTION_STOP ) ) {

        }
    }

    private NotificationCompat.Action generateAction(int icon, String title, String intentAction ) {
        Intent intent = new Intent( getApplicationContext(), MediaPlayerService.class );
        intent.setAction( intentAction );
        // 펜딩인텐트 : 실행 대상이 되는 인텐트를 지연시키는 역할
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 1, intent, 0);
            return new NotificationCompat.Action.Builder(icon, title, pendingIntent).build();
    }

    private void buildNotification( NotificationCompat.Action action ) {

        // 노티바 전체를 클릭했을 때 실행되는 메인 인텐트
       Intent intent = new Intent( getApplicationContext(), MediaPlayerService.class );
        intent.setAction( ACTION_STOP );
        PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 1, intent, 0);

        // 노티바 생성
        NotificationCompat.Builder builder = new NotificationCompat.Builder( this )
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle( "Beautiful" )
                .setContentText( "Crush" )
                .setDeleteIntent( pendingIntent )
                .setOngoing(false);

        builder.addAction( generateAction( android.R.drawable.ic_media_previous, "Previous", ACTION_PREVIOUS ) );
        builder.addAction( generateAction( android.R.drawable.ic_media_rew, "Rewind", ACTION_REWIND ) );
        builder.addAction( action );
        builder.addAction( generateAction( android.R.drawable.ic_media_ff, "Fast Foward", ACTION_FAST_FORWARD ) );
        builder.addAction( generateAction( android.R.drawable.ic_media_next, "Next", ACTION_NEXT ) );


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE );
        // 노티바를 화면에 보여준다.
        notificationManager.notify( 1, builder.build() );
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if( mManager == null ) {
            initMediaSessions();
        }

        handleIntent( intent );
        return super.onStartCommand(intent, flags, startId);
    }

    private void initMediaSessions() {
        mMediaPlayer = new MediaPlayer();



    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
