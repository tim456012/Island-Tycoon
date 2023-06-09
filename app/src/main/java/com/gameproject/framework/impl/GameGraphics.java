package com.gameproject.framework.impl;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.gameproject.framework.Graphics;
import com.gameproject.framework.Pixmap;

import java.io.IOException;
import java.io.InputStream;

public class GameGraphics implements Graphics {

    private AssetManager assets;
    private Bitmap frameBuffer;
    private Canvas canvas;
    private Paint paint;
    private Rect srcRect = new Rect();
    private Rect dstRect = new Rect();
    private Typeface font;

    public GameGraphics(AssetManager assets, Bitmap frameBuffer){

        this.assets = assets;
        this.frameBuffer = frameBuffer;
        this.canvas = new Canvas(frameBuffer);
        this.paint = new Paint();
        font = Typeface.createFromAsset(this.assets, "Font/FFFFORWA.TTF");
    }

    public Pixmap newPixmap(String fileName, PixmapFormat format){

        Config config = null;
        if(format == PixmapFormat.RGB565)
            config = Config.RGB_565;
        else if(format == PixmapFormat.ARGB4444)
            config = Config.ARGB_4444;
        else
            config = Config.ARGB_8888;

        Options options = new Options();
        options.inPreferredConfig = config;

        InputStream inputStream = null;
        Bitmap bitmap = null;

        try{
            inputStream = assets.open(fileName);
            bitmap = BitmapFactory.decodeStream(inputStream);
            if(bitmap == null)
                throw new RuntimeException("Couldn't load bitmap from asset '" + fileName + "'.");
        }catch (IOException ioe) {
            throw new RuntimeException("Couldn't load bitmap from asset '" + fileName + "'.");
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                }catch (IOException ioe){

                }
            }
        }

        if(bitmap.getConfig() == Config.RGB_565)
            format = PixmapFormat.RGB565;
        else if(bitmap.getConfig() == Config.ARGB_4444)
            format = PixmapFormat.ARGB4444;
        else
            format = PixmapFormat.ARGB8888;

        return new GamePixmap(bitmap, format);
    }

    public void clear(int color) {

        canvas.drawRGB((color & 0xff000) >> 16, (color & 0xff00) >> 8, (color & 0xff));
    }

    public void drawPixel(int x, int y, int color) {

        paint.setColor(color);
        canvas.drawPoint(x, y, paint);
    }

    public void drawLine(int x, int y, int x2, int y2, int color) {

        paint.setColor(color);
        canvas.drawLine(x, y, x2, y2, paint);
    }

    public void drawRect(int x, int y, int width, int height, int alpha, int color) {

        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        paint.setAlpha(alpha);
        canvas.drawRect(x, y, x + width - 1, y + height - 1, paint);
    }

    public void drawText(int x, int y, int size, String text, int color) {

        paint.setTypeface(font);
        paint.setColor(color);
        paint.setTextSize(size);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(text, x, y + paint.getTextSize() / 0.7f, paint);
    }

    public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight) {

        srcRect.left = srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth - 1;
        srcRect.bottom = srcY + srcHeight - 1;

        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + srcWidth - 1;
        dstRect.bottom = y + srcHeight - 1;

        canvas.drawBitmap(((GamePixmap) pixmap).bitmap, srcRect, dstRect, null);
    }

    public void drawPixmap(Pixmap pixmap, int x, int y) {

        canvas.drawBitmap(((GamePixmap) pixmap).bitmap, x, y, null);
    }

    public int getWidth() {

        return frameBuffer.getWidth();
    }

    public int getHeight() {

        return frameBuffer.getHeight();
    }
}
