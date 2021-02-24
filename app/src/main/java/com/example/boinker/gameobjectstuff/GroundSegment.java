package com.example.boinker.gameobjectstuff;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class GroundSegment extends GameObject {

    GroundSegment(Bitmap sprite,int x, int y) {
        super(sprite, x, y);
        objectType = Type.PLATFORM;
        this.x = x;
        this .y = y;
    }

    @Override
    public void update(int deltaX) {
        x -= deltaX;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(sprite,x,y,null);
    }
}
