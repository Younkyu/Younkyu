##**Fragment Bundle 사용**


#### **fragment끼리 값을 주고 받고 싶을 때**

**보내는쪽**

	Bundle bundle = new Bundle();
	                                    if(memo.getContent() != null) {
	                                        bundle.putString("gl", memo.getContent());
	                                    }
	                                    if(memo.getUri() != null) {
	                                        bundle.putString("uri", memo.getUri());
	                                    }
	                                    bundle.putInt("no", memo.getId());
	                                    wr.setArguments(bundle);
	
	
	                                    FragmentTransaction transaction = manager.beginTransaction();
	                                    transaction.add(R.id.fragment, wr); // add가 잘 안먹어서 replace 많이씀
	                                    // 4. 트랜잭션 전체를 스택에 저장을 합니다.
	                                    transaction.addToBackStack(null);
	                                    // 5. git의 commit과 같은 기능
	                                    transaction.commit();


**받는쪽**

	 @Override
	    public void onCreate(@Nullable Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	
	        if(getArguments() != null) {
	            sujung = getArguments().getString("gl");
	
	            sajin = getArguments().getString("uri");
	            no = getArguments().getInt("no");
	        }
	
	    }

