package com.example.younkyu.firebasechat;

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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GaipActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etID,etPass,etPassCon,etName;

    Button btngaip;

    String id, pass,passCon,name;

    FirebaseDatabase database;
    DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaip);

        etID = (EditText)findViewById(R.id.etID);
        etPass = (EditText)findViewById(R.id.etPass);
        etPassCon = (EditText)findViewById(R.id.etPassCon);
        etName = (EditText)findViewById(R.id.etName);
        btngaip = (Button)findViewById(R.id.btngaip);

        btngaip.setOnClickListener(this);

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("user");

    }

    @Override
    public void onClick(View v) {
        id = etID.getText().toString();
        pass = etPass.getText().toString();
        passCon = etPassCon.getText().toString();
        name = etName.getText().toString();

        if(!id.equals("") && !pass.equals("") && !passCon.equals("") && !name.equals("")) {
            userRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getChildrenCount() > 0) {
                        Toast.makeText(GaipActivity.this, "id가 이미 존재합니다.", Toast.LENGTH_LONG).show();
                    } else {
                        if(pass.equals(passCon) && pass.length() >= 4) {

                            Map<String,String> msgMap = new HashMap<>();
                            msgMap.put("name",name);
                            msgMap.put("password",pass);
                            Map<String,Object> keyMap = new HashMap<>();
                            keyMap.put(id, msgMap);
                            userRef.updateChildren(keyMap);

                            Toast.makeText(GaipActivity.this, "가입이 완료되었습니다.", Toast.LENGTH_LONG).show();
                            finish();

                        }else if(pass.length() < 4){
                            Toast.makeText(GaipActivity.this, "비밀번호는 4자리 이상이어야 합니다.", Toast.LENGTH_LONG).show();
                        } else if (!pass.equals(passCon) ) {
                            Toast.makeText(GaipActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            Toast.makeText(GaipActivity.this, "빈칸을 채우세요", Toast.LENGTH_LONG).show();
        }




    }
}
