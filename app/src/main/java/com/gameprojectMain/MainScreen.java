package com.gameprojectMain;

import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.Switch;

import com.gameproject.framework.Game;
import com.gameproject.framework.Graphics;
import com.gameproject.framework.Input;
import com.gameproject.framework.Input.TouchEvent;
import com.gameproject.framework.Music;
import com.gameproject.framework.Screen;

import java.util.List;

public class MainScreen extends Screen {

    private int count = 0;
    private boolean isReset = false;

    enum GameState {
        Running,
        Crafting,
        Purchasing,
        Paused,
        Warning,
        GameOver
    }

    enum PlayerState {
        idle,
        miningGold,
        miningSilver,
        inHouse
    }

    GameState state = GameState.Running;
    PlayerState playerState = PlayerState.idle;
    World world;

    public MainScreen(Game game) {
        super(game);
        world = new World();
    }

    @Override
    public void update(float deltaTime) {

        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();

        switch (state) {
            case Running:
                updateGameRunning(touchEvents, deltaTime);
                break;
            case Crafting:
                updateGameCrafting(touchEvents);
                break;
            case Purchasing:
                updateGamePurchasing(touchEvents);
                break;
            case Paused:
                updateGamePaused(touchEvents);
                break;
            case Warning:
                updateGameWarning(touchEvents);
                break;
            case GameOver:
                updateGameOver(touchEvents);
                break;
        }

        Settings.playMusic();
    }

    @Override
    public void present(float deltaTime) {

        Graphics g = game.getGraphics();
        g.drawRect(0, 0, 1080, 1920, 255, Color.BLACK);

        drawWorld(world);

        switch (playerState) {
            case idle:
                g.drawPixmap(Assets.Player, 476, 904);
                break;
            case miningGold:
                g.drawPixmap(Assets.Player, 370, 1024);
                break;
            case miningSilver:
                g.drawPixmap(Assets.Player, 648, 1208);
                break;
            case inHouse:
                g.drawPixmap(Assets.Player, 578, 825);
                break;
        }

        switch (state) {
            case Running:
                drawGameUI();
                break;
            case Crafting:
                drawCraftUI();
                break;
            case Purchasing:
                drawPurchaseUI();
                break;
            case Paused:
                drawPausedUI();
                break;
            case Warning:
                drawWarningUI();
                break;
            case GameOver:
                drawGameOverUI();
                world.reset();
                break;
        }
    }

    //region GameUpdate
    private void updateGameRunning(List<TouchEvent> touchEvents, float deltaTime) {

        int length = touchEvents.size();
        for (int i = 0; i < length; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {

                if (inBounds(event, 964, 224, 96, 96)) {

                    state = GameState.Paused;
                    return;
                }

                if(inBounds(event, 354, 1024, 96, 96)) {

                    playerState = PlayerState.miningGold;
                    if(Settings.soundEnabled)
                        Assets.sfx_gdig.play(5);
                    PlayerBehaviour(event, deltaTime);
                    return;
                }

                if(inBounds(event, 632, 1208, 96, 96)) {

                    playerState = PlayerState.miningSilver;
                    if(Settings.soundEnabled)
                        Assets.sfx_sdig.play(5);
                    PlayerBehaviour(event, deltaTime);
                    return;
                }

                if(inBounds(event, 562, 810, 96, 94)) {

                    playerState = PlayerState.inHouse;
                    PlayerBehaviour(event, deltaTime);
                    return;
                }

                if(inBounds(event, 645, 1072, 143, 80)) {

                    state = GameState.Purchasing;
                    return;
                }
            }
        }

        world.update(deltaTime);
        if(world.gameOver) {
            state = GameState.GameOver;
        }
    }

