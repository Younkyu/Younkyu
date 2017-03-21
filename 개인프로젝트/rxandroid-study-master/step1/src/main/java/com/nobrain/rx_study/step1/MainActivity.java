package com.nobrain.rx_study.step1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private int data = 97;
    PublishSubject<Integer> subject;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Integer> datas = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
       subject = PublishSubject.create();
        subject
                .observeOn(Schedulers.computation())
                .map(data -> {
                    if(data%2 ==1) {
                        return ' ';
                    }
                    return ((char)('a' + data));
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(character -> tv.setText(tv.getText().toString() + character));

        tv = (TextView) findViewById(R.id.tv_main);

        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subject.onNext(data++);
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(subject != null) {
            subject.onComplete();
        }

    }
}

