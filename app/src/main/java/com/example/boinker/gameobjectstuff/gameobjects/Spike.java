package com.example.boinker.gameobjectstuff.gameobjects;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.boinker.gameobjectstuff.GameObject;

public class Spike extends GameObject {

    private Bitmap sprite;
    private Paint cpaint;

    public Spike(Bitmap sprite, int x, int y){
        super(sprite,x,y);
        objectType = Type.SPIKE;
        this.sprite = sprite;
        cpaint = new Paint();
        cpaint.setColor(Color.GREEN);
        this.y -= sprite.getHeight();
    }

    @Override
    public void update(int deltaX) {
        super.update(deltaX);
        collisionBox.set(x + 45,y + 180, x + 145, y + sprite.getHeight());
    }

    @Override
    public void draw(Canvas canvas, boolean details) {
        super.draw(canvas,details);
        canvas.drawBitmap(sprite,x,y,null);
    }
}
