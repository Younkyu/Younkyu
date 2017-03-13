package com.example.younkyu.firechat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RoomList extends AppCompatActivity {

    ListView listView;
    List<RoomObj> datas = new ArrayList<>();
    ListAdapter adapter;

    String userid;
    String name;

    // Write a message to the database
    FirebaseDatabase database;
    DatabaseReference bbsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        listView = (ListView) findViewById(R.id.listView);

        Intent intent = getIntent();
        name = intent.getExtras().getString("name");
        userid = intent.getExtras().getString("id");

        database = FirebaseDatabase.getInstance();
        bbsRef = database.getReference("room");

        adapter = new ListAdapter(datas, this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RoomObj roomObj = datas.get(position);
                Intent intent = new Intent(RoomList.this, Room.class);
                intent.putExtra("key",roomObj.getKey());
                intent.putExtra("title",roomObj.getTitle());
                intent.putExtra("id",userid);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });

        bbsRef.addValueEventListener(postListener);
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
                RoomObj room = new RoomObj();
                room.setKey(snapshot.getKey());
                room.setTitle(snapshot.getValue().toString());
                datas.add(room);
            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
            //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            // ...
            Log.w("aaaaaaaaaaaaaaaaaaa","data count= "+"13212515151");
        }
    };

    class ListAdapter extends BaseAdapter {

        Context context;
        List<RoomObj> datas;
        LayoutInflater inflater;

        public ListAdapter(List<RoomObj> datas, Context context) {
            this.context = context;
            this.datas = datas;
            this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = inflater.inflate(R.layout.item_room_list, null);
            }

            TextView tvTitle = (TextView) convertView.findViewById(R.id.textView);

            RoomObj roomObj = datas.get(position);
            tvTitle.setText(roomObj.title);

            return convertView;
        }
    }

}
