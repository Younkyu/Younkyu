### **애니메이션 ver.1**

	// 이동 애니메이션
	            Animation animation = new TranslateAnimation(0,0,-100,0);
	            // 나타났다 사라짐 애니메이션 
	            Animation animation1 = new AlphaAnimation(0,1);
	            //속도조절
	            animation.setDuration(300);
	            animation1.setDuration(300);
	            category_menu.setAnimation(animation);
	            category_menu.setAnimation(animation1);
	            category_menu.setVisibility(view.VISIBLE);