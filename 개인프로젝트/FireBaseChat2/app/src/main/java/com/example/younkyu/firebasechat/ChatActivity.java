package com.example.younkyu.firebasechat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvTitle;
    RecyclerView listView;
    EditText etSend;
    Button btnSend;
    String userid;
    String name;

    CustomAdapter adapter;
    List<Message> datas = new ArrayList<>();

    // Write a message to the database
    FirebaseDatabase database;
    DatabaseReference roomRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        listView = (RecyclerView) findViewById(R.id.listView2);
        etSend = (EditText)findViewById(R.id.etSend);
        btnSend = (Button) findViewById(R.id.btnSend);

        Intent intent = getIntent();
        String key = intent.getExtras().getString("key");
        String title = intent.getExtras().getString("title");
        userid = intent.getExtras().getString("id");
        name = intent.getExtras().getString("name");


        adapter = new CustomAdapter(this, datas, userid);
        listView.setAdapter(adapter);
        listView.setLayoutManager(new LinearLayoutManager(this));
        btnSend.setOnClickListener(this);

        tvTitle.setText(title);

        database = FirebaseDatabase.getInstance();
        roomRef = database.getReference("chat").child(key);
        Log.w("roomRef","===================key="+key+", roomRef="+roomRef);
        roomRef.addValueEventListener(listen);

    }

    ValueEventListener listen = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            datas.clear();
            for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                String key = snapshot.getKey();
                Log.w("Room","snapshot.key="+key+", getValue="+snapshot.getValue());
                Message msg = snapshot.getValue(Message.class);
                msg.setKey(key);
                datas.add(msg);
            }
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    @Override
    public void onClick(View v) {
        // 키 생성
        String msgkey = roomRef.push().getKey();
        String userName = name;
        String userId = userid;
        String msg = etSend.getText().toString();

        DatabaseReference msgRef = roomRef.child(msgkey);
        Map<String,String> msgMap = new HashMap<>();
        msgMap.put("userid",userId);
        msgMap.put("userName",userName);
        msgMap.put("msg",msg);
        msgRef.setValue(msgMap);

    }
}

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder> {

    List<Message> datas;
    Context context;
    String userid;

    public CustomAdapter(Context context, List<Message> datas, String userid) {

        this.context = context;
        this.datas = datas;
        this.userid = userid;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list, null);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        Message msg = datas.get(position);
        holder.userName.setText(msg.getUserName());
        holder.msg.setText(msg.getMsg());
        Log.w("ddddddddddddddd",msg.getUserid()+"dddddddddddddddd"+userid);

        if(userid.equals(msg.getUserid())) {
            holder.itemLayout.setGravity(Gravity.RIGHT);
            Log.w("aaaaaaaaaaaaa",msg.getUserid()+"dddddddddddddddd"+userid);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class Holder extends  RecyclerView.ViewHolder {

        LinearLayout itemLayout;
        TextView userName;
        TextView msg;

        public Holder(View itemView) {
            super(itemView);
            itemLayout= (LinearLayout) itemView.findViewById(R.id.liq);
            userName = (TextView) itemView.findViewById(R.id.tvUser);
            msg = (TextView) itemView.findViewById(R.id.tvMsg);
        }
    }

}