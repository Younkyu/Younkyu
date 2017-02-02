##**170202 - MediaPlayer, Viewpaser 사용법**

***
 어제는 노래 리스트 불러오기 작업을 했다면, 오늘은 노래를 직접 플레이 하고, 그 안에서 목록의 변화를 어떻게 하는지 알아보자. 먼저 MediaPlayer로 음원파일을 재생하고, 정지하고, 중지시킬 수 있고, ViewPaser를 이용하면 최신 음악 앱처럼 화면이 넘어가면, 그 화면에 해당하는 노래로 바뀌도록 프로그램 하는 것이다. 또한 다음노래 버튼을 누르면 다음 노래는 랜덤으로 나오도록 프로그래밍했다.

 소스코드는 [여기](https://github.com/Younkyu/Younkyu/tree/master/%EA%B0%9C%EC%9D%B8%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8/CustomMusicPlayer)에서 볼 수 있다. 여기에서 중요한것은, ViewPaser의 움직임을 감지하는 메소드를 찾는 것이었다. ViewPaser의 변화를 스크롤중에 감지된 것에서 노래를 바꿀 것인지, 전부 다 바뀐뒤에 감지해서 노래를 바꿀 것인지 시점을 선택할 수 있다. 또한, 이것은 랜덤으로 페이지가 바뀔 때도 똑같이 사용된다는 것이다. 따라서 음악을 재생하는 메소드를 버튼을 누를 때와, 손으로 넘길 때 둘다 설정해줘버리면 버튼을 눌렀을 때 노래가 두번 틀어지는 에러가 발생하였다.
 
###**MediaPlayer**
***
 미디어를 플레이하는 것이다 먼저 MediaPlayer player를 선언한 뒤에, create로 context와 미디어파일의 Uri를 가져와서 사용한다.
 
		  Uri musicUri = datas.get(position).uri;
		        // 1. 플레이어 초기화
		        player = MediaPlayer.create(this, musicUri);
		
		        player.start();

 또한 일시정지는 pause(); 정지는 stop(); 메모리에서 완전히 놓아버리는 것은 release();가 있다. 여기에서는 앱에서 뒤로 갈 때마다 노래를 꺼버릴 것이기 때문에, onDestroy를 오버라이드해서 거기에 항상 release();를 사용해주었다.

 ###**ViewPaser**
 ***
  ViewPaser를 사용하는 법은 어제 썼던 다른 리사이클뷰나, 리스트뷰와 다르지 않다. 선언하고, 데이터를 가져오고, 어댑터를 연결하면 된다. 
	
	   // 데이터 가져오기
	        datas = Data.get(this);
	
	        //뷰페이저 가져오기
	        vp = (ViewPager) findViewById(R.id.vp);
	
	        // 뷰 페이저용 아답터 생성
	        adapter = new CustomAdapter(datas, this);
	        // 아답터연결
	        vp.setAdapter(adapter);

 여기에서 재밌는 것은, ViewPaser의 움직임을 느낄 수 있는 메소드이다. addOnPageChangeListener메소드인데, 원래 OnPageChangeListener가 있었으나 이제는 더이상 쓰지 못하게 되고 세분화되어 나뉘게 되었다. addOnPageChangeListener를 쓰게 되면 몇가지를 강제로 오버라이드해야하는데, 그것은 다음의 예시와 함께 보면 좋다.

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

 여기에서 재밌는 것은 페이지가 얼마나 넘겨졌냐에 따라서 세분화하여 구분할 수 있다는 것이다. 내가 처음에 생각한것은 중간에 변화를 감지하면 변화를 감지하자마자 노래를 끄고 다시 다음 페이지의 노래를 트는 것이었는데, 사용해보니 성능이 좋지 않았다. 그래서 여기에서는 페이지가 완전히 변경되엇을 때 노래를 트는 방법을 사용하였다.