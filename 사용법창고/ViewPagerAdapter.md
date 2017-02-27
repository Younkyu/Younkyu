###**ViewPagerAdapter**


 **class 따로 만들기**

	public class PagerAdapters extends FragmentStatePagerAdapter {
	
	
	    List<Fragment> fragments;
	
	    public PagerAdapters(FragmentManager fm) {
	        super(fm);
	        fragments = new ArrayList<>();
	    }
	
	    @Override
	    public Fragment getItem(int position) {
	
	        return fragments.get(position);
	    }
	
	    @Override
	    public int getCount() {
	        return fragments.size();
	    }
	
	    public void add(Fragment fragment) {
	        fragments.add(fragment);
	    }
	}


**main에서**

	        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
	
	        tabLayout.addTab(tabLayout.newTab().setText("Title").setIcon(R.mipmap.muse));
	        tabLayout.addTab(tabLayout.newTab().setText("Artist").setIcon(R.mipmap.saram));
	        tabLayout.addTab(tabLayout.newTab().setText("Album").setIcon(R.mipmap.btnran));
	        tabLayout.addTab(tabLayout.newTab().setText("Genre").setIcon(R.mipmap.dd));
	
	        ViewPager vp = (ViewPager) findViewById(R.id.vp);
	
	        // 아답터 생성
	        PagerAdapters adapter = new PagerAdapters(getSupportFragmentManager());
	
	        adapter.add(new TitleFragment());
	        adapter.add(new ArtistFragment());
	        adapter.add(new AlbumFragment());
	        adapter.add(new GenreFragment());
	
	        vp.setAdapter(adapter);
	
	        // 1. 페이저 리스너 - 페이저가 변경되었을 때 탭을 바꿔주는 리스너
	        vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
	        // 2. 탭이 변경되었을 때 페이지를 바꿔주는 리스너
	        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vp));
