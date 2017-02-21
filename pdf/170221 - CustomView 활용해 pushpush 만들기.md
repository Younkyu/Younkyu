###**170221 - CustomView 활용해 pushpush 만들기**

####**새로운 class를 만들고 View 상속**

	class CustomView extends View {
	
	       public CustomView(Context context) {
	           super(context);
	          
	       }

####**메인에서 불러오기**
	
	View view = new CustomView(this);
	        fr.addView(view); // 프레임 레이아웃에 추가


####**View 초기화**
	
	view.invalidate();

####**화면 크기 구하기**

	DisplayMetrics metrics = getResources().getDisplayMetrics();
	        unit = metrics.widthPixels/GROUND_SIZE;

####**View에서 화면에 그림그리기**

	@Override
	        protected void onDraw(Canvas canvas) {
	            super.onDraw(canvas);
	            // 맵그리기
	            for(int i = 0 ; i <map.length ; i ++) {
	                for(int j=0 ; j<map[0].length ; j ++) {
	                    if(map[i][j] ==1) {
	                        canvas.drawRect(unit*j,unit*i, unit*(j+1), unit*(i+1),black);
	                    }
	                    if(map[i][j] == 3) {
	                        canvas.drawRect(unit*j,unit*i, unit*(j+1), unit*(i+1),yellow);
	                    }
	                }
	            }
	            // 공그리기
	            canvas.drawCircle(gong1x*unit+player_radius,gong1y*unit+player_radius,gongradius,blue);
	            canvas.drawCircle(gong2x*unit+player_radius,gong2y*unit+player_radius,gongradius,blue);
	            canvas.drawCircle(gong3x*unit+player_radius,gong3y*unit+player_radius,gongradius,blue);
	            canvas.drawCircle(gong4x*unit+player_radius,gong4y*unit+player_radius,gongradius,blue);
	            canvas.drawCircle(gong5x*unit+player_radius,gong5y*unit+player_radius,gongradius,blue);
	            canvas.drawCircle(gong6x*unit+player_radius,gong6y*unit+player_radius,gongradius,blue);
	            // 2. canvas에 캐릭터 그리기
	            canvas.drawCircle(player_x*unit+player_radius,player_y*unit+player_radius,player_radius,magenta);
	            canvas.drawRect(player_x*unit+player_radius/5,player_y*unit+player_radius/5,player_x*unit+player_radius,player_y*unit+player_radius, green);
	            canvas.drawRect(player_x*unit+player_radius/5+player_radius,player_y*unit+player_radius/5,player_x*unit+player_radius+player_radius,player_y*unit+player_radius, green);
	            canvas.drawLine(player_x*unit+player_radius,
	                    player_y*(unit)+player_radius*3/2,
	                    player_x*(unit)+player_radius*3/2,
	                    player_y*(unit)+player_radius*3/2,
	                    black);
	
	
	        }

