package com.gameproject.framework.impl;

import android.content.Context;
import android.view.View;

import com.gameproject.framework.Input;

import java.util.List;

public class GameInput implements Input {

    TouchHandler touchHandler;

    public GameInput(Context context, View view, float scaleX, float scaleY) {

        touchHandler = new MultiTouchHandler(view, scaleX, scaleY);
    }

    public boolean isTouchDown(int pointer) {

        return touchHandler.isTouchDown(pointer);
    }

    public int getTouchX(int pointer) {

        return touchHandler.getTouchX(pointer);
    }

    public int getTouchY(int pointer) {

        return touchHandler.getTouchY(pointer);
    }

    public List<TouchEvent> getTouchEvents() {

        return touchHandler.getTouchEvents();
    }

}