    private void updateGameCrafting(List<TouchEvent> touchEvents) {

        int length = touchEvents.size();
        for (int i = 0; i < length; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {

                if (inBounds(event, 874, 490, 192, 192)) {

                    if(Settings.soundEnabled)
                        Assets.sfx_click.play(2);

                    state = GameState.Running;
                    return;
                }

                if(inBounds(event, 674, 719, 250, 83)) {

                    if(world.items[Items.GOLD].quantity >= 20) {

                        world.items[Items.JEWELRY].quantity++;
                        world.items[Items.GOLD].quantity -= 20;

                        if(Settings.soundEnabled)
                            Assets.sfx_click.play(2);
                    }
                    else {
                        return;
                    }
                    return;
                }

                if(inBounds(event, 674, 915, 250, 83)) {

                    if(world.items[Items.APPLE].quantity >= 5) {

                        world.items[Items.WINE].quantity++;
                        world.items[Items.APPLE].quantity -= 5;

                        if(Settings.soundEnabled)
                            Assets.sfx_click.play(2);
                    }
                    else {
                        return;
                    }
                    return;
                }

                if(inBounds(event, 674, 1124, 250, 83)) {

                    if(world.items[Items.RAW_MEAT].quantity >= 1) {

                        world.items[Items.MEAT].quantity++;
                        world.items[Items.RAW_MEAT].quantity -= 1;

                        if(Settings.soundEnabled)
                            Assets.sfx_click.play(2);
                    }
                    else {
                        return;
                    }
                    return;
                }
            }
        }
    }

    private void updateGamePurchasing(List<TouchEvent> touchEvents) {

        int length = touchEvents.size();
        for (int i = 0; i < length; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {

                if (inBounds(event, 851, 525, 192, 192)) {

                    if(Settings.soundEnabled)
                        Assets.sfx_click.play(2);

                    state = GameState.Running;
                    return;
                }

                if (inBounds(event, 205, 866, 250, 83)) {

                    if(world.items[Items.SILVER].quantity > 0) {
                        world.items[Items.SILVER].quantity -= 5;
                        world.items[Items.APPLE].quantity++;

                        if(Settings.soundEnabled)
                            Assets.sfx_click.play(2);
                    }
                    else
                        return;
                    return;
                }

                if (inBounds(event, 607, 866, 250, 83)) {

                    if(world.items[Items.SILVER].quantity > 0) {
                        world.items[Items.SILVER].quantity -= 20;
                        world.items[Items.RAW_MEAT].quantity++;

                        if(Settings.soundEnabled)
                            Assets.sfx_click.play(2);
                    }
                    else
                        return;
                    return;
                }

                if (inBounds(event, 118, 1209, 250, 83)) {

                    if(world.items[Items.JEWELRY].quantity > 0) {
                        world.items[Items.SILVER].quantity += 100;
                        world.items[Items.JEWELRY].quantity--;

                        if(Settings.soundEnabled)
                            Assets.sfx_tax.play(2);
                    }
                    else
                        return;
                    return;
                }

                if (inBounds(event, 406, 1209, 250, 83)) {

                    if(world.items[Items.WINE].quantity > 0) {
                        world.items[Items.SILVER].quantity += 20;
                        world.items[Items.WINE].quantity--;

                        if(Settings.soundEnabled)
                            Assets.sfx_tax.play(2);
                    }
                    else
                        return;
                    return;
                }

                if (inBounds(event, 694, 1209, 250, 83)) {

                    if(world.items[Items.MEAT].quantity > 0) {
                        world.items[Items.SILVER].quantity += 5;
                        world.items[Items.MEAT].quantity--;

                        if(Settings.soundEnabled)
                            Assets.sfx_tax.play(2);
                    }
                    else
                        return;
                    return;
                }
            }
        }
    }

    private void updateGamePaused(List<TouchEvent> touchEvents) {

        int length = touchEvents.size();
        for (int i = 0; i < length; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {

                if(inBounds(event, 843, 527, 192, 192)) {

                    Settings.soundEnabled = !Settings.soundEnabled;
                    if(Settings.soundEnabled)
                        Assets.sfx_click.play(2);
                    return;
                }

                if (inBounds(event, 334, 792, 384, 128)) {

                    state = GameState.Running;
                    if(Settings.soundEnabled)
                        Assets.sfx_click.play(2);
                    return;
                }

                if(inBounds(event, 334, 994, 384, 128)) {

                    if(Settings.soundEnabled)
                        Assets.sfx_click.play(2);
                    isReset = true;
                    state = GameState.Warning;
                    return;
                }

                if(inBounds(event, 334, 1184, 384, 128)) {

                    if(Settings.soundEnabled)
                        Assets.sfx_click.play(2);
                    game.setScreen(new MainMenuScreen(game));
                    SaveGame();
                    return;
                }
            }
        }
    }

