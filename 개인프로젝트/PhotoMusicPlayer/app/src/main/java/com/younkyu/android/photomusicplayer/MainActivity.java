package com.younkyu.android.photomusicplayer;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //탭 세팅을 위한 선언
    final int TAB_COUNT = 2;
    PhotoFragment photo;
    MusicFragment music;

    //권한 요청 코드
    public static final int REQ_CODE = 100;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tapset();
        // 권한처리
        checkPermission();

    }

    private void init() {

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

    private void tapset() {
        //생성
        photo = new PhotoFragment();
        music = new MusicFragment();

        //탭 layout 정의
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.addTab(tabLayout.newTab().setText("Photo"));
        tabLayout.addTab(tabLayout.newTab().setText("Music"));

        //프레그먼트 페이저 작성
        ViewPager vp = (ViewPager) findViewById(R.id.ViewPager);

        // 아답터 생성
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);

        // 1. 페이저 리스너 - 페이저가 변경되었을 때 탭을 바꿔주는 리스너
        vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        // 2. 탭이 변경되었을 때 페이지를 바꿔주는 리스너
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vp));
    }

    class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0 : fragment = photo; break;
                case 1 : fragment = music; break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DataLoaderPhoto.removeData();
    }
}
