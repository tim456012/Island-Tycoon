package com.gameproject.framework;

public interface Graphics {

    enum PixmapFormat{

        ARGB8888,
        ARGB4444,
        RGB565,

    }

    Pixmap newPixmap(String fileName, PixmapFormat format);

    void clear(int color);
    void drawPixel(int x, int y, int color);
    void drawLine(int x, int y, int x2, int y2, int color);
    void drawRect(int x, int y, int width, int height, int alpha, int color);
    void drawText(int x, int y, int size, String text, int color);

    void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight);
    void drawPixmap(Pixmap pixmap, int x, int y);

    int getWidth();
    int getHeight();
}
