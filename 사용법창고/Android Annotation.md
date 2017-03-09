###**Android Annotation**

 코드를 획기적으로 줄일 수 있는 방법

####**build gradle(App)에서**

**제일 위에**

	apply plugin: 'com.neenbedankt.android-apt'
	def AAVersion = '4.1.0' // 추가

**따로 만드는 것**

	dependencies {
    apt "org.androidannotations:androidannotations:$AAVersion"
    compile "org.androidannotations:androidannotations-api:$AAVersion"
    apt"org.androidannotations:rest-spring:$AAVersion"
    compile "org.androidannotations:rest-spring-api:$AAVersion"

    compile 'org.springframework.android:spring-android-rest-template:2.0.0.M3'
} // 따로 추가

**dependencies에서**

	compile 'org.androidannotations:androidannotations:4.1.0'



####**build gradle(Project dependencies)에서**

	buildscript {
	    repositories {
	        jcenter()
	    }
	    dependencies {
	        classpath 'com.android.tools.build:gradle:2.3.0'
	        classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8' // 이거 한줄만 추가




####**사용법**
	
**액티비티 받을 때**

	@EActivity(R.layout.bookmarks) // *manifest에서 레이아웃 뒤에 _만들어줌


**findViewbyid 대신**
	
	@ViewById
	  ListView bookmarkList; // id와 변수명이 같으면 인식

**스레드 돌리는 것 예제**

	public void getGoogle(View view){
	        String url="http://google.com";
	        runBackground(url);
	    }
	
	    // 백그라운드 thread 에서 동작
	    @Background
	    public void runBackground(String url){
	        String result = googleService.getData();
	        writeOnUi(result);
	    }
	
	    // 다시 Uithread 에서 동작되는 부분
	    @UiThread
	    public void writeOnUi(String result){
	        textView.setText(result);
	    }
	
	    // 아래 정의된 인터페이스를 사용
	    @RestService
	    Google googleService;
	
	}
	
	// Rest 애너테이션은 Top 레벨에서만 사용가능
	// 단일 Class 레벨에서만 사용가능
	@Rest(rootUrl = "http://www.google.com", converters = {StringHttpMessageConverter.class})
	interface Google {
	    @Get("/")
	    String getData();
	}


**공식 문서**
	
	http://androidannotations.org/