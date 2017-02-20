package com.example.younkyu.teacher;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.younkyu.teacher.batang.GesiFragment;
import com.example.younkyu.teacher.batang.ManageranFragment;
import com.example.younkyu.teacher.batang.MoreFragment;
import com.example.younkyu.teacher.batang.MuniFragment;
import com.example.younkyu.teacher.batang.TeachFragment;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    //권한 요청 코드
    public static final int REQ_CODE = 100;
    final int TAB_COUNT = 5;
    ManageranFragment mana;
    TeachFragment teach;
    GesiFragment gesi;
    MoreFragment more;
    MuniFragment muni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setTab ();
        checkPermission();

    }

    private void init() {

    }


    private void setTab () {
        mana = new ManageranFragment();
        teach = new TeachFragment();
        gesi = new GesiFragment();
        more = new MoreFragment();
        muni = new MuniFragment();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("매니저란?").setIcon(R.mipmap.manager));
        tabLayout.addTab(tabLayout.newTab().setText("선생님").setIcon(R.mipmap.teacherh));
        tabLayout.addTab(tabLayout.newTab().setText("게시판").setIcon(R.mipmap.gesih));
        tabLayout.addTab(tabLayout.newTab().setText("문의").setIcon(R.mipmap.munih));
        tabLayout.addTab(tabLayout.newTab().setText("더보기").setIcon(R.mipmap.moreh));
        //탭 아래 선택될때 컬러
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#65c8cd"));
        tabLayout.setTabTextColors(
                ContextCompat.getColor(this, R.color.your_nonselected_text_color),
                ContextCompat.getColor(this, R.color.your_selected_text_color)
        );

        // 아이콘 색상 바꾸기
        tabLayout.addOnTabSelectedListener(this);



        //프레그먼트 페이저 작성
        ViewPager vp = (ViewPager) findViewById(R.id.vp);

        // 아답터 생성
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);

        // 1. 페이저 리스너 - 페이저가 변경되었을 때 탭을 바꿔주는 리스너
        vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        // 2. 탭이 변경되었을 때 페이지를 바꿔주는 리스너
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vp));
    }

    private void checkPermission() {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            if(Permission_Control.checkPermission(this, REQ_CODE)) {
                init(); // 프로그램실행
            }
        } else {
            init(); // 사실상 여기에서 main 시작
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQ_CODE) {
            if(Permission_Control.onCheckResult(grantResults)) {
                init();
            } else {
                Toast.makeText(this, "권한을 허용하지 않으시면 프로그램을 실행할수 없습니다.", Toast.LENGTH_LONG).show();
                // 선택1 종료, 2 권한체크 다시 물어보기
                finish();
            }
        }

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch(tab.getPosition()) {
            case 0 :
                tab.setIcon(R.mipmap.manager);
                break;
            case 1 :
                tab.setIcon(R.mipmap.teacher);
                break;
            case 2 :
                tab.setIcon(R.mipmap.gesi);
                break;
            case 3 :
                tab.setIcon(R.mipmap.muni);
                break;
            case 4 :
                tab.setIcon(R.mipmap.more);
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        switch(tab.getPosition()) {
            case 0 :
                tab.setIcon(R.mipmap.managerh);
                break;
            case 1 :
                tab.setIcon(R.mipmap.teacherh);
                break;
            case 2 :
                tab.setIcon(R.mipmap.gesih);
                break;
            case 3 :
                tab.setIcon(R.mipmap.munih);
                break;
            case 4 :
                tab.setIcon(R.mipmap.moreh);
                break;
        }

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0 : fragment = mana; break;
                case 1 : fragment = teach; break;
                case 2 : fragment = gesi; break;
                case 3 : fragment = muni; break;
                case 4 : fragment = more; break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }
    }
}


