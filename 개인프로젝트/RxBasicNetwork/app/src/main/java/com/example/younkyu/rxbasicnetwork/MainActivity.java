package com.example.younkyu.rxbasicnetwork;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    TextView textView, textView2, textView3, textView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView);
        textView2 = (TextView)findViewById(R.id.textView2);
        textView3 = (TextView)findViewById(R.id.textView3);
        textView4 = (TextView)findViewById(R.id.textView4);


        Observable<String> simpleObservable =
                Observable.create(emitter -> {
                    emitter.onNext(Remote.getUrl("naver.com"));
                });

        Observable<String> cnetObservable =
                Observable.create(emitter -> {
                    emitter.onNext(Remote.getUrl("www.cnet.co.kr"));
                });

        cnetObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {textView.setText(result);}
                );

        simpleObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                  result -> {textView3.setText(result);}
                );

    }
}
