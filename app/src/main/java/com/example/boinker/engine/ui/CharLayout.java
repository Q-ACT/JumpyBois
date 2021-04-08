package com.example.boinker.engine.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class CharLayout {
    private CharCard[] charCards;
    private Boolean[] lockedChars;
    public int currentPlayer;

    public CharLayout(Boolean[] lockedChars,Bitmap[] icons, Bitmap[] lockedIcons, Bitmap card,Bitmap activeCard, int currentPlayer){
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

    public void draw(Canvas canvas){
        for (CharCard charCard : charCards) {
            charCard.draw(canvas);
        }
    }

    int touchX;
    int touchY;
    boolean touch;
    public void setTouch(float touchX, float touchY){
        touch = true;
        this.touchX = (int)touchX;
        this.touchY = (int)touchY;

    }

    private int xDiff;
    private int previousX;
    private int currentX;
    private boolean firstTouch = true;
    private int touchTicks;
    public void update(){
        scroll();
        for (CharCard charCard : charCards) {
            charCard.currentPlayer = currentPlayer;
        }
    }

    public void tapCheck(){
        touch = false;
        for (int i = 0; i < charCards.length; i++) {
                charCards[i].setTouch(touchX, touchY);
                if (charCards[i].active){
                    currentPlayer = i;
                    charCards[i].active = false;
                }
            }
    }

    void scroll(){
        if(touch) {
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

