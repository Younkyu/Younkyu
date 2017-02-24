package com.example.younkyu.raindrop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FrameLayout stagelayout;
    Button btnstart, btnpause, btnstop;

    int deviceWidth, deviceHeight;
    Stage stage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        deviceWidth = metrics.widthPixels;
        deviceHeight = metrics.heightPixels;
        stagelayout=(FrameLayout)findViewById(R.id.stage);
        btnstart = (Button) findViewById(R.id.btnstart);
        btnpause = (Button) findViewById(R.id.btnpause);
        btnstop = (Button) findViewById(R.id.btnstop);
        btnstart.setOnClickListener(this);
        btnpause.setOnClickListener(this);
        btnstop.setOnClickListener(this);
        stage = new Stage(this);
        stagelayout.addView(stage);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnstart :
                DrawStage drawStage = new DrawStage(stage);
                drawStage.start();
                MakeRain makeRain = new MakeRain(stage);
                makeRain.start();
                break;
            case R.id.btnpause :
                break;
            case R.id.btnstop :
                break;
        }
        stage.invalidate();
    }


    boolean running = true;

    class DrawStage extends Thread{
        Stage stage;
        public DrawStage(Stage stage){
            this.stage = stage;
        }

        public void run(){
            while(running){
                stage.postInvalidate();
                try {
                    Thread.sleep(10);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }


    class MakeRain extends Thread {
        boolean flag = true;
        Stage stage;
        public MakeRain(Stage stage) {
            this.stage = stage;
        }

        @Override
        public void run() {
            while(flag) {
                new Raindrop(stage);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    class Raindrop extends Thread {
        int x;
        int y;
        int radius;
        int speed;
        boolean stopflag = false;
        boolean pauseflag = false;
        Stage stage;

        public Raindrop(Stage stage) {
            Random random = new Random();
            x = random.nextInt(deviceWidth);
            y = 0;
            radius = random.nextInt(30)+5;
            speed = random.nextInt(3)+1;
            this.stage = stage;
            stage.addRaindrop(this);
        }

        @Override
        public void run() {
            while(!stopflag && y < deviceHeight+radius) {
                if(!pauseflag) {
                    y = y+1;
                    try {
                        Thread.sleep(10*speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(y > deviceHeight)
                    stopflag = true;

            }
            stage.removeRaindrop(this);
        }
    }

        class Stage extends View {

            List<Raindrop> raindrops;

            Paint rainColor;

            public Stage(Context context) {
            super(context);
            raindrops = new CopyOnWriteArrayList<>();
            rainColor = new Paint();
            rainColor.setColor(Color.BLUE);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            for(Raindrop raindrop : raindrops) {
                canvas.drawCircle(raindrop.x,raindrop.y,raindrop.radius,rainColor);
            }

        }

        public void addRaindrop(Raindrop raindrop) {
            raindrops.add(raindrop);
            raindrop.start();
        }

        public void removeRaindrop(Raindrop raindrop) {
            raindrops.remove(raindrop);
            raindrop.interrupt();
        }
    }



}
