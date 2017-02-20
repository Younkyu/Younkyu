package com.example.younkyu.teacher.batang;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.younkyu.teacher.MainActivity;
import com.example.younkyu.teacher.R;
import com.example.younkyu.teacher.manageran.GimFragment;
import com.example.younkyu.teacher.manageran.GorgiFragment;
import com.example.younkyu.teacher.manageran.MaSangdamFragment;
import com.example.younkyu.teacher.manageran.ManewsFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageranFragment extends Fragment {

    final int TAB_COUNT = 4;
    View view;
    GimFragment gim;
    GorgiFragment gorgi;
    ManewsFragment manews;
    MaSangdamFragment masanddam;



    public ManageranFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view =inflater.inflate(R.layout.fragment_manageran, container, false);

        gim = new GimFragment();
        gorgi = new GorgiFragment();
        manews = new ManewsFragment();
        masanddam = new MaSangdamFragment();

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tablayout2);
        tabLayout.addTab(tabLayout.newTab().setText("김과외 매니저란?"));
        tabLayout.addTab(tabLayout.newTab().setText("선생님 고르기"));
        tabLayout.addTab(tabLayout.newTab().setText("매니저 뉴스"));
        tabLayout.addTab(tabLayout.newTab().setText("상담하기"));

        //탭 아래 선택될때 컬러
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#65c8cd"));
        tabLayout.setTabTextColors(
                ContextCompat.getColor(getContext(), R.color.your_nonselected_text_color),
                ContextCompat.getColor(getContext(), R.color.your_selected_text_color)
        );

        //프레그먼트 페이저 작성
        ViewPager vp = (ViewPager) view.findViewById(R.id.vp2);

        // 아답터 생성
        PagerAdapter adapter = new PagerAdapter(getActivity().getSupportFragmentManager());
        vp.setAdapter(adapter);

        // 1. 페이저 리스너 - 페이저가 변경되었을 때 탭을 바꿔주는 리스너
        vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        // 2. 탭이 변경되었을 때 페이지를 바꿔주는 리스너
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vp));


        return view;
    }

    class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0 : fragment = gim; break;
                case 1 : fragment = gorgi; break;
                case 2 : fragment = manews; break;
                case 3 : fragment = masanddam; break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }
    }

}
