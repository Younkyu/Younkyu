package com.example.younkyu.soundplayer;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.younkyu.soundplayer.domain.Sound;
import com.example.younkyu.soundplayer.fragments.util.fragment.TimeUtil;

import java.util.List;

import static com.example.younkyu.soundplayer.App.ACTION_PAUSE;
import static com.example.younkyu.soundplayer.App.ACTION_PLAY;
import static com.example.younkyu.soundplayer.App.ACTION_RESTART;
import static com.example.younkyu.soundplayer.App.PAUSE;
import static com.example.younkyu.soundplayer.App.PLAY;
import static com.example.younkyu.soundplayer.App.STOP;
import static com.example.younkyu.soundplayer.App.playStatus;
import static com.example.younkyu.soundplayer.App.position;
import static com.example.younkyu.soundplayer.App.soundPlayer;

public class PlayerActivity extends AppCompatActivity {
    private static final String TAG="PlayerActivity";
    ViewPager viewPager;
    ImageButton btnRew, btnPlay, btnFf;

    List<Sound> datas;
    PlayerAdapter adapter;

    SeekBar seekBar;
    TextView txtDuration,txtCurrent;

    Intent service;

    int position = 0;
    String list_type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        service = new Intent(this,PlayerService.class);

        // 볼륨 조절 버튼으로 미디어 음량만 조절하기 위한 설정
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        // seekBar 의 변경사항을 체크하는 리스너 등록
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        txtDuration = (TextView) findViewById(R.id.txtDuration);
        txtCurrent = (TextView) findViewById(R.id.txtCurrent);

        btnRew = (ImageButton) findViewById(R.id.btnRew);
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        btnFf = (ImageButton) findViewById(R.id.btnFf);

        btnRew.setOnClickListener(clickListener);
        btnPlay.setOnClickListener(clickListener);
        btnFf.setOnClickListener(clickListener);

        // 0. 데이터 가져오기
        datas = DataLoader.getSound(this);

        // 1. 뷰페이저 가져오기
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        // 2. 뷰페이저용 아답터 생성
        adapter = new PlayerAdapter(datas ,this);
        // 3. 뷰페이저 아답터 연결
        viewPager.setAdapter( adapter );
        // 4. 뷰페이지 리스너 연결
        viewPager.addOnPageChangeListener(viewPagerListener);

        // 5. 특정 페이지 호출
        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            list_type = bundle.getString(ListFragment.ARG_LIST_TYPE);
            position = bundle.getInt(ListFragment.ARG_POSITION);
            // 첫페이지일 경우만 init 호출
            // 이유 : 첫페이지가 아닐경우 위의 setCurrentItem 에 의해서 ViewPager의 onPageSelected가 호출된다.
            if(position == 0) {
                init();
            } else {
                // 0 페이지가 아닐경우 해당페이지로 이동한다. 이동후 listener 에서 init 이 자동으로 호출된다.
                viewPager.setCurrentItem(position);
            }
        }
    }

    // 컨트롤러 정보 초기화
    private void init(){
        playerInit();
        controllerInit();
    }

    private void playerInit(){

    }

    private void controllerInit(){
        Sound sound = datas.get(position);
        txtCurrent.setText("0");
        txtDuration.setText(sound.getDurationText());
        seekBar.setMax(sound.getDuration());
    }

    private void play() {
        // 플레이중이 아니면 음악 실행
        Log.i("PlayerActivity","=============play().playStatus="+playStatus);
        switch(playStatus) {
            case STOP:
                playStart();
                break;
            // 플레이중이면 멈춤
            case PLAY :
                playPause();
                break;
            // 멈춤상태이면 거기서 부터 재생
            case PAUSE:
                playRestart();
                break;
        }
    }

    private void playStart(){

        Intent intent = new Intent(this, SoundService.class);
        intent.setAction(SoundService.ACTION_PLAY);
        intent.putExtra(ListFragment.ARG_POSITION,position);
        intent.putExtra(ListFragment.ARG_LIST_TYPE,list_type);
        startService(intent);
        Log.i("AAAAAAAAAAAAAAA","ccccccccccccc");
    }

    private void playPause(){

    }

    private void playRestart(){

    }



    private void prev() {

    }

    private void next() {

    }

    // 버튼 클릭 리스너
    View.OnClickListener clickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnPlay:
                    play();
                    break;
                case R.id.btnRew:
                    prev();
                    break;
                case R.id.btnFf:
                    next();
                    break;
            }
        }
    };

    // 뷰페이저 체인지 리스너
    ViewPager.OnPageChangeListener viewPagerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            init();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    // SeekBar 체인지 리스너
    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(soundPlayer != null && fromUser)
                soundPlayer.seekTo(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    // sub thread 를 생성해서 mediaplayer 의 현재 포지션 값으로 seekbar 를 변경해준다. 매 1초마다
    // sub thread 에서 동작할 로직 정의
    class TimerThread extends Thread {
        @Override
        public void run() {
            while (playStatus < STOP) {
                //Log.i("PlayerActivity","TimeThread================================="+player);
                if(soundPlayer != null) {
                    // 이 부분은 메인쓰레드에서 동작하도록 Runnable instance를 메인쓰레드에 던져준다
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                seekBar.setProgress(soundPlayer.getCurrentPosition());
                                txtCurrent.setText(TimeUtil.covertMiliToTime(soundPlayer.getCurrentPosition()));
                            } catch (Exception e) { e.printStackTrace(); }
                        }
                    });
                }
                try { Thread.sleep(1000); } catch (InterruptedException e) {}
            }
        }
    }
}
