package com.example.boinker.engine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class CharCard {
    private Bitmap card;
    private Bitmap activeCard;
    private Bitmap skinIcon;
    private Bitmap skinIconLocked;
    private int x;
    private int y;
    private int height;
    private int width;
    private boolean unlocked = true;
    boolean active;
    private int index;
    int currentPlayer;

    CharCard(Bitmap card, Bitmap activeCard, Bitmap skinIcon, Bitmap skinIconLocked, int xOffset, int currentPlayer, int index){
        this.index = index;
        this.currentPlayer = currentPlayer;
        this.card = card;
        this.activeCard = activeCard;
        this.skinIcon = skinIcon;
        this.skinIconLocked = skinIconLocked;
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
        canvas.drawBitmap(skinIcon,x,y,null);
    }

    public void shift(int xShift){
        x += xShift;
    }

    public void setTouch(int touchX,int touchY){
        if(touchX > x && touchX < x + width && touchY > y && touchY < y + height){
            if(unlocked){
                active = true;
            }
        }
    }
}
