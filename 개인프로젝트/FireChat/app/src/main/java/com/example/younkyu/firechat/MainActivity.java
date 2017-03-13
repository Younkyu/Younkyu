package com.example.younkyu.firechat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText editText,editText2;
    Button button;

    // Write a message to the database
    FirebaseDatabase database;
    DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);

        button = (Button)findViewById(R.id.button);

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("user");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = editText.getText().toString();
                final String pw = editText2.getText().toString();

                // userRef.child(id)에 대한 callBack
                userRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getChildrenCount() > 0) {
                            String fbPw = dataSnapshot.child("password").getValue().toString();
                            String name = dataSnapshot.child("name").getValue().toString();
                            Log.w("mainactiviy","pw =" + fbPw);
                            if(fbPw.equals(pw)) {
                                    Intent intent = new Intent(MainActivity.this, RoomList.class);
                                    intent.putExtra("userid",id);
                                intent.putExtra("name",name);
                                    startActivity(intent);
                            } else {
                                Toast.makeText(MainActivity.this, "패스워드가 틀림", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "user id가 없습니다.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

    }
}
