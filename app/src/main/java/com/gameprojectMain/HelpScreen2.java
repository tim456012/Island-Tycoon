package com.gameprojectMain;

import java.util.List;
import com.gameproject.framework.Game;
import com.gameproject.framework.Graphics;
import com.gameproject.framework.Input.TouchEvent;
import com.gameproject.framework.Screen;

public class HelpScreen2 extends Screen {


    public HelpScreen2(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {

        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        int length = touchEvents.size();
        for(int i = 0; i<length; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) {

                if(inBounds(event, 831, 33, 192, 192)) {
                    game.setScreen(new MainMenuScreen(game));
                    return;
                }

                if(inBounds(event, 74, 1695, 192, 192)){
                    game.setScreen(new HelpScreen(game));
                    return;
                }

                if(inBounds(event, 831, 1695, 192, 192)) {
                    game.setScreen(new HelpScreen3(game));
                    return;
                }
            }
        }

    }

    @Override
    public void present(float deltaTime) {

        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.Help2, 0,0);

        g.drawPixmap(Assets.Btn_Cancel, 831, 33);
        g.drawPixmap(Assets.Btn_Next, 831, 1695);
        g.drawPixmap(Assets.Btn_Back, 74, 1695);
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

    private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {

        if(event.x > x && event.x < x + width - 1 &&
                event.y > y && event.y < y + height - 1)
            return true;
        else
            return false;
    }
}