    private void updateGameWarning(List<TouchEvent> touchEvents) {

        int length = touchEvents.size();
        for (int i = 0; i < length; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {

                if(!isReset) {

                    if(Settings.soundEnabled)
                        Assets.sfx_warning.play(10);

                    if (inBounds(event, 334, 1086, 384, 128)) {

                        if(Settings.soundEnabled)
                            Assets.sfx_click.play(2);

                        world.items[Items.SILVER].quantity -= 3000;
                        state = GameState.Running;
                        return;
                    }
                }
                else {

                    if (inBounds(event, 334, 1142, 384, 128)) {

                        if(Settings.soundEnabled)
                            Assets.sfx_click.play(2);

                        world.reset();
                        state = GameState.Running;
                        return;
                    }
                }
            }
        }
    }

    private void updateGameOver(List<TouchEvent> touchEvents) {

        int length = touchEvents.size();
        for (int i = 0; i < length; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {

                if (event.x > 0 && event.y > 0) {

                    if(Settings.soundEnabled) {
                        Assets.track1.pause();
                        Assets.track2.pause();
                        Assets.track3.pause();
                        Assets.track4.pause();
                        Assets.sfx_gameOver.play(5);
                    }
                    game.setScreen(new MainMenuScreen(game));
                    return;
                }
            }
        }
    }
    //endregion

    //region GameDraw
    private void drawWorld(World world) {

        Graphics g = game.getGraphics();
        Items items[] = world.items;

        g.drawPixmap(Assets.Map, 0,0);
        g.drawPixmap(Assets.GameWindow1, 0, 0);
        g.drawPixmap(Assets.GameWindow2, 0, 1715);

        g.drawPixmap(Assets.Silver, 38, 224);
        g.drawPixmap(Assets.Gold, 64, 58);
        g.drawPixmap(Assets.Apple, 402, 58);
        g.drawPixmap(Assets.RawMeat, 740, 58);
        g.drawPixmap(Assets.Jewelry, 64, 1777);
        g.drawPixmap(Assets.Wine, 402, 1777);
        g.drawPixmap(Assets.Meat, 740, 1777);

        g.drawPixmap(Assets.House, 562, 810);
        g.drawPixmap(Assets.GoldMine, 354, 1024);
        g.drawPixmap(Assets.SilverMine, 632, 1208);

        g.drawPixmap(Assets.MerchantShip, 775, 1072);

        g.drawText(160, 228, 50, String.valueOf(items[Items.SILVER].quantity), Color.BLACK);
        g.drawText(202, 58, 50, String.valueOf(items[Items.GOLD].quantity), Color.BLACK);
        g.drawText(540, 58, 50, String.valueOf(items[Items.APPLE].quantity), Color.BLACK);
        g.drawText(878, 58, 50, String.valueOf(items[Items.RAW_MEAT].quantity), Color.BLACK);
        g.drawText(202, 1777, 50, String.valueOf(items[Items.JEWELRY].quantity), Color.BLACK);
        g.drawText(540, 1777, 50, String.valueOf(items[Items.WINE].quantity), Color.BLACK);
        g.drawText(878, 1777, 50, String.valueOf(items[Items.MEAT].quantity), Color.BLACK);
    }

