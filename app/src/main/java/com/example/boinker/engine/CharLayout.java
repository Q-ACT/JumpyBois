package com.example.boinker.engine;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class CharLayout {
    private CharCard[] charCards;
    private Boolean[] lockedChars;

    CharLayout(Boolean[] lockedChars,Bitmap[] icons, Bitmap[] lockedIcons, Bitmap[] spriteSheets, Bitmap card, int currentPlayer){
        charCards = new CharCard[icons.length-1];
        this.lockedChars = lockedChars;
        int offset = 0;
        for(int i = currentPlayer; i == icons.length-1; i++){
            charCards[i] = new CharCard(card,icons[i],lockedIcons[i],offset * 20);
            offset++;
        }
        offset = 0;
        for(int i = currentPlayer - 1; i == 0; i--){
            charCards[i] = new CharCard(card,icons[i],lockedIcons[i],offset * -20);
            offset++;
        }
    }

    void draw(Canvas canvas){
        for(int i = charCards.length-1; i > 0; i--){
            charCards[i].draw(canvas);
        }
    }
    void update(int touchX, int touchY, boolean touch){

    }
}

