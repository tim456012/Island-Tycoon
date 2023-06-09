package com.gameprojectMain;

public class Items {

    public static final int SILVER = 0;
    public static final int GOLD = 1;
    public static final int APPLE = 2;
    public static final int RAW_MEAT = 3;
    public static final int JEWELRY = 4;
    public static final int WINE = 5;
    public static final int MEAT = 6;

    public int quantity;
    public int type;

    public Items(int quantity, int type) {

        this.quantity = quantity;
        this.type = type;
    }

}
