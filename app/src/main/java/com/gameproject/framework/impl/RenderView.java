package com.gameproject.framework.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import com.gameprojectMain.MainActivity;


public class RenderView extends SurfaceView implements Runnable {

    MainActivity game;
    Bitmap framebuffer;
    Thread renderThread = null;
    SurfaceHolder holder;
    volatile boolean running = false;

    public RenderView(MainActivity game, Bitmap framebuffer) {

        super(game);
        this.game = game;
        this.framebuffer = framebuffer;
        this.holder = getHolder();
    }

    public void resume() {

        running = true;
        renderThread = new Thread(this);
        renderThread.start();
    }

    public void run() {

        Rect dstRect = new Rect();
        long startTime = System.nanoTime();
        while (running) {
            if (!holder.getSurface().isValid())
                continue;

            float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
            startTime = System.nanoTime();

            game.getCurrentScreen().update(deltaTime);
            game.getCurrentScreen().present(deltaTime);

            Canvas canvas = holder.lockCanvas();
            canvas.getClipBounds(dstRect);
            canvas.drawBitmap(framebuffer, null, dstRect, null);
            holder.unlockCanvasAndPost(canvas);
        }
    }


    public void pause() {

        running = false;
        while (true) {
            try {
                renderThread.join();
                return;
            }catch (InterruptedException e){
                //Retry
            }
        }
    }
}
