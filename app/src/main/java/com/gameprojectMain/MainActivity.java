package com.gameprojectMain;

import android.app.Activity;
import android.graphics.Insets;
import android.os.Bundle;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.util.Log;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;

import com.gameproject.framework.Audio;
import com.gameproject.framework.FileIO;
import com.gameproject.framework.Game;
import com.gameproject.framework.Graphics;
import com.gameproject.framework.Input;
import com.gameproject.framework.Screen;
import com.gameproject.framework.impl.RenderView;
import com.gameproject.framework.impl.GameAudio;
import com.gameproject.framework.impl.GameFileIO;
import com.gameproject.framework.impl.GameGraphics;
import com.gameproject.framework.impl.GameInput;

public abstract class MainActivity extends Activity implements Game{

    RenderView renderView;
    Graphics graphics;
    Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Pixel Prefect
        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        int frameBufferWidth = isLandscape ? 1920 : 1080;
        int frameBufferHeight = isLandscape ? 1080 : 1920;
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Config.ARGB_8888);

        WindowMetrics windowMetrics = getWindowManager().getCurrentWindowMetrics();
        Insets insets = windowMetrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
        int width = windowMetrics.getBounds().width() - insets.left - insets.right;
        int height = windowMetrics.getBounds().height() - insets.top - insets.bottom;

        // Old version
        // DisplayMetrics displayMetrics = new DisplayMetrics();
        // getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //  int width = displayMetrics.widthPixels;
        //  int height = displayMetrics.heightPixels;

        Log.d("Width Value", "Width = " + width);
        Log.d("Height Value", "Height = " + height);

        float scaleX = (float) frameBufferWidth / width;
        float scaleY = (float) frameBufferHeight / height;

        renderView = new RenderView(this, frameBuffer);
        graphics = new GameGraphics(getAssets(), frameBuffer);
        fileIO = new GameFileIO(this);
        audio = new GameAudio(this);
        input = new GameInput(this, renderView, scaleX, scaleY);

        screen = getStartScreen();

        setContentView(renderView);
    }

    @Override
    public void onResume(){

        super.onResume();
        screen.resume();
        renderView.resume();
    }

    @Override
    public void onPause() {

        super.onPause();
        renderView.pause();
        screen.pause();

        if(isFinishing()) {
            screen.dispose();
        }
    }

    public Input getInput(){

        return input;
    }

    public FileIO getFileIO() {

        return fileIO;
    }

    public Graphics getGraphics() {

        return graphics;
    }

    public Audio getAudio() {

        return audio;
    }

    public void setScreen(Screen screen) {

        if(screen == null)
            throw new IllegalArgumentException("Screen must not be null");

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }

    public Screen getCurrentScreen() {

        return screen;
    }
}
