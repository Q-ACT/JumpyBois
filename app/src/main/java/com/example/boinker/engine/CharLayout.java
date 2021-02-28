package com.example.boinker.engine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

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
        for(int i = 0; i < charCards.length; i++){
            charCards[i].draw(canvas);
        }
    }
    void update(int touchX, int touchY, boolean touch){

    }
}

