package com.example.boinker.engine;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private Game game;
    private boolean running;
    private static Canvas canvas;


    MainThread(SurfaceHolder surfaceHolder, Game game) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.game = game;

    }

    @Override
    public void run(){
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;

        while(running) {
            //canvas = null;
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) {
                this.game.update();
                delta--;
            }
            draw();
        }
    }

    private void draw() {
        try {
            canvas = this.surfaceHolder.lockCanvas();
            synchronized (surfaceHolder) {
                this.game.draw(canvas);
            }
        } catch (Exception e) {
        } finally {
            if (canvas != null) {
                try {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    void setRunning(boolean isRunning) {
        running = isRunning;
        if (running){
            Log.d("Thread", "while loop running");
        }else{
            Log.d("Thread", "while loop stopped");
        }
    }


}
