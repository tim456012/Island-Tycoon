package com.gameprojectMain;

import com.gameproject.framework.Game;
import com.gameproject.framework.Graphics;
import com.gameproject.framework.Music;
import com.gameproject.framework.Screen;
import com.gameproject.framework.Input.TouchEvent;

import java.util.ArrayList;
import java.util.List;

public class MainMenuScreen extends Screen{

    public MainMenuScreen(Game game) {
        super(game);
        //Settings.load(game.getFileIO());
    }

    @Override
    public void update(float deltaTime) {

        //Settings.load(game.getFileIO());

        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        int length = touchEvents.size();
        for(int i = 0; i < length; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP){

                if(inBounds(event, 830, 41, 192, 192)) {

                    Settings.soundEnabled = !Settings.soundEnabled;
                    if(Settings.soundEnabled)
                        Assets.sfx_click.play(2);
                    return;
                }

                if(inBounds(event, 348, 1160, 384, 128)) {

                    if(Settings.soundEnabled)
                        Assets.sfx_click.play(2);
                    //Settings.load(game.getFileIO());
                    game.setScreen(new MainScreen(game));
                    return;
                }

                if(inBounds(event, 348, 1381, 384, 128)) {

                    if(Settings.soundEnabled)
                        Assets.sfx_click.play(2);
                    game.setScreen(new HelpScreen(game));
                    return;
                }

                if(inBounds(event, 348, 1602, 384, 128)) {

                    if(Settings.soundEnabled)
                        Assets.sfx_click.play(2);
                    game.setScreen(new ScoresScreen(game));
                    return;
                }
            }
        }
        Settings.playMusic();
    }

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();
        g.drawPixmap(Assets.Background, 0, 0);
        g.drawPixmap(Assets.Btn_Start, 348, 1160);
        g.drawPixmap(Assets.Btn_Help, 348, 1381);
        g.drawPixmap(Assets.Btn_Scores, 348, 1602);

        if(Settings.soundEnabled)
            g.drawPixmap(Assets.Btn_Audio, 830, 41);
        else
            g.drawPixmap(Assets.Btn_MuteAudio, 830, 41);
    }

    @Override
    public void pause() {

        Settings.save(game.getFileIO());
        for(Music m : Settings.musicList)
            m.pause();
    }

    @Override
    public void resume() {

        //Settings.playMusic();
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
