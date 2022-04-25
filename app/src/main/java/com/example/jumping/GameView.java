package com.example.jumping;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class GameView extends View {

    Handler handler;
    Runnable runnable;
    Bitmap background;
    Bitmap[] astro;
    Bitmap squares1;
    Bitmap squares2;
    Bitmap squares3;
    Random random;
    Paint paint;
    int deviceW = 1920;
    int deviceH = 1080;
    int update = 30;
    int choice = 0;
    int speed = 0;
    int gravity = 8;
    int astroX;
    int astroY;
    int counter = 0;
    double score = 0;
    boolean firstTouch = false;
    int numberOfSquares = 2;
    int hole = 2000;
    int[] squaresX = new int[numberOfSquares];
    int[] squaresX2 = new int[numberOfSquares];
    int[] squaresX3 = new int[numberOfSquares];
    int squaresY;
    int squaresY2;
    int squaresY3;
    int squaresSpeed = 50;
    boolean over = false;


    public GameView(Context context){
        super(context);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        paint = new Paint();
        random = new Random();
        background = BitmapFactory.decodeResource(getResources(),R.drawable.sky);
        astro = new Bitmap[2];
        astro[0] = BitmapFactory.decodeResource(getResources(), R.drawable.astro1);
        astro[1] = BitmapFactory.decodeResource(getResources(), R.drawable.astro2);
        squares1 = BitmapFactory.decodeResource(getResources(),R.drawable.square);
        squares2 = BitmapFactory.decodeResource(getResources(),R.drawable.duo);
        squares3 = BitmapFactory.decodeResource(getResources(),R.drawable.triple);

        squaresY = 786- squares1.getHeight();
        squaresY2 = 786- squares2.getHeight();
        squaresY3 = 786- squares3.getHeight();


        for(int i = 0; i< numberOfSquares; i++){
            squaresX[i] = deviceW +  (random.nextInt(4))*hole;
            squaresX2[i] = deviceW + (random.nextInt(3))*3000;
            squaresX3[i] = deviceW + (random.nextInt(3))*6000;
        }
        astroX = 250;
        astroY = 200;
    }
    @Override
    public void onDraw(Canvas canvas){

        super.onDraw(canvas);
        canvas.drawBitmap(background, null, new RectF(0, 0, deviceW, deviceH), null);

        if(choice==0){
            choice=1;
        }else{
            choice=0;
        }

        if(firstTouch = false){
            gravity = 2;
        }

        speed += gravity;
        astroY += speed;

        if(astroY > 786- astro[0].getHeight()){
                astroY = 786- astro[0].getHeight();
            }
        if(astroY == 786- astro[0].getHeight()){
                counter = 0;
                firstTouch = true;
            }
        score = score + 0.35;

        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(55);
        canvas.drawText("Score:  "+ (int)score, 1500, 60, paint);
        canvas.drawBitmap(astro[choice], astroX, astroY,null);

        for(int i = 0; i< numberOfSquares; i++) {

            if(squaresX[i] < -squares1.getWidth()){
                squaresX[i] += numberOfSquares * hole;
            }
            if(squaresX2[i] < -squares2.getWidth()){
                squaresX2[i] += numberOfSquares * hole;
            }
            if(squaresX3[i] < -squares3.getWidth()){
                squaresX3[i] += numberOfSquares * hole;
            }
            squaresX[i]  -= squaresSpeed;
            squaresX2[i] -= squaresSpeed;
            squaresX3[i] -= squaresSpeed;

            canvas.drawBitmap(squares1, squaresX[i], squaresY, null);
            canvas.drawBitmap(squares2, squaresX2[i], squaresY2, null);
            canvas.drawBitmap(squares3, squaresX3[i], squaresY3, null);

            }
        handler.postDelayed(runnable, update);

        if((astroY +astro[0].getHeight()) <= 786 && (astroY +astro[0].getHeight()) >= 636){
            for(int i = 0; i < numberOfSquares; i++){
                if((squaresX[i] < astroX) && (astroX )<(squaresX[i] + squares1.getWidth()) ){
                    gameOver(canvas);
                }
                if((squaresX2[i] < astroX) && (astroX )<(squaresX2[i] + squares1.getWidth()) ){
                    gameOver(canvas);
                }
                if((squaresX3[i] < astroX) && (astroX)<(squaresX3[i] + squares1.getWidth()) ){
                    gameOver(canvas);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        if(action == MotionEvent.ACTION_DOWN && counter<2){
            speed = -70;
            counter += 1;
        }

        return true;
    }

    public void gameOver(Canvas canvas){
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(150);
        canvas.drawText("GAME OVER!", 800, 540, paint);
        over = true;
        handler.removeCallbacks(runnable);
    }

}
//testGIT