package com.gameproject.framework;

import  com.gameproject.framework.Graphics.PixmapFormat;

public interface Pixmap {

    int getWidth();
    int getHeight();

    PixmapFormat getFormat();

    void dispose();
}
