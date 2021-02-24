package com.example.boinker.engine;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class CharCard {
    private Bitmap card;
    private Bitmap skinIcon;
    private Bitmap skinIconLocked;
    private int x;
    private int y;
    private int height;
    private int width;

    CharCard(Bitmap card, Bitmap skinIcon,Bitmap skinIconLocked, int xOffset){
        this.card = card;
        this.skinIcon = skinIcon;
        this.skinIconLocked = skinIconLocked;
        height = card.getHeight();
        width = card.getWidth();
        x = Game.screenWidth / 2 - width / 2;
        y = Game.screenWidth / 5;
        x -= xOffset;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(card,x,y,null);
        canvas.drawBitmap(skinIcon,x,y,null);
    }

    public boolean getTouch(int touchX,int touchY, boolean touch){
        return touch
                && touchX > x
                && touchX < x + width
                && touchY > y
                && touchY < y + height;
    }
}
