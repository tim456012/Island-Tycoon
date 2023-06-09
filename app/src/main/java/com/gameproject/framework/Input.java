package com.gameproject.framework;

import java.util.List;

public interface Input {

    class TouchEvent{

        public static final int TOUCH_DOWN = 0;
        public static final int TOUCH_UP = 1;
        public static int TOUCH_DRAGGED = 2;
        public int type;
        public int x, y;
        public int pointer;
    }

    boolean isTouchDown(int pointer);
    int getTouchX(int pointer);
    int getTouchY(int pointer);

    List<TouchEvent> getTouchEvents();
}