###**Butterknife**

**build gradle에서**

	 compile 'com.jakewharton:butterknife:8.5.1'
	    annotationProcessor 'com.jakewharton:butterknife-compiler:8.5.1'


**main에서 온클릭을 사용할 때**

	ButterKnife.bind(this);
	->oncreate에서 꼭 해줘야함

	 @BindView(R.id.button)
	    Button button;
	
	    @BindView(R.id.textView)
	    TextView textView;

	 @OnClick
	    public void setTextView(View view) {
	        textView.setText("Hello Butterknife!");
	    }