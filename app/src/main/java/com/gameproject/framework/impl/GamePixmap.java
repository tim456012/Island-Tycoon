package com.gameproject.framework.impl;

import com.gameproject.framework.Graphics.PixmapFormat;
import com.gameproject.framework.Pixmap;
import android.graphics.Bitmap;

public class GamePixmap implements Pixmap {

    Bitmap bitmap;
    PixmapFormat format;

    public GamePixmap(Bitmap bitmap, PixmapFormat format) {

        this.bitmap = bitmap;
        this.format = format;
    }

    public int getWidth() {

        return bitmap.getWidth();
    }

    public int getHeight() {

        return bitmap.getHeight();
    }

    public PixmapFormat getFormat() {

        return format;
    }

    public void dispose() {

        bitmap.recycle();
    }
}
