package com.example.boinker.engine.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class CharLayout {
    public CharCard[] charCards;
    public int currentPlayer;
    Boolean[] unlockedPlayers;
    public CharLayout(Boolean[] unlockedChars,Bitmap[] icons, Bitmap[] lockedIcons, Bitmap card,Bitmap activeCard, int currentPlayer){
        this.currentPlayer = currentPlayer;
        charCards = new CharCard[icons.length];
        unlockedPlayers = unlockedChars;
        int offset;
        offset = 0;
        for (int i = currentPlayer; i < icons.length; i++) {
            charCards[i] = new CharCard(card,activeCard, icons[i],lockedIcons[i],offset * 320,currentPlayer,i,unlockedChars[i]);
            offset++;
        }
        offset = 1;
        for(int i = currentPlayer -1; i >= 0; i--){
            charCards[i] = new CharCard(card,activeCard,icons[i],lockedIcons[i],offset * -320,currentPlayer,i,unlockedChars[i]);
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

    private int coins;
    public void tapCheck(int coins){
        this.coins = coins;
        touch = false;
        for (int i = 0; i < charCards.length; i++) {
                charCards[i].setTouch(touchX, touchY);
                if (charCards[i].active){
                    if(charCards[i].unlocked){
                        currentPlayer = i;
                    }else if(i*10 <= coins){
                        this.coins -= i*10;
                        charCards[i].unlocked = true;
                        unlockedPlayers[i] = true;
                        Log.d("bought", "player: " +i);
                    }
                    charCards[i].active = false;
                }
        }
    }

    public int coinUpdates(){
        return coins;
    }
    public Boolean[] getUnlockedPlayers(){
        return unlockedPlayers;
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

