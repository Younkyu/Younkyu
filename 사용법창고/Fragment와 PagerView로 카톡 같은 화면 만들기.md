###**Fragment와 PagerView로 카톡 같은 화면 만들기**

 **준비할 것**
  Fragment Activity 4개
  ViewPager(메인 액티비티에)

###**MainActivity**

**선언**
	
	 // 탭 및 페이저 속성 정의
	    final int TAB_COUNT = 4;
	    OneFragment one;
	    TwoFragment two;
	    ThreeFragment three;
	    FourFragment four;
**create view**

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

####**주의 해야할 점**
 Fragment는 create하면 return으로 레이아웃 인플레이터를 받기 때문에 사용할 때 이것에 주의해서 사용해야 한다.
  예시
	   @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                             Bundle savedInstanceState) {
	        // Inflate the layout for this fragment
	        view = inflater.inflate(R.layout.fragment_two, container, false);
	        return view;
와 같은 방식으로 사용해야 한다.