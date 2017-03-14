###**FireBase - id 생성**

**생성**


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



**데이터 변경 리스너**

	 // 사용자 추가후 결과처리
	        userRef.addChildEventListener(new ChildEventListener() {
	            @Override
	            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
	
	                String addedId = dataSnapshot.getKey();
	
	                if(addedId.equals(upid)){
	                    Toast.makeText(getBaseContext()
	                            , "등록되었습니다"
	                            , Toast.LENGTH_SHORT).show();
	
	                    upid = "";
	                    // 사용자 등록후 등록버튼 잠금
	                    layoutSignup.setVisibility(View.GONE);
	                    btnSignup.setEnabled(false);
	                }
	            }