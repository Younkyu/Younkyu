### **Background 이미지 glide처리**

	//백그라운드 이미지를 glide처리하고 싶을 때 사용
	Glide.with(context).load(R.drawable.list_dummy).thumbnail(0.1f).into(new ViewTarget<ConstraintLayout, GlideDrawable>(holder.itemback) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation anim) {
                ConstraintLayout myView = this.view;
                // Set your resource on myView and/or start your animation here.
                myView.setBackground(resource);
            }
        });