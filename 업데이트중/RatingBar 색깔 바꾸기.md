### **RatingBar 색깔 바꾸기**

	//레이팅바의 색깔을 바꿔야 할 경우에 사용
	        LayerDrawable stars = (LayerDrawable) holder.ratingBar.getProgressDrawable();
	        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);