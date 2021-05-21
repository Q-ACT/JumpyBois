package com.example.boinker.engine.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import com.example.boinker.engine.Game;

public class CharCard {
    private Bitmap card;
    private Bitmap activeCard;
    private Bitmap skinIcon;
    private Bitmap skinIconLocked;
    public int x;
    private int y;
    private int height;
    private int width;
    public boolean unlocked;
    boolean active;
    private int index;
    int currentPlayer;

    CharCard(Bitmap card, Bitmap activeCard, Bitmap skinIcon, Bitmap skinIconLocked, int xOffset, int currentPlayer, int index, boolean unlocked){
        this.index = index;
        this.currentPlayer = currentPlayer;
        this.card = card;
        this.activeCard = activeCard;
        this.skinIcon = skinIcon;
        this.skinIconLocked = skinIconLocked;
        this.unlocked = unlocked;
        height = card.getHeight();
        width = card.getWidth();
        x = Game.screenWidth / 2 - width / 2;
        y = Game.screenHeight / 2 - height / 2;
        x += xOffset;
    }

    public void draw(Canvas canvas){
        if(currentPlayer == index){
            canvas.drawBitmap(activeCard,x,y,null);
        } else{
            canvas.drawBitmap(card,x,y,null);
        }
        if(unlocked){
            canvas.drawBitmap(skinIcon,x,y,null);
        } else{
            canvas.drawBitmap(skinIconLocked,x,y,null);
        }
    }

    public void shift(int xShift){
        x += xShift;
    }

    public void setTouch(int touchX, int touchY){
        if(touchX > x && touchX < x + width && touchY > y && touchY < y + height){
            active = true;
        }
    }
}
