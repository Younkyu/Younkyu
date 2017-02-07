###**버전 문제해결법, multidex**

####**문제가 되는 경우**
 버전이 낮은 기기의 경우에 가끔 
Error:Execution failed for task ':app:transformClassesWithDexForDebug'. com.android.build.api.transform.TransformException: ... 와 같은 에러가 나타나는 경우가 있는데 이런 경우에 이것을 해결하기 위해서 multidex기능을 사용해야 한다.

**build gradle 설정**

	 defaultConfig에      
	        multiDexEnabled true
	 추가
	dependencies에
    compile 'com.android.support:multidex:1.0.0'
	추가

**Manifest**

	  <application에
	        android:name="android.support.multidex.MultiDexApplication"
	        추가

