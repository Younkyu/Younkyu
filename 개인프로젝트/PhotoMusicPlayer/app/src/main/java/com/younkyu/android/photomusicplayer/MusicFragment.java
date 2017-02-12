package com.younkyu.android.photomusicplayer;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;


public class MusicFragment extends Fragment implements ViewPager.OnPageChangeListener {

    ViewPager viewpager;
    ImageButton ap, dui, play;
    ImageView imgview;
    List<Music> datas;
    PlayerAdapter adapter;

    MediaPlayer player;
    SeekBar seekBar;
    TextView txtDuration;
    TextView txtcurrent;
    CheckBox cbrd;
    boolean cb = false;
    private static final int PLAY = 0;
    private static final int PAUSE = 1;
    private static final int STOP = 2;

    private static int isPlaying = STOP;
    // boolean isPlaying;
    int position = 0; // 현재 위치


    public MusicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_music, container, false);

        settings(view);
        sunun(view);
        setAdapter(view);
        tent();

        return view;
    }

    private void settings(View view) {
        isPlaying = STOP;
        getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);

    }

    private void tent() {
        position = (int)(Math.random()*datas.size());
        viewpager.setCurrentItem(position);
        init();
    }

    private void setAdapter(View view) {
        // 데이터 가져오기
        datas = DataLoader.get(getContext());

        //뷰페이저 가져오기
        viewpager = (ViewPager) view.findViewById(R.id.viewpaser);

        // 뷰 페이저용 아답터 생성
        adapter = new PlayerAdapter(datas, getContext());
        // 아답터연결
        viewpager.setAdapter(adapter);

        //뷰페이저 리스너 제작
        viewpager.addOnPageChangeListener(this);

        viewpager.setPageTransformer(false, pageTransformer);
    }

    private void sunun(View view) {
        ap = (ImageButton) view.findViewById(R.id.ap);
        dui = (ImageButton) view.findViewById(R.id.dui);
        play = (ImageButton) view.findViewById(R.id.play);
        imgview = (ImageView) view.findViewById(R.id.imageView2);
        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        txtDuration = (TextView) view.findViewById(R.id.txtDuration);
        txtcurrent = (TextView) view.findViewById(R.id.txtcurrent);
        cbrd = (CheckBox) view.findViewById(R.id.cbrd);

        //  식바리스너제작
        seekBar.setOnSeekBarChangeListener(seek);
        ap.setOnClickListener(clickListener);
        dui.setOnClickListener(clickListener);
        play.setOnClickListener(clickListener);
        cbrd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    cb = true;
                } else {
                    cb = false;
                }
            }
        });

    }
    View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.play :
                    playms();
                    break;
                case R.id.ap :
                    apms();
                    break;
                case R.id.dui :
                    duims();
                    break;
            }

        }
    };


    private void init() {
        if(player != null) {
            isPlaying = STOP;
            play.setImageResource(android.R.drawable.ic_media_play);
            player.release();
        }
        playerInit ();
        controlerInit ();
    }


    private void playerInit () {
        Uri musicUri = datas.get(position).uri;
        // 1. 플레이어 초기화
        player = MediaPlayer.create(getContext(), musicUri);

        //미디어플레이어에 완료체크리스너를 등록한다
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                apms();
            }
        });
    }

    private void controlerInit () {
        seekBar.setMax(player.getDuration());
        seekBar.setProgress(0);
        txtDuration.setText(converMiliToTim(player.getDuration()) + "");
        txtcurrent.setText("0");
    }


    private void playms() {

        switch(isPlaying) {
            case STOP :
                //옵션추가
                playStop();
                break;
            case PAUSE :
                playPause();
                break;
            case PLAY :
                playPlay();
                break;
        }
    }

    private void playStop() {
        //옵션추가
        isPlaying = PLAY;
        play.setImageResource(android.R.drawable.ic_media_pause);
        player.start();

        Thread thread = new TimerThread();
        thread.start();

    }

    private void playPlay() {
        player.setLooping(false); // 반복여부
        player.pause();
        isPlaying = PAUSE;
        play.setImageResource(android.R.drawable.ic_media_play);
    }

    private void playPause() {
        player.start();
        isPlaying = PLAY;
        play.setImageResource(android.R.drawable.ic_media_pause);
    }


    private void apms() {
        if(position < datas.size()-1) {
            if(!cb) {
                position = position+1;
            } else {
                position = (int)(Math.random()*datas.size());
            }
            viewpager.setCurrentItem(position);
            init();
            playms();
        } else {
            if(!cb) {
                position = 0;
            } else {
                position = (int)(Math.random()*datas.size());
            }
            viewpager.setCurrentItem(position);
            init();
            playms();
        }
    }

    private void duims() {
        if(position> 0) {

            if(!cb) {
                position = position-1;
            } else {
                position = (int)(Math.random()*datas.size());
            }
            viewpager.setCurrentItem(position);
            init();
            playms();
        }else {
            if(!cb) {
                position = datas.size()-1;
            } else {
                position = (int)(Math.random()*datas.size());
            }
            viewpager.setCurrentItem(position);
            init();
            playms();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(player != null) {
            player.release(); // 사용이 끝나면 해제해야만 한다.
            //   player.release(); // 사용이 끝나면 해제해야만 한다.
            //   isPlaying = STOP;
        }
        isPlaying = STOP;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        MusicFragment.this.position = position;
        init();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    SeekBar.OnSeekBarChangeListener seek =  new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//            if(fromUser) {
//                if(player != null) {
//                    player.seekTo(progress);
//                }
//            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            int progress = seekBar.getProgress();
            if(player != null) {
                player.seekTo(progress);
            }
        }
    };

    private String converMiliToTim(long mili) {
        long min = mili / 1000 / 60;
        long sec = mili/1000 % 60;

        return String.format("%02d", min) + ":" + String.format("%02d", sec);
    }

    ViewPager.PageTransformer pageTransformer = new ViewPager.PageTransformer() {

        @Override
        public void transformPage(View page, float position) {
            //현재 Page의 위치가 조금이라도 바뀔때마다 호출되는 메소드
            //첫번째 파라미터 : 현재 존재하는 View 객체들 중에서 위치가 변경되고 있는 View들
            //두번째 파라미터 : 각 View 들의 상대적 위치( 0.0 ~ 1.0 : 화면 하나의 백분율)

            //           1.현재 보여지는 Page의 위치가 0.0
            //           Page가 왼쪽으로 이동하면 값이 -됨. (완전 왼쪽으로 빠지면 -1.0)
            //           Page가 오른쪽으로 이동하면 값이 +됨. (완전 오른쪽으로 빠지면 1.0)

            //주의할 것은 현재 Page가 이동하면 동시에 옆에 있는 Page(View)도 이동함.
            //첫번째와 마지막 Page 일때는 총 2개의 View가 메모리에 만들어져 잇음.
            //나머지 Page가 보여질 때는 앞뒤로 2개의 View가 메모리에 만들어져 총 3개의 View가 instance 되어 있음.
            //ViewPager 한번에 1장의 Page를 보여준다면 최대 View는 3개까지만 만들어지며
            //나머지는 메모리에서 삭제됨.-리소스관리 차원.

            //position 값이 왼쪽, 오른쪽 이동방향에 따라 음수와 양수가 나오므로 절대값 Math.abs()으로 계산
            //position의 변동폭이 (-2.0 ~ +2.0) 사이이기에 부호 상관없이 (0.0~1.0)으로 변경폭 조절
            //주석으로 수학적 연산을 설명하기에는 한계가 있으니 코드를 보고 잘 생각해 보시기 바랍니다.
            float normalizedposition = Math.abs( 1 - Math.abs(position) );

            page.setAlpha(normalizedposition);  //View의 투명도 조절
            page.setScaleX(normalizedposition/2 + 0.5f); //View의 x축 크기조절
            page.setScaleY(normalizedposition/2 + 0.5f); //View의 y축 크기조절
            page.setRotationY(position * 80); //View의 Y축(세로축) 회전 각도
        }
    };

    class TimerThread extends Thread {
        @Override
        public void run() {
            while (isPlaying < STOP) {
                if(player != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(player != null) {
                                try {
                                    seekBar.setProgress(player.getCurrentPosition());
                                    txtcurrent.setText(converMiliToTim(player.getCurrentPosition()) + "");
                                } catch (Exception e) {
                                }
                            }
                        }
                    });
                }
                //   handler.sendEmptyMessage(PROGRESS_SET);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
        }
    }


}
