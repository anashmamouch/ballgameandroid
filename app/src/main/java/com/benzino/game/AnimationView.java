package com.benzino.game;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.Random;

/**
 * Created by Anas on 05/07/2015.
 */
public class AnimationView extends SurfaceView implements SurfaceHolder.Callback {
    /**
     *  @x the coordinate on the x axis
     *  @y the coordinate on the y axis
     *  @vx the velocity on the x axis
     *  @vy the velocity on the y axis
     *  @width the width of the canvas
     *  @height the height of the canvas
     *  @circleRadius the radius of the circle
     *  @circlePaint
     *  @touching variable to check if the user is touching the screen
     *  @loop instance of the Loop class
     */
    private float x;
    private float y;

    private float vx;
    private float vy;

    private float width;
    private float height;

    private float circleRadius;

    private Paint circlePaint;
    private Paint textPaint;
    private Paint timePaint;

    private boolean touching = false;

    private int time = 0 ;

    private float rate = 0  ;
    private int touched = 0;
    private int touch = 0;
    private Loop loop;

    /**
     * Calls the super() method to give us our surfaceView to work with
     * Link the class up with the SurfaceHolder.Callback
     * Initialize variables regarding the circle
     * Set the speed of mouvement in each direction
     * @param context
     */
    public AnimationView(Context context) {
        super(context);
        getHolder().addCallback(this);

        circleRadius = 30;
        circlePaint = new Paint();
        circlePaint.setColor(Color.RED);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(16);

        timePaint = new Paint();
        timePaint.setColor(Color.BLUE);
        timePaint.setTextAlign(Paint.Align.CENTER);
        timePaint.setTextSize(22);


        vx = 2;
        vy = 2;
    }

    /**
     *
     * Getters and Setters
     */
    @Override
    public float getX() {
        return x;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    public int getTouched() {
        return touched;
    }

    public void setTouched(int touched) {
        this.touched = touched;
    }

    public float getCircleRadius() {
        return circleRadius;
    }

    public void setCircleRadius(float circleRadius) {
        this.circleRadius = circleRadius;
    }

    public float getVx() {
        return vx;
    }

    public void setVx(float vx) {
        this.vx = vx;
    }

    public float getVy() {
        return vy;
    }

    public void setVy(float vy) {
        this.vy = vy;
    }

    public boolean isTouching() {
        return touching;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void incrementTime(int count){
        this.time +=count;
    }

    public void setTouching(boolean touching) {
        this.touching = touching;
    }

    public void success(){
        if(touch != 0)
            rate = ((touched/touch)*100);
    }

    public void increaseSpeed(float count){
        if(vx > 0)
            vx +=count;
        else
            vx -= count ;

        if(vy > 0)
            vy +=count;
        else
            vy -= count ;

        touched++;
    }

    public void decreaseSpeed(float count){
        if(vx > 0)
            vx -=count;
        else
            vx += count ;

        if(vy > 0)
            vy -=count;
        else
            vy += count ;
    }

    public void touchedBall(int z){

    }



    public void changePosition(){
        Random xRand = new Random();
        Random yRand = new Random();

        x = xRand.nextInt(1000);
        y = yRand.nextInt(1000);

        vx*= -1;
        vy*= -1;
    }

    public void incrementTouch(){
        touch++;
        rate = (float)((touched/touch)*100);
    }

    /**
     * Implement this to do your drawing.
     *
     * @param canvas the canvas on which the background will be drawn
     */
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        canvas.drawText("Time in seconds: " + time, width / 2, 30, timePaint);
        canvas.drawText("Touched: "+touching, 0, 50, textPaint);
        canvas.drawText("POSITION X = " + x + "; Y = " + y , 0, 70, textPaint);
        canvas.drawText("VELOCITY VX = "+String.format("%.2f",vx)+"; VY = "+String.format("%.2f", vy)+"",0, 90, textPaint);
        canvas.drawText("SCREEN SIZE Width = "+getWidth()+"; Height = "+getHeight()+".", 0, 110, textPaint);
        canvas.drawText("BALL TOUCHED " + touched + " times" , 0, 130, textPaint);
        canvas.drawText("TOTAL TOUCHES: " + touch + " times" , 0, 150, textPaint);
        canvas.drawText("SUCCESS RATE: " + rate + " %" , 0, 170, textPaint);
        canvas.drawCircle(x, y, circleRadius, circlePaint);
    }

    /**
     * Handles the simple physics of the mouvement
     * Update the position of the ball
     * Make the ball bounce if it hits the edges of the canvas
     */
    public void updatePhysics(){
        x += vx;
        y += vy;

        if(y - circleRadius < 0 || y + circleRadius > height){
            //The ball has hit the top or the bottom of the canvas
            if(y - circleRadius < 0){
                //The ball has hit the top of the canvas
                y = circleRadius;
            }else{
                //The ball has hit the bottom of the canvas
                y = height - circleRadius;
            }
            //Reverse the y direction of the ball
            vy *= -1;
        }

        if(x - circleRadius < 0 || x + circleRadius > width){
            //The ball has hit the sides of the canvas
            if(x - circleRadius <0){
                //The ball has hit the left side of the canvas
                x = circleRadius;
            }else{
                //The ball has hit the right side of the canvas
                x = width - circleRadius;
            }
            //Reverse the x direction of the ball
            vx *= -1;
        }
    }

    /**
     * This is called immediately after the surface is first created.
     * Implementations of this should start up whatever rendering code
     * they desire.  Note that only one thread can ever draw into
     * a {@link Surface}, so you should not draw into the Surface here
     * if your normal rendering will be in another thread.
     *
     * @param holder The SurfaceHolder whose surface is being created.
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Rect surface = holder.getSurfaceFrame();
        width  = surface.width();
        height = surface.height();

        x = width/2;
        y = circleRadius;

        loop = new Loop(this);
        loop.isRunning(true);
        loop.start();

    }

    public void alertDialog() {
        /*
                                * Alert Dialog
                                * */

        if(time  == 10){

            AlertDialog alert = new AlertDialog.Builder(getContext())
                    .setTitle("Title")
                    .setMessage("Time is up \n ball touched "+touched+" times")
                    .setPositiveButton("Play again", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            time = 0;
                            touched = 0;
                            touch = 0;
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("Continue", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                            dialog.dismiss();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

            alert.setCanceledOnTouchOutside(false);

        }

                                /*
                                * Finish Alert Dialog
                                * */
    }

    /**
     * This is called immediately after any structural changes (format or
     * size) have been made to the surface.  You should at this point update
     * the imagery in the surface.  This method is always called at least
     * once, after {@link #surfaceCreated}.
     *
     * @param holder The SurfaceHolder whose surface has changed.
     * @param format The new PixelFormat of the surface.
     * @param width  The new width of the surface.
     * @param height The new height of the surface.
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    /**
     * This is called immediately before a surface is being destroyed. After
     * returning from this call, you should no longer try to access this
     * surface.  If you have a rendering thread that directly accesses
     * the surface, you must ensure that thread is no longer touching the
     * Surface before returning from this function.
     *
     * @param holder The SurfaceHolder whose surface is being destroyed.
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true ;

        loop.isRunning(false);
        while(retry){
            try {
                loop.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
