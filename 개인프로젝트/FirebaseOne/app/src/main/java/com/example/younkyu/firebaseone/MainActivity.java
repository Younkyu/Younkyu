package com.example.younkyu.firebaseone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etTitle,etText;
    Button button;

    ListView listView;
    ListAdapter listAdapter;

    // Write a message to the database
    FirebaseDatabase database;
    DatabaseReference bbsRef;

    List<Bbs> datas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        bbsRef = database.getReference("bbs");

        listView = (ListView) findViewById(R.id.listView);
        listAdapter = new ListAdapter(datas, this);
        listView.setAdapter(listAdapter);

        etText = (EditText) findViewById(R.id.etText);
        etTitle = (EditText) findViewById(R.id.etTitle);

        button = (Button) findViewById(R.id.btn);

        button.setOnClickListener(this);

        bbsRef.addValueEventListener(postListener);

    }

    @Override
    public void onClick(View v) {
        String title = etTitle.getText().toString();
        String content = etText.getText().toString();

        //bbs 레퍼런스(테이블)에 키를 생성
        String key = bbsRef.push().getKey();

        // 2. 입력될 키,값 세트(레코드)를 생성
        Map<String, String> postValues = new HashMap<>();
        postValues.put("title",title);
        postValues.put("content",content);

        // 3. 생성된 레코드를 데이터베이스에 입력
        // bbs - 생성된키 - title : 값
        //                  content : 값
        // bbs - 생성된키2 - title : 값
        //                   content : 값

//        Map<String, Object> keyMap = new HashMap<>();
//        keyMap.put(key, postValues);
//
//        bbsRef.updateChildren(keyMap);

        DatabaseReference key_Ref = bbsRef.child(key);
        key_Ref.setValue(postValues);

    }

    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // Get Post object and use the values to update the UI
            //Post post = dataSnapshot.getValue(Post.class);
            // ...
            Log.w("aaaaaaaaaaaaaaaaaaa","data count= "+dataSnapshot.getChildrenCount());
            datas.clear();
            for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                String key = snapshot.getKey();
                Bbs bbs = snapshot.getValue(Bbs.class);
                bbs.key = key;

                datas.add(bbs);
            }
            listAdapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            // ...
            Log.w("aaaaaaaaaaaaaaaaaaa","data count= "+"13212515151");
        }
    };


}
