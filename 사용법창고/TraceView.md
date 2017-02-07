###**TraceView**

>우리가 앱을 사용할 때 어떤 함수에서 얼마나 시간을 쓰느냐, 즉 어떤 함수가 얼마나 무거운지 알고 싶을 때 TraceView를 사용해서 확인 할 수 있습니다.


**준비물**
  external storage write 권한 설정, [권한설정 방법]()

**사용법**
 체크 시작 지점에서  Debug.startMethodTracing("파일이름");
 체크 완료 지점에서  Debug.stopMethodTracing();

 을 하면 핸드폰에 "파일이름".trace로 파일이 생성된 것을 볼 수 있을 것이다. 
 가장 간단하게 사용하는 방법은 trace 파일을 자신이 원하는 경로에 복사해서 붙여넣기 한뒤, (윈도우의 경우) cmd로 켜서 adb([adb란?](https://developer.android.com/studio/command-line/adb.html?hl=ko))가 실행되는지 본 후, traceview 경로+파일이름.trace를 하면 실행되는 것을 확인할 수 있다. 

![](http://cfile2.uf.tistory.com/image/20307F374F3B717502C9A4)

 이런 화면을 통해서 확인 할 수 있다. [출처:http://aroundck.tistory.com/183](http://aroundck.tistory.com/183)