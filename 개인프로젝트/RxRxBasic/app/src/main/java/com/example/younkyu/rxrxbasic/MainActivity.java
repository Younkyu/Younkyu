package com.example.younkyu.rxrxbasic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    TextView textView,textView2,textView3;
    public static final String TAG = "MainAc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView);
        textView2 = (TextView)findViewById(R.id.textView2);
        textView3 = (TextView)findViewById(R.id.textView3);

        Observable<String> simpleObservable =
                Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        // 네트웍을 통해서 데이터를 긁어온다
                        // 반복문을 돌면서 ====
                        // for(네트웍에서 가져온데이터)
                        for(int i = 0 ; i < 3 ; i++) {
                            subscriber.onNext("Hello RxAndroid !!"+i);// onNext로 구독자들에게 데이터를 보내준다.
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        subscriber.onCompleted();
                    }
                });

        simpleObservable
                .subscribeOn(Schedulers.io()) // 발행자를 별도의 thread에서 동작 시킨다.
                .observeOn(AndroidSchedulers.mainThread()) // 구독자를 mainThread에서 동작시킨다.
                .subscribe(new Subscriber<String>() { // Observer(구독자)
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
                        textView.setText("[1]"+text);
                    }
                });

        // 옵저버를 등록하는 함수 - 진화형(각 함수ㅡㄹ 하나의 콜백객체에 나눠서 담아준다.
        simpleObservable
                .subscribeOn(Schedulers.io()) // 발행자를 별도의 thread에서 동작 시킨다.
                .observeOn(AndroidSchedulers.mainThread()) // 구독자를 mainThread에서 동작시킨다.
                .subscribe(new Action1<String>() { // onNext함수와 동일한 역할을 하는 콜백객체
            @Override
            public void call(String s) {
                textView2.setText("[2]"+s);
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
        simpleObservable
                .subscribeOn(Schedulers.io()) // 발행자를 별도의 thread에서 동작 시킨다.
                .observeOn(AndroidSchedulers.mainThread()) // 구독자를 mainThread에서 동작시킨다.
                .subscribe(
                (string) -> {textView3.setText("[3]"+ string);}
                ,(error)-> {Log.e(TAG, "[3]"+"error: " + error.getMessage());}
                ,() -> {Log.d(TAG, "[3]"+"complete!");}
        );
    }


    }

