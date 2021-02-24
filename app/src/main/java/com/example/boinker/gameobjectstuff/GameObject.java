package com.example.boinker.gameobjectstuff;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.example.boinker.engine.animator.SpriteAnimation;


public abstract class GameObject {
    public RectF collisionBox;
    public int x,y;
    public Bitmap sprite;
    public Paint paint;
    public Type objectType = Type.DEF;
    public boolean active = false;
    public boolean animated = false;
    public SpriteAnimation animation;
    public enum Type{
        DEF,
        SPIKE,
        PLATFORM,
        CRATE
    }

    public GameObject(Bitmap sprite, int x, int y){
        this.x = x;
        this.y = y;
        paint = new Paint();
        paint.setColor(Color.RED);
        this.sprite = sprite;
        collisionBox = new RectF();
    }

    public void draw(Canvas canvas, boolean details){
        if(details){
            canvas.drawRect(collisionBox,paint);
        }
        if(animated){
            animation.draw(canvas,x,y);
        }else {
            canvas.drawBitmap(sprite, x, y, null);
        }
    }

    public void update(int deltaX) {
        if(animated){
            collisionBox.set(x,y, x + animation.getSpriteWidthI(), y + animation.getSpriteHeightI());
        }else {
            collisionBox.set(x, y, x + sprite.getWidth(), y + sprite.getHeight());
        }
        x -= deltaX;
    }
}
