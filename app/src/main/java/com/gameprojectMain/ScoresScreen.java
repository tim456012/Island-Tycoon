package com.gameprojectMain;

import android.graphics.Color;

import com.gameproject.framework.Game;
import com.gameproject.framework.Graphics;
import com.gameproject.framework.Input;
import com.gameproject.framework.Screen;

import java.util.List;

public class ScoresScreen extends Screen {

    public ScoresScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();

        int length = touchEvents.size();
        for(int i = 0; i < length; i++) {
            Input.TouchEvent event = touchEvents.get(i);
            if(event.type == Input.TouchEvent.TOUCH_UP) {

                if(inBounds(event, 831, 33, 192, 192)) {
                    game.setScreen(new MainMenuScreen(game));
                    return;
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {

        Graphics g = game.getGraphics();
        String temp = String.valueOf(Settings.savedScore);

        g.drawPixmap(Assets.Background, 0, 0);
        g.drawText(181, 670, 50, "Your higher score is : ", Color.BLACK);

        if(Settings.savedScore == 0)
            g.drawText(428, 855, 50, "No data", Color.BLACK);
        else
            g.drawText(428, 855, 50, temp, Color.BLACK);

        g.drawPixmap(Assets.Btn_Cancel, 831, 33);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    private boolean inBounds(Input.TouchEvent event, int x, int y, int width, int height) {

        if(event.x > x && event.x < x + width - 1 &&
                event.y > y && event.y < y + height - 1)
            return true;
        else
            return false;
    }
}
