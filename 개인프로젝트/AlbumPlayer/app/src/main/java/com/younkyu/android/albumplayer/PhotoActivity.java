package com.younkyu.android.albumplayer;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

public class PhotoActivity extends AppCompatActivity implements View.OnClickListener {


    ViewPager viewpager;
    ImageButton ibtnd, ibtna, ibtnr;
    ArrayList<Photo> datas;
    PagerAdapter adapter;

    int position = 0; // 현재 위치

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        // 데이터 가져오기
        datas = Checkdata.get(this);

//        int i = 0;
//        while(i<datas.size()) {
//            if(datas.get(i).mola == null) {
//                datas.remove(i);
//            }
//            i++;
//        }
        viewpager = (ViewPager) findViewById(R.id.vp);

        // 뷰 페이저용 아답터 생성
        adapter = new PagerAdapter(datas, this);
        // 아답터연결
        viewpager.setAdapter(adapter);

        //뷰페이저 리스너 제작
       // viewpager.addOnPageChangeListener(this);

//        // 특정 페이지 호출
//        Intent intent = getIntent();
//        if(intent != null) {
//            Bundle bundle = intent.getExtras();
//            if(bundle != null) {
//                position = bundle.getInt("position");
//            }
//        }

        ibtna = (ImageButton) findViewById(R.id.ibtna);
        ibtnd = (ImageButton) findViewById(R.id.ibtnd);
        ibtnr = (ImageButton) findViewById(R.id.ibtnr);

        ibtna.setOnClickListener(this);
        ibtnd.setOnClickListener(this);
        ibtnr.setOnClickListener(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        Checkdata.remove(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtna :
                if(position < datas.size()-1) {
                    position = position +1;
                    viewpager.setCurrentItem(position);
                }
                break;
            case R.id.ibtnd :
                if(position > 0 ) {
                    position = position -1;
                    viewpager.setCurrentItem(position);
                }
                break;
            case R.id.ibtnr :
                position = (int)(Math.random()*datas.size());
                viewpager.setCurrentItem(position);
                break;
        }

    }
}
