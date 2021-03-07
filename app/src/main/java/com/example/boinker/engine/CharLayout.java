package com.example.boinker.engine;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class CharLayout {
    private CharCard[] charCards;
    private Boolean[] lockedChars;

    CharLayout(Boolean[] lockedChars,Bitmap[] icons, Bitmap[] lockedIcons, Bitmap[] spriteSheets, Bitmap card, int currentPlayer){
        charCards = new CharCard[icons.length];
        this.lockedChars = lockedChars;
        int offset;
        offset = 0;
        for (int i = currentPlayer; i < icons.length; i++) {
            charCards[i] = new CharCard(card, icons[i], lockedIcons[i], offset * 320);
            offset++;
        }
        offset = 1;
        for(int i = currentPlayer -1; i >= 0; i--){
            charCards[i] = new CharCard(card,icons[i],lockedIcons[i],offset * -320);
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
    }

    void tapCheck(){
        for(CharCard charCard : charCards){
            charCard.setTouch(touchX,touchY);
        }
    }

    void scroll(){
        if(touch) {
            currentX = touchX;
            if(firstTouch){
                previousX = currentX;
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

