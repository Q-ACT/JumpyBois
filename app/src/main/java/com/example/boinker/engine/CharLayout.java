package com.example.boinker.engine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class CharLayout {
    private CharCard[] charCards;
    private Boolean[] lockedChars;
    public int currentPlayer;

    CharLayout(Boolean[] lockedChars,Bitmap[] icons, Bitmap[] lockedIcons, Bitmap card,Bitmap activeCard, int currentPlayer){
        this.currentPlayer = currentPlayer;
        charCards = new CharCard[icons.length];
        this.lockedChars = lockedChars;
        int offset;
        offset = 0;
        for (int i = currentPlayer; i < icons.length; i++) {
            charCards[i] = new CharCard(card,activeCard, icons[i],lockedIcons[i],offset * 320,currentPlayer,i);
            offset++;
        }
        offset = 1;
        for(int i = currentPlayer -1; i >= 0; i--){
            charCards[i] = new CharCard(card,activeCard,icons[i],lockedIcons[i],offset * -320,currentPlayer,i);
            offset++;
        }
    }

    void draw(Canvas canvas){
        for (CharCard charCard : charCards) {
            charCard.draw(canvas);
        }
    }

    int touchX;
    int touchY;
    boolean touch;
    void setTouch(float touchX, float touchY){
        this.touchX = (int)touchX;
        this.touchY = (int)touchY;

    }

    private int xDiff;
    private int previousX;
    private int currentX;
    private boolean firstTouch = true;
    private int touchTicks;
    void update(){
        scroll();
        tapCheck();
        for (CharCard charCard : charCards) {
            charCard.currentPlayer = currentPlayer;
        }
    }

    void tapCheck(){
        if(!touch && touchTicks < 20) {
            for (int i = 0; i < charCards.length; i++) {
                charCards[i].setTouch(touchX, touchY);
                if (charCards[i].active){
                    currentPlayer = i;
                    charCards[i].active = false;
                }
            }
            touchTicks = 20;
        }
    }

    void scroll(){
        if(touch) {
            touchTicks++;
            currentX = touchX;
            if(firstTouch){
                previousX = currentX;
                touchTicks = 0;
                firstTouch = false;
            }
            xDiff = (currentX - previousX);
            previousX = currentX;
            for (CharCard charCard : charCards) {
                charCard.shift(xDiff);
            }
        }else {
            firstTouch = true;
        }
    }
}