    private void drawGameUI() {

        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.Btn_Pause, 964, 224);
    }

    private void drawCraftUI() {

        Graphics g = game.getGraphics();

        g.drawRect(0, 0, 1080, 1920, 100, Color.BLACK);
        g.drawPixmap(Assets.Window, 60, 560);
        g.drawPixmap(Assets.Btn_Cancel, 874, 490);

        g.drawPixmap(Assets.Gold, 157, 706);
        g.drawPixmap(Assets.Apple, 157, 902);
        g.drawPixmap(Assets.RawMeat, 157, 1111);

        g.drawText(296, 706, 50, "20", Color.BLACK);
        g.drawText(296, 902, 50, "5", Color.BLACK);
        g.drawText(296, 1111, 50, "1", Color.BLACK);

        g.drawPixmap(Assets.Jewelry, 464, 706);
        g.drawPixmap(Assets.Wine, 464, 902);
        g.drawPixmap(Assets.Meat, 464, 1111);

        g.drawPixmap(Assets.Btn_Craft, 674, 719);
        g.drawPixmap(Assets.Btn_Craft, 674, 915);
        g.drawPixmap(Assets.Btn_Craft, 674, 1124);
    }

    private void drawPurchaseUI() {

        Graphics g = game.getGraphics();
        int silver = world.items[Items.SILVER].quantity;

        g.drawRect(0, 0, 1080, 1920, 100, Color.BLACK);

        g.drawPixmap(Assets.Silver, 86, 477);
        g.drawText(208, 477, 70, String.valueOf(silver), Color.BLACK);
        g.drawPixmap(Assets.Window, 37, 595);
        g.drawPixmap(Assets.Btn_Cancel, 851, 525);

        g.drawPixmap(Assets.Apple, 282, 674);
        g.drawPixmap(Assets.RawMeat, 684, 674);
        g.drawPixmap(Assets.Jewelry, 195, 998);
        g.drawPixmap(Assets.Wine, 483, 998);
        g.drawPixmap(Assets.Meat, 771, 998);

        g.drawPixmap(Assets.SilverSmall, 269, 788);
        g.drawPixmap(Assets.SilverSmall, 671, 788);
        g.drawPixmap(Assets.SilverSmall, 162, 1136);
        g.drawPixmap(Assets.SilverSmall, 463, 1136);
        g.drawPixmap(Assets.SilverSmall, 753, 1136);

        g.drawText(350, 770, 50, "5", Color.BLACK);
        g.drawText(752, 770, 50, "20", Color.BLACK);
        g.drawText(243, 1118, 50, "100", Color.BLACK);
        g.drawText(544, 1118, 50, "20", Color.BLACK);
        g.drawText(834, 1118, 50, "5", Color.BLACK);

        g.drawPixmap(Assets.Btn_Buy, 205, 866);
        g.drawPixmap(Assets.Btn_Buy, 607, 866);
        g.drawPixmap(Assets.Btn_Sell, 118, 1209);
        g.drawPixmap(Assets.Btn_Sell, 406, 1209);
        g.drawPixmap(Assets.Btn_Sell, 694, 1209);
    }

    private void drawPausedUI() {

        Graphics g = game.getGraphics();

        g.drawRect(0, 0, 1080, 1920, 100, Color.BLACK);

        g.drawPixmap(Assets.Window, 64, 548);
        g.drawPixmap(Assets.Btn_Resume, 352, 747);
        g.drawPixmap(Assets.Btn_Reset, 352, 949);
        g.drawPixmap(Assets.Btn_Menu, 352, 1139);

        if(Settings.soundEnabled)
            g.drawPixmap(Assets.Btn_Audio, 861, 482);
        else
            g.drawPixmap(Assets.Btn_MuteAudio, 861, 482);
    }

    private void drawWarningUI() {

        Graphics g = game.getGraphics();

        g.drawRect(0, 0, 1080, 1920, 100, Color.BLACK);

        if(!isReset) {

            g.drawPixmap(Assets.Window, 46, 537);
            g.drawPixmap(Assets.WarningText, 116, 680);
            g.drawPixmap(Assets.Btn_Ok, 334, 1086);
        }
        else {

            g.drawPixmap(Assets.Window, 46, 593);
            g.drawText(118, 790, 38, "Are you sure want to reset data?", Color.BLACK);
            g.drawPixmap(Assets.Btn_Ok, 334, 1142);
        }
    }

    private void drawGameOverUI() {

        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.GameOver, 0, 0);

        g.drawText(427, 1164, 60, String.valueOf(world.getHigherScore()), Color.BLACK);

    }
    //endregion

    @Override
    public void pause() {

        SaveGame();
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

    private boolean inBounds(Input.TouchEvent event, int x, int y, int width, int height) {

        if(event.x > x && event.x < x + width - 1 &&
                event.y > y && event.y < y + height - 1)
            return true;
        else
            return false;
    }

    private void PlayerBehaviour(TouchEvent event, float deltaTime) {

        if (event.type == TouchEvent.TOUCH_UP) {
            switch (playerState) {
                case miningGold:
                    count++;
                    if(count == 3) {
                        world.items[Items.GOLD].quantity++;
                        count = 0;
                    }
                    break;
                case miningSilver:
                    world.items[Items.SILVER].quantity += 5;
                    break;
                case inHouse:
                    state = GameState.Crafting;
                    break;
            }
        }
    }

    private void SaveGame() {

        Settings.saveData(world.items);
        Settings.addScore(world.getHigherScore());
        Settings.save(game.getFileIO());
    }
}
