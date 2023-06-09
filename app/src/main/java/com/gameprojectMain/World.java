package com.gameprojectMain;

import java.util.Random;

public class World {

    enum TaxLevel {

        level1,
        level2,
        level3,
        level4
    }

    public static float timer_Initial = 0.5f;
    public static float timer_Decrement = 0.05f;

    public boolean gameOver = false;
    public boolean itemsIsEmpty = false;
    public Items items[] = new Items[7];

    private TaxLevel level = TaxLevel.level1;
    private float Totaltime = 0;
    private float timer = timer_Initial;
    private int count = 0, taxSuccess = 0, higherScore = 0;

    public World() {

        Initial();
    }

    private void Initial() {

        if(Settings.firstTime) {
            for (int i = 0; i < items.length; i++) {
                items[i] = new Items(0, i);
            }
            items[Items.SILVER].quantity = 1000;
        }
        else {
            if (Settings.savedItems != null) {
              for (int i = 0; i < items.length; i++) {
                    items[i] = new Items(Settings.savedItems[i].quantity, i);
                }
            }
        }
    }

    public void update(float deltaTime) {

        if(gameOver)
            return;

        Totaltime += deltaTime;

        if(items[Items.SILVER].quantity > higherScore)
            higherScore = items[Items.SILVER].quantity;

        while (Totaltime > timer) {
            Totaltime -= timer;
            if(items[Items.SILVER].quantity <= 0) {
                gameOver = true;
                return;
            }

            for(int i = 1; i < items.length; i++) {
                if(items[i].quantity <= 0) {
                    itemsIsEmpty = true;
                    items[i].quantity = 0;
                }
            }

            count++;

            if(count == 60) {
                if(Settings.soundEnabled)
                    Assets.sfx_tax.play(5);
                taxTime();
                count = 0;
            }

            switch (taxSuccess) {
                case 15:
                    level = TaxLevel.level2;
                    break;
                case 50:
                    level = TaxLevel.level3;
                    break;
                case 100:
                    level = TaxLevel.level4;
                    break;
            }

            if(gameOver)
                Settings.gameOver = true;
        }
    }

    private void taxTime() {

        switch (level) {
            case level1:
                items[Items.SILVER].quantity -= 400;
                taxSuccess++;
                break;
            case level2:
                items[Items.SILVER].quantity -= 1200;
                items[Items.GOLD].quantity -= 10;
                taxSuccess++;
                break;
            case level3:
                items[Items.SILVER].quantity -= 3000;
                items[Items.GOLD].quantity -= 20;
                items[Items.APPLE].quantity -= 10;
                items[Items.JEWELRY].quantity -= 10;
                taxSuccess++;
                break;
            case level4:
                items[Items.SILVER].quantity -= 5000;
                items[Items.GOLD].quantity -= 30;
                items[Items.APPLE].quantity -= 30;
                items[Items.JEWELRY].quantity -= 30;
                break;
        }
    }

    public void reset() {

        Settings.addScore(items[Items.SILVER].quantity);
        for (Items i : items) {
            i.quantity = 0;
        }
        items[Items.SILVER].quantity = 1000;
    }

    public int getHigherScore() {

        return higherScore;
    }
}
