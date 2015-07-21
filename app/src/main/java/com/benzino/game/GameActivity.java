package com.benzino.game;

import android.app.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.zip.Inflater;


public class GameActivity extends Activity {
    private AnimationView view;
    /**
     * Called when the activity is starting.  This is where most initialization
     * should go: calling {@link #setContentView(int)} to inflate the
     * activity's UI, using {@link #findViewById} to programmatically interact
     * with widgets in the UI, calling
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        view = new AnimationView(this);

        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                view.incrementTime(1);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int h = (int)(event.getX());
                int d = (int)(view.getX() + view.getCircleRadius());
                int l = (int)(view.getX() - view.getCircleRadius());

                int i = (int)(event.getY());
                int j = (int)(view.getY() + view.getCircleRadius());
                int k = (int)(view.getY() - view.getCircleRadius());

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if ( l < h && h < d && k < i && i <j ) {
                            Log.d("ANAS", "------------------->>TOUCHED");
                            view.setX(event.getX());
                            view.setY(event.getY());
                            view.increaseSpeed(0.5F);
                            view.changePosition();
                        }
                        view.incrementTouch();
                        view.success();
                        view.setTouching(true);
                        Log.d("ANAS", "------------------->>ACTION DOWN");
                        break;

                    case MotionEvent.ACTION_UP:
                        //finger touches the screen


                        view.setTouching(false);
                        Log.d("ANAS", "------------------->>ACTION UP");
                        break;

                    case MotionEvent.ACTION_MOVE:
                        //finger move in the screen (drag)
                        view.setX(event.getX());
                        view.setY(event.getY());
                        view.setTouching(true);
                        Log.d("ANAS", "------------------->>ACTION MOVE");
                        break;

                    default:
                        view.setTouching(false);
                        Log.d("ANAS", "------------------->>DEFAULT");

                }
                view.invalidate();
                return true;
            }


        });
        setContentView(view);
    }

}
