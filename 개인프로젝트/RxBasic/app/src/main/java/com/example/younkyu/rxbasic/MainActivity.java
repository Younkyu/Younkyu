package com.example.younkyu.rxbasic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainAc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable<String> simpleObservable =
                Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        // 네트웍을 통해서 데이터를 긁어온다
                        // 반복문을 돌면서 ====
                        // for(네트웍에서 가져온데이터)
                        subscriber.onNext("Hello RxAndroid !!");
                        subscriber.onCompleted();
                    }
                });

        simpleObservable
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "[1]"+"complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "[1]"+"error: " + e.getMessage());
                    }

                    @Override
                    public void onNext(String text) {
                        Toast.makeText(MainActivity.this,"[1]", Toast.LENGTH_LONG).show();
                    }
                });

        // 옵저버를 등록하는 함수 - 진화형(각 함수ㅡㄹ 하나의 콜백객체에 나눠서 담아준다.
        simpleObservable.subscribe(new Action1<String>() { // onNext함수와 동일한 역할을 하는 콜백객체
            @Override
            public void call(String s) {
                Toast.makeText(MainActivity.this,"[2]"+s, Toast.LENGTH_LONG).show();
            }
        }, new Action1<Throwable>() { // onError 함수와 동일한 역할을 하는 콜백 객체
            @Override
            public void call(Throwable throwable) {
                Log.e(TAG, "[2]"+"error: " + throwable.getMessage());
            }
        }, new Action0() { // onComplete과 동일한 역할을 하는콜백 객체
            @Override
            public void call() {
                Log.d(TAG, "[2]"+"complete!");
            }
        });

        // 옵저버를 등록하는 함수 - 최종진화형(람다식)
        simpleObservable.subscribe(
                (string) -> {Toast.makeText(MainActivity.this,"[2]"+string, Toast.LENGTH_LONG).show();}
                ,(error)-> {Log.e(TAG, "[2]"+"error: " + error.getMessage());}
                ,() -> {Log.d(TAG, "[2]"+"complete!");}
        );
    }
}
