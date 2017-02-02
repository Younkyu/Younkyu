package com.younkyu.android.custommusicplayer;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    ImageButton ibtnnext;
    ViewPager vp;
    ArrayList<Music> datas;
    CustomAdapter adapter;
    MediaPlayer player;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            checkPermission();
        } else {
            init();
        }
    }

    // 데이터를 로드할 함수
    private void init() {

        Toast.makeText(this, "프로그램이 실행됩니다.", Toast.LENGTH_LONG).show();

        setContentView(R.layout.activity_main);

        ibtnnext = (ImageButton)findViewById(R.id.ibtnnext);
        vp = (ViewPager) findViewById(R.id.vp);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        ibtnnext.setOnClickListener(this);

        // 데이터 가져오기
        datas = Data.get(this);

        //뷰페이저 가져오기
        vp = (ViewPager) findViewById(R.id.vp);

        // 뷰 페이저용 아답터 생성
        adapter = new CustomAdapter(datas, this);
        // 아답터연결
        vp.setAdapter(adapter);
        vp.addOnPageChangeListener(this);
        //옵션추가
        Uri musicUri = datas.get(position).uri;
        // 1. 플레이어 초기화
        player = MediaPlayer.create(this, musicUri);

        player.start();

    }

        @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.ibtnnext :
                player.release();
               // player = null;
                position = (int)(Math.random()*datas.size());
           vp.setCurrentItem(position);
                /*
                Uri musicUri = datas.get(position).uri;
                // 1. 플레이어 초기화
                player = MediaPlayer.create(this, musicUri);
                player.start();
                */

            break;
        }
    }

    private final int REQ_CODE = 100;
    // 1. 권한체크
    @TargetApi(Build.VERSION_CODES.M) // 타겟 지정 애너테이션
    private void checkPermission(){
        // 1.1 런타임 권한 체크
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ) {
            String permArr[] = {Manifest.permission.READ_EXTERNAL_STORAGE};

            requestPermissions(permArr, REQ_CODE);
        }else {
            init();
        }
    }
    // 2. 권한체크 후 콜백 < 사용자가 확인후 시스템이 호출하는 함수


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQ_CODE) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                init();
            } else {
                Toast.makeText(this, "권한을 허용하지 않으시면 프로그램을 실행할수 없습니다.", Toast.LENGTH_LONG).show();
                // 선택1 종료, 2 권한체크 다시 물어보기
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(player != null) {
            player.release(); // 사용이 끝나면 해제해야만 한다.
        }

    }


    // 페이지 변경 감지 메소드
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        player.release();
        position = vp.getCurrentItem();
        Uri musicUri = datas.get(position).uri;
        // 1. 플레이어 초기화
        player = MediaPlayer.create(this, musicUri);
        player.start();


    }

    @Override
    public void onPageScrollStateChanged(int state) {

      

    }
}
