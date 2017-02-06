##**170206 - Fragment**
***

####**fragment란?**
***
 Fragment는 간단히 말해서 조각이다. 어떤 조각이냐면, 한 화면을 구성할 때 더 효율적으로 화면을 사용할 수 있는 조각이라고 이해할 수 있을 것이다. 

 fragment를 사용하는 것은 굉장히 편리하다. 프래그먼트는 너무나 자주 쓰이기 때문에, 카카오톡 같은 탭 화면 구성을 단 몇줄의 코드로 가능하게 하였다. 예제를 보면, 얼마나 간단한지 알 수 있을 것이다.


	 package com.younkyu.android.fragmenttap;
	
	import android.support.design.widget.TabLayout;
	import android.support.v4.app.Fragment;
	import android.support.v4.app.FragmentManager;
	import android.support.v4.app.FragmentStatePagerAdapter;
	import android.support.v4.view.ViewPager;
	import android.support.v7.app.AppCompatActivity;
	import android.os.Bundle;
	
	public class MainActivity extends AppCompatActivity {
	
	    // 탭 및 페이저 속성 정의
	    final int TAB_COUNT = 4;
	    OneFragment one;
	    TwoFragment two;
	    ThreeFragment three;
	    FourFragment four;
	
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	
	        // 프래그먼트 init
	        one = new OneFragment();
	        two = new TwoFragment();
	        three = new ThreeFragment();
	        four = new FourFragment();
	
	        //탭 layout 정의
	        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);
	        tabLayout.addTab(tabLayout.newTab().setText("계산기"));
	        tabLayout.addTab(tabLayout.newTab().setText("단위환산"));
	        tabLayout.addTab(tabLayout.newTab().setText("검색"));
	        tabLayout.addTab(tabLayout.newTab().setText("현재위치"));
	
	
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
	
	
	    class PagerAdapter extends FragmentStatePagerAdapter {
	
	        public PagerAdapter(FragmentManager fm) {
	            super(fm);
	        }
	
	        @Override
	        public Fragment getItem(int position) {
	            Fragment fragment = null;
	            switch (position) {
	                case 0 : fragment = one; break;
	                case 1 : fragment = two; break;
	                case 2 : fragment = three; break;
	                case 3 : fragment = four; break;
	            }
	            return fragment;
	        }
	
	        @Override
	        public int getCount() {
	            return TAB_COUNT;
	        }
	    }
	}
