package com.gameprojectMain;

import com.gameproject.framework.Audio;
import com.gameproject.framework.Game;
import com.gameproject.framework.Graphics;
import com.gameproject.framework.Graphics.PixmapFormat;
import com.gameproject.framework.Screen;

public class StartScreen extends Screen {

    public StartScreen(Game game) {

        super(game);
    }

    @Override
    public void update(float deltaTime) {

        Graphics g = game.getGraphics();
        Audio a = game.getAudio();

        Settings.load(game.getFileIO());

        //Screen Pages
        Assets.Background = g.newPixmap("Images/Screens/Background.png", PixmapFormat.ARGB8888);
        Assets.Map = g.newPixmap("Images/Screens/Map.png", PixmapFormat.ARGB8888);
        Assets.Help1 = g.newPixmap("Images/Screens/Help1.png", PixmapFormat.ARGB8888);
        Assets.Help2 = g.newPixmap("Images/Screens/Help2.png", PixmapFormat.ARGB8888);
        Assets.Help3 = g.newPixmap("Images/Screens/Help3.png", PixmapFormat.ARGB8888);
        Assets.GameOver = g.newPixmap("Images/Screens/GameOver.png", PixmapFormat.ARGB8888);

        //Windows
        Assets.GameWindow1 = g.newPixmap("Images/Windows/GameWindow1.png", PixmapFormat.RGB565);
        Assets.GameWindow2 = g.newPixmap("Images/Windows/GameWindow2.png", PixmapFormat.RGB565);
        Assets.Window = g.newPixmap("Images/Windows/Window.png", PixmapFormat.RGB565);
        Assets.WarningText = g.newPixmap("Images/Windows/WarningText.png", PixmapFormat.RGB565);

        //Environments
        Assets.SilverMine = g.newPixmap("Images/Environments/SilverMine.png", PixmapFormat.RGB565);
        Assets.GoldMine = g.newPixmap("Images/Environments/GoldMine.png", PixmapFormat.RGB565);
        Assets.House = g.newPixmap("Images/Environments/House.png", PixmapFormat.RGB565);

        //Characters
        Assets.Player = g.newPixmap("Images/Characters/Player.png", PixmapFormat.RGB565);
        Assets.MerchantShip = g.newPixmap("Images/Characters/MerchantShip.png", PixmapFormat.RGB565);

        //Buttons
        Assets.Btn_Audio = g.newPixmap("Images/Buttons/Btn_Audio.png", PixmapFormat.RGB565);
        Assets.Btn_MuteAudio = g.newPixmap("Images/Buttons/Btn_MuteAudio.png", PixmapFormat.RGB565);
        Assets.Btn_Start = g.newPixmap("Images/Buttons/Btn_Start.png", PixmapFormat.RGB565);
        Assets.Btn_Help = g.newPixmap("Images/Buttons/Btn_Help.png", PixmapFormat.RGB565);
        Assets.Btn_Scores = g.newPixmap("Images/Buttons/Btn_Scores.png", PixmapFormat.RGB565);
        Assets.Btn_Next = g.newPixmap("Images/Buttons/Btn_Next.png", PixmapFormat.RGB565);
        Assets.Btn_Back = g.newPixmap("Images/Buttons/Btn_Back.png", PixmapFormat.RGB565);
        Assets.Btn_Cancel = g.newPixmap("Images/Buttons/Btn_Cancel.png", PixmapFormat.RGB565);
        Assets.Btn_Pause = g.newPixmap("Images/Buttons/Btn_Pause.png", PixmapFormat.RGB565);
        Assets.Btn_Resume = g.newPixmap("Images/Buttons/Btn_Resume.png", PixmapFormat.RGB565);
        Assets.Btn_Reset = g.newPixmap("Images/Buttons/Btn_Reset.png", PixmapFormat.RGB565);
        Assets.Btn_Menu = g.newPixmap("Images/Buttons/Btn_Menu.png", PixmapFormat.RGB565);
        Assets.Btn_Buy = g.newPixmap("Images/Buttons/Btn_Buy.png", PixmapFormat.RGB565);
        Assets.Btn_Ok = g.newPixmap("Images/Buttons/Btn_Ok.png", PixmapFormat.RGB565);
        Assets.Btn_Craft = g.newPixmap("Images/Buttons/Btn_Craft.png", PixmapFormat.RGB565);
        Assets.Btn_Sell = g.newPixmap("Images/Buttons/Btn_Sell.png", PixmapFormat.RGB565);

        //Items
        Assets.Apple = g.newPixmap("Images/Items/Apple.png", PixmapFormat.RGB565);
        Assets.Wine = g.newPixmap("Images/Items/Wine.png", PixmapFormat.RGB565);
        Assets.RawMeat = g.newPixmap("Images/Items/RawMeat.png", PixmapFormat.RGB565);
        Assets.Meat = g.newPixmap("Images/Items/Meat.png", PixmapFormat.RGB565);
        Assets.Gold = g.newPixmap("Images/Items/Gold.png", PixmapFormat.RGB565);
        Assets.Jewelry = g.newPixmap("Images/Items/Jewelry.png", PixmapFormat.RGB565);
        Assets.Silver = g.newPixmap("Images/Items/Silver.png", PixmapFormat.RGB565);
        Assets.SilverSmall = g.newPixmap("Images/Items/Silversmall.png", PixmapFormat.RGB565);

        //Music
        Assets.track1 = a.newMusic("Music/BGM_01.mp3");
        Assets.track2 = a.newMusic("Music/BGM_02.mp3");
        Assets.track3 = a.newMusic("Music/BGM_03.mp3");
        Assets.track4 = a.newMusic("Music/BGM_04.mp3");

        //Sounds
        Assets.sfx_click = a.newSound("Sounds/sfx_click.wav");
        Assets.sfx_sdig = a.newSound("Sounds/sfx_sdig.wav");
        Assets.sfx_gdig = a.newSound("Sounds/sfx_gdig.wav");
        Assets.sfx_tax = a.newSound("Sounds/sfx_tax.wav");
        Assets.sfx_warning = a.newSound("Sounds/sfx_warning.wav");
        Assets.sfx_gameOver = a.newSound("Sounds/sfx_gameOver.wav");

        Settings.initialMusic();

        game.setScreen(new MainMenuScreen(game));
    }

    @Override
    public void present(float deltaTime) {

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

}
