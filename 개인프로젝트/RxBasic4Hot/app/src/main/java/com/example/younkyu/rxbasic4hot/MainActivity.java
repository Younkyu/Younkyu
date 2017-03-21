package com.example.younkyu.rxbasic4hot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    ListView listView1, listView2;

    List<String> data1 = new ArrayList<>();
    List<String> data2 = new ArrayList<>();

    ArrayAdapter<String> adapter1;
    ArrayAdapter<String> adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView1 = (ListView)findViewById(R.id.listView1);
        listView2 = (ListView)findViewById(R.id.listVIew2);

        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,data1);
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,data2);

        listView1.setAdapter(adapter1);
        listView2.setAdapter(adapter2);

        Observable<String> hotObservable = Observable.create(emitter -> {
           for(int i = 0; i < 100 ; i++) {
               emitter.onNext("item="+i);
//               try {
//                   Thread.sleep(1000);
//               } catch (InterruptedException e) {
//                   e.printStackTrace();
//               }
           }
        });

        // 위에 작성한 Observable(발행자)에서 바로 발행을 시작
        hotObservable.subscribeOn(Schedulers.io()).publish();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 3초 후에 첫번째 구독자를 등록
        hotObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            data1.add(result);
                            adapter1.notifyDataSetChanged();
                        }
                );

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 3초 후에 첫번째 구독자를 등록
        hotObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            data2.add(result);
                            adapter2.notifyDataSetChanged();
                        }
                );

    }
}
