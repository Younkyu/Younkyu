package com.example.younkyu.customview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnup,btndown,btnright,btnleft;
    FrameLayout fr;

    private static final int GROUND_SIZE = 10;

    int player_x=0;
    int player_y=0;
    int unit = 0;

    int gong1x = 0;
    int gong1y = 0;
    int gong2x = 0;
    int gong2y = 0;
    int gong3x = 0;
    int gong3y = 0;
    int gong4x = 0;
    int gong4y = 0;
    int gong5x = 0;
    int gong5y = 0;
    int gong6x = 0;
    int gong6y = 0;

    int gongradius = 0;

    int player_radius= 0;

    CustomView view;

    final int map[][] = {
            {1,1,1,1,1,1,1,1,1,1},
            {1,0,0,1,1,0,0,0,1,1},
            {1,0,0,0,0,0,0,0,1,1},
            {1,0,0,1,1,1,0,0,1,1},
            {1,0,1,3,3,3,1,0,1,1},
            {1,0,1,3,3,3,1,0,1,1},
            {0,0,0,0,0,0,0,0,0,1},
            {0,0,0,0,0,1,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1},
            {1,1,1,1,1,1,1,1,1,1},
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fr = (FrameLayout) findViewById(R.id.fr);
        btndown = (Button) findViewById(R.id.btndown);
        btnup = (Button) findViewById(R.id.btnup);
        btnleft = (Button) findViewById(R.id.btnright);
        btnright = (Button) findViewById(R.id.btnleft);

        btndown.setOnClickListener(this);
        btnleft.setOnClickListener(this);
        btnright.setOnClickListener(this);
        btnup.setOnClickListener(this);

        init();

        view = new CustomView(this);
        fr.addView(view);

    }

    private void init() {

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        unit = metrics.widthPixels/GROUND_SIZE;
        player_radius= unit/2;
        gongradius = unit/2;

        gong1x = 1;
        gong1y = 3;

        gong2x = 4;
        gong2y = 2;

        gong3x = 1;
        gong3y = 6;

        gong4x = 7;
        gong4y = 3;

        gong5x = 4;
        gong5y = 6;

        gong6x = 7;
        gong6y = 6;
        
        player_x = 5;
        player_y = 6;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnup :
                if(player_y > 0 && !collisionCheck("up") && gongCheck("up"))
                {player_y = player_y - 1;}
                gameset();
                break;
            case R.id.btndown :
                if(player_y < GROUND_SIZE-1 && !collisionCheck("down") && gongCheck("down"))
                {player_y = player_y + 1;}
                gameset();
                break;
            case R.id.btnleft :
                if(player_x > 0  && !collisionCheck("left") && gongCheck("left"))
                {player_x = player_x - 1;}
                gameset();
                break;
            case R.id.btnright :
                if(player_x < GROUND_SIZE-1 && !collisionCheck("right") && gongCheck("right"))
                {player_x = player_x + 1;}
                gameset();
                break;
        }
        view.invalidate();
    }

    private boolean gongCheck(String direction) {
        boolean x = true;

        if(direction.equals("right")) {
            if(player_y == gong1y && player_x +1 == gong1x) {
                if(map[gong1y][gong1x+1]==1 || (gong1y == gong2y && gong1x+1 ==gong2x)
                        || (gong1y == gong3y && gong1x+1 ==gong3x)
                        || (gong1y == gong4y && gong1x+1 ==gong4x)
                        || (gong1y == gong5y && gong1x+1 ==gong5x)
                        || (gong1y == gong6y && gong1x+1 ==gong6x)
                        ) x = false;
                else {
                    gong1x = gong1x +1;
                }
            } else if(player_y == gong2y && player_x +1 == gong2x) {
                if(map[gong2y][gong2x+1]==1 || (gong2y == gong1y && gong2x+1 ==gong1x)
                        || (gong2y == gong3y && gong2x+1 ==gong3x)
                        || (gong2y == gong4y && gong2x+1 ==gong4x)
                        || (gong2y == gong5y && gong2x+1 ==gong5x)
                        || (gong2y == gong6y && gong2x+1 ==gong6x)
                        ) x = false;
                else {
                    gong2x = gong2x +1;
                }
            } else if(player_y == gong3y && player_x +1 == gong3x) {
                if(map[gong3y][gong3x+1]==1 || (gong3y == gong2y && gong3x+1 ==gong2x)
                        || (gong3y == gong1y && gong3x+1 ==gong1x)
                        || (gong3y == gong4y && gong3x+1 ==gong4x)
                        || (gong3y == gong5y && gong3x+1 ==gong5x)
                        || (gong3y == gong6y && gong3x+1 ==gong6x)
                        ) x = false;
                else {
                    gong3x = gong3x +1;
                }
            } else if(player_y == gong4y && player_x +1 == gong4x) {
                if(map[gong4y][gong4x+1]==1 || (gong4y == gong2y && gong4x+1 ==gong2x)
                        || (gong4y == gong3y && gong4x+1 ==gong3x)
                        || (gong4y == gong1y && gong4x+1 ==gong1x)
                        || (gong4y == gong5y && gong4x+1 ==gong5x)
                        || (gong4y == gong6y && gong4x+1 ==gong6x)
                        ) x = false;
                else {
                    gong4x = gong4x +1;
                }
            } else if(player_y == gong5y && player_x +1 == gong5x) {
                if(map[gong5y][gong5x+1]==1|| (gong5y == gong2y && gong5x+1 ==gong2x)
                        || (gong5y == gong3y && gong5x+1 ==gong3x)
                        || (gong5y == gong4y && gong5x+1 ==gong4x)
                        || (gong5y == gong1y && gong5x+1 ==gong1x)
                        || (gong5y == gong6y && gong5x+1 ==gong6x)
                        ) x = false;
                else {
                    gong5x = gong5x +1;
                }
            } else if(player_y == gong6y && player_x +1 == gong6x) {
                if(map[gong6y][gong6x+1]==1|| (gong6y == gong2y && gong6x+1 ==gong2x)
                        || (gong6y == gong3y && gong6x+1 ==gong3x)
                        || (gong6y == gong4y && gong6x+1 ==gong4x)
                        || (gong6y == gong5y && gong6x+1 ==gong5x)
                        || (gong6y == gong1y && gong6x+1 ==gong1x)
                        ) x = false;
                else {
                    gong6x = gong6x +1;
                }
            }
        }

        if(direction.equals("left")) {
            if(player_y == gong1y && player_x -1 == gong1x) {
                if(map[gong1y][gong1x-1]==1 || (gong1y == gong2y && gong1x-1 ==gong2x)
                        || (gong1y == gong3y && gong1x-1 ==gong3x)
                        || (gong1y == gong4y && gong1x-1 ==gong4x)
                        || (gong1y == gong5y && gong1x-1 ==gong5x)
                        || (gong1y == gong6y && gong1x-1 ==gong6x)
                        ) x = false;
                else {
                    gong1x = gong1x -1;
                }
            } else if(player_y == gong2y && player_x -1 == gong2x) {
                if(map[gong2y][gong2x-1]==1 || (gong2y == gong1y && gong2x-1 ==gong1x)
                        || (gong2y == gong3y && gong2x-1 ==gong3x)
                        || (gong2y == gong4y && gong2x-1 ==gong4x)
                        || (gong2y == gong5y && gong2x-1 ==gong5x)
                        || (gong2y == gong6y && gong2x-1 ==gong6x)
                        ) x = false;
                else {
                    gong2x = gong2x -1;
                }
            } else if(player_y == gong3y && player_x -1 == gong3x) {
                if(map[gong3y][gong3x-1]==1 || (gong3y == gong2y && gong3x-1 ==gong2x)
                        || (gong3y == gong1y && gong3x-1 ==gong1x)
                        || (gong3y == gong4y && gong3x-1 ==gong4x)
                        || (gong3y == gong5y && gong3x-1 ==gong5x)
                        || (gong3y == gong6y && gong3x-1 ==gong6x)
                        ) x = false;
                else {
                    gong3x = gong3x -1;
                }
            } else if(player_y == gong4y && player_x -1 == gong4x) {
                if(map[gong4y][gong4x-1]==1 || (gong4y == gong2y && gong4x-1 ==gong2x)
                        || (gong4y == gong3y && gong4x-1 ==gong3x)
                        || (gong4y == gong1y && gong4x-1 ==gong1x)
                        || (gong4y == gong5y && gong4x-1 ==gong5x)
                        || (gong4y == gong6y && gong4x-1 ==gong6x)
                        ) x = false;
                else {
                    gong4x = gong4x -1;
                }
            } else if(player_y == gong5y && player_x -1 == gong5x) {
                if(map[gong5y][gong5x-1]==1|| (gong5y == gong2y && gong5x-1 ==gong2x)
                        || (gong5y == gong3y && gong5x-1 ==gong3x)
                        || (gong5y == gong4y && gong5x-1 ==gong4x)
                        || (gong5y == gong1y && gong5x-1 ==gong1x)
                        || (gong5y == gong6y && gong5x-1 ==gong6x)
                        ) x = false;
                else {
                    gong5x = gong5x -1;
                }
            } else if(player_y == gong6y && player_x -1 == gong6x) {
                if(map[gong6y][gong6x-1]==1|| (gong6y == gong2y && gong6x-1 ==gong2x)
                        || (gong6y == gong3y && gong6x-1 ==gong3x)
                        || (gong6y == gong4y && gong6x-1 ==gong4x)
                        || (gong6y == gong5y && gong6x-1 ==gong5x)
                        || (gong6y == gong1y && gong6x-1 ==gong1x)
                        ) x = false;
                else {
                    gong6x = gong6x -1;
                }
            }
        }

        if(direction.equals("down")) {
            if(player_y+1 == gong1y && player_x == gong1x) {
                if(map[gong1y+1][gong1x]==1 || (gong1y+1 == gong2y && gong1x ==gong2x)
                        || (gong1y+1 == gong3y && gong1x ==gong3x)
                        || (gong1y+1 == gong4y && gong1x ==gong4x)
                        || (gong1y+1 == gong5y && gong1x ==gong5x)
                        || (gong1y+1 == gong6y && gong1x ==gong6x)
                        ) x = false;
                else {
                    gong1y = gong1y +1;
                }
            } else if(player_y +1 == gong2y && player_x == gong2x) {
                if(map[gong2y+1][gong2x]==1 || (gong2y+1 == gong1y && gong2x ==gong1x)
                        || (gong2y+1 == gong3y && gong2x ==gong3x)
                        || (gong2y+1 == gong4y && gong2x ==gong4x)
                        || (gong2y+1 == gong5y && gong2x ==gong5x)
                        || (gong2y+1 == gong6y && gong2x ==gong6x)
                        ) x = false;
                else {
                    gong2y = gong2y +1;
                }
            } else if(player_y +1 == gong3y && player_x == gong3x) {
                if(map[gong3y+1][gong3x]==1 || (gong3y+1 == gong2y && gong3x ==gong2x)
                        || (gong3y+1 == gong1y && gong3x ==gong1x)
                        || (gong3y+1 == gong4y && gong3x ==gong4x)
                        || (gong3y+1 == gong5y && gong3x ==gong5x)
                        || (gong3y+1 == gong6y && gong3x ==gong6x)
                        ) x = false;
                else {
                    gong3y = gong3y +1;
                }
            } else if(player_y +1 == gong4y && player_x == gong4x) {
                if(map[gong4y+1][gong4x]==1 || (gong4y+1 == gong2y && gong4x ==gong2x)
                        || (gong4y+1 == gong3y && gong4x ==gong3x)
                        || (gong4y+1 == gong1y && gong4x ==gong1x)
                        || (gong4y+1 == gong5y && gong4x ==gong5x)
                        || (gong4y+1 == gong6y && gong4x ==gong6x)
                        ) x = false;
                else {
                    gong4y = gong4y +1;
                }
            } else if(player_y +1 == gong5y && player_x == gong5x) {
                if(map[gong5y+1][gong5x]==1|| (gong5y+1 == gong2y && gong5x ==gong2x)
                        || (gong5y+1 == gong3y && gong5x ==gong3x)
                        || (gong5y+1 == gong4y && gong5x ==gong4x)
                        || (gong5y+1 == gong1y && gong5x ==gong1x)
                        || (gong5y+1 == gong6y && gong5x ==gong6x)
                        ) x = false;
                else {
                    gong5y = gong5y +1;
                }
            } else if(player_y +1 == gong6y && player_x == gong6x) {
                if(map[gong6y+1][gong6x]==1|| (gong6y+1 == gong2y && gong6x ==gong2x)
                        || (gong6y+1 == gong3y && gong6x ==gong3x)
                        || (gong6y+1 == gong4y && gong6x ==gong4x)
                        || (gong6y+1 == gong5y && gong6x ==gong5x)
                        || (gong6y+1 == gong1y && gong6x ==gong1x)
                        ) x = false;
                else {
                    gong6y = gong6y +1;
                }
            }
        }

        if(direction.equals("up")) {
            if(player_y-1 == gong1y && player_x == gong1x) {
                if(map[gong1y-1][gong1x]==1 || (gong1y-1 == gong2y && gong1x ==gong2x)
                        || (gong1y-1 == gong3y && gong1x ==gong3x)
                        || (gong1y-1 == gong4y && gong1x ==gong4x)
                        || (gong1y-1 == gong5y && gong1x ==gong5x)
                        || (gong1y-1 == gong6y && gong1x ==gong6x)
                        ) x = false;
                else {
                    gong1y = gong1y -1;
                }
            } else if(player_y -1 == gong2y && player_x == gong2x) {
                if(map[gong2y-1][gong2x]==1 || (gong2y-1 == gong1y && gong2x ==gong1x)
                        || (gong2y-1 == gong3y && gong2x ==gong3x)
                        || (gong2y-1 == gong4y && gong2x ==gong4x)
                        || (gong2y-1 == gong5y && gong2x ==gong5x)
                        || (gong2y-1 == gong6y && gong2x ==gong6x)
                        ) x = false;
                else {
                    gong2y = gong2y -1;
                }
            } else if(player_y -1 == gong3y && player_x == gong3x) {
                if(map[gong3y-1][gong3x]==1 || (gong3y-1 == gong2y && gong3x ==gong2x)
                        || (gong3y-1 == gong1y && gong3x ==gong1x)
                        || (gong3y-1 == gong4y && gong3x ==gong4x)
                        || (gong3y-1 == gong5y && gong3x ==gong5x)
                        || (gong3y-1 == gong6y && gong3x ==gong6x)
                        ) x = false;
                else {
                    gong3y = gong3y -1;
                }
            } else if(player_y -1 == gong4y && player_x == gong4x) {
                if(map[gong4y-1][gong4x]==1 || (gong4y-1 == gong2y && gong4x ==gong2x)
                        || (gong4y-1 == gong3y && gong4x ==gong3x)
                        || (gong4y-1 == gong1y && gong4x ==gong1x)
                        || (gong4y-1 == gong5y && gong4x ==gong5x)
                        || (gong4y-1 == gong6y && gong4x ==gong6x)
                        ) x = false;
                else {
                    gong4y = gong4y -1;
                }
            } else if(player_y -1 == gong5y && player_x == gong5x) {
                if(map[gong5y-1][gong5x]==1|| (gong5y-1 == gong2y && gong5x ==gong2x)
                        || (gong5y-1 == gong3y && gong5x ==gong3x)
                        || (gong5y-1 == gong4y && gong5x ==gong4x)
                        || (gong5y-1 == gong1y && gong5x ==gong1x)
                        || (gong5y-1 == gong6y && gong5x ==gong6x)
                        ) x = false;
                else {
                    gong5y = gong5y -1;
                }
            } else if(player_y -1 == gong6y && player_x == gong6x) {
                if(map[gong6y-1][gong6x]==1|| (gong6y-1 == gong2y && gong6x ==gong2x)
                        || (gong6y-1 == gong3y && gong6x ==gong3x)
                        || (gong6y-1 == gong4y && gong6x ==gong4x)
                        || (gong6y-1 == gong5y && gong6x ==gong5x)
                        || (gong6y-1 == gong1y && gong6x ==gong1x)
                        ) x = false;
                else {
                    gong6y = gong6y -1;
                }
            }
        }

        return x;
    }



    private boolean collisionCheck(String direction) {
        if(direction.equals("right")) {
            if(map[player_y][player_x+1] == 1) {
                return true;
            }
        }

        if(direction.equals("left")) {
            if(map[player_y][player_x-1] == 1) {
                return true;
            }
        }

        if(direction.equals("up")) {
            if(map[player_y-1][player_x] == 1) {
                return true;
            }
        }

        if(direction.equals("down")) {
            if(map[player_y+1][player_x] == 1) {
                return true;
            }
        }


        return false;
    }

    class CustomView extends View {

        //#온드로우 함수에서 그림그리기
        // 1. 색상을 정의
        Paint magenta = new Paint();
        Paint black = new Paint();
        Paint yellow = new Paint();
        Paint blue = new Paint();
        Paint green = new Paint();

        public CustomView(Context context) {
            super(context);
            yellow.setColor(Color.YELLOW);
            blue.setColor(Color.BLUE);
            magenta.setColor(Color.MAGENTA);
            black.setColor(Color.BLACK);
            green.setColor(Color.GREEN);
        }

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

    }

    private void gameset() {
        if((gong1x >=3 && gong1y >=4 && gong1y <=5 && gong1x <=5)
                && (gong2x >=3 && gong2y >=4 && gong2y <=5 && gong2x <=5)
                && (gong3x >=3 && gong3y >=4 && gong3y <=5 && gong3x <=5)
                && (gong4x >=3 && gong4y >=4 && gong4y <=5 && gong4x <=5)
                && (gong5x >=3 && gong5y >=4 && gong5y <=5 && gong5x <=5)
                && (gong6x >=3 && gong6y >=4 && gong6y <=5 && gong6x <=5)
               ) {
            Intent intent = new Intent(this, EndingActivity.class);
            startActivity(intent);
        }
    }

}


