###**FireBase**

**선언**

	// 데이터베이스 연결
	    FirebaseDatabase database;
	    DatabaseReference roomRef;
	

**연결**

	// 데이터베이스 레퍼런스
	        database = FirebaseDatabase.getInstance();
	        roomRef = database.getReference("chat").child(key);


**리스너**
	
	 roomRef.addValueEventListener(eventListener);


	 // 채팅 목록 처리
	    ValueEventListener eventListener = new ValueEventListener() {
	        @Override
	        public void onDataChange(DataSnapshot dataSnapshot) {
	            datas.clear();
	            //   배열중에 한개          :  bbs 아래에 전체 데이터셋이 배열
	            for( DataSnapshot snapshot : dataSnapshot.getChildren() ){
	                String key = snapshot.getKey();
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

**데이터 넣기, 키생성**

	String msgKey = roomRef.push().getKey(); // 키생성과 동시에 가져오기
	            String msg = editMessage.getText().toString();
	
	            DatabaseReference msgRef = roomRef.child(msgKey);
	
	            Map<String, String> msgMap = new HashMap<>();
	            msgMap.put("userid",userid);
	            msgMap.put("username",username);
	            msgMap.put("msg",msg);
	
	            msgRef.setValue(msgMap); //map형태로 저장


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