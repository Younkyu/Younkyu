###**FireBase - id 확인**

**db연결**

	FirebaseDatabase database;
	   DatabaseReference userRef;
	
	database = FirebaseDatabase.getInstance();
	       userRef = database.getReference("user");


**id, password확인**

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
	                                Intent intent = new Intent(MainActivity.this, RoomListActivity.class);
	                                intent.putExtra("id",id);
	                                intent.putExtra("name",name);
	                                startActivity(intent);
	                            } else {
	                                Toast.makeText(MainActivity.this, "패스워드가 틀림", Toast.LENGTH_LONG).show();
	                            }
	                        } else {
	                            Toast.makeText(MainActivity.this, "user id가 없습니다.", Toast.LENGTH_LONG).show();
	                        }