###**SharedPreference**

###**사용**

	public void saveSetting(View view) {
	        // 1. Preference 생성하기
	        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
	        // 2. SharedPreference에 값을 입력하기 위해서는 에디터를 통해서만 가능
	        SharedPreferences.Editor editor = sharedPref.edit();
	        //editor.putInt("키", "값");
	        editor.putString("email", editText.getText().toString());
	        editor.putBoolean("suffle",switchSuffle.isChecked());
	
	        // 3. 입력된 값을 반영
	        editor.commit();
	    }
	
	    public void loadSetting() {
	        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
	        String email = sharedPref.getString("email", null);
	        boolean shuffle = sharedPref.getBoolean("suffle", false);
	
	        editText.setText(email);
	        switchSuffle.setChecked(shuffle);
	    }