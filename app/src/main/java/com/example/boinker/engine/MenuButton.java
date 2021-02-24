package com.example.boinker.engine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.boinker.engine.animator.SpriteAnimation;

class MenuButton {
    private int height,width,x,y;
    boolean isPressed;
    String label;
    private Paint paint;
    boolean active;
    Bitmap icon;
    SpriteAnimation buttonSprite;
    private boolean autoBound;

    MenuButton(int x, int y, int width, int height, String label){
        this.label = label;
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        paint = new Paint();
        paint.setColor(Color.WHITE);
    }

    MenuButton(int x, int y, int width, int height, String label, Bitmap icon, int rows, int columns){
        this.icon = icon;
        this.label = label;
        this.height = height;
        this.width = width;
        buttonSprite = new SpriteAnimation(icon,rows,columns);
        buttonSprite.addAnimation(0,1,false,false);
        this.x = x;
        this.y = y;
    }

    MenuButton(int x, int y, String label, Bitmap icon, int rows, int columns){
        this.icon = icon;
        this.label = label;
        this.width = icon.getWidth() / columns;
        this.height = icon.getHeight() / rows;
        buttonSprite = new SpriteAnimation(icon,rows,columns);
        buttonSprite.addAnimation(0,1,false,true);
        this.x = x - buttonSprite.getSpriteWidthI()/2;
        this.y = y - buttonSprite.getSpriteHeightI()/2;
    }

    void setPressed(float touchX, float touchY){
        isPressed = touchX > x
                && touchX < x + width
                && touchY > y
                && touchY < y + height;
    }

    void release(){
        isPressed = false;
    }

    void draw(Canvas canvas){
        if(icon == null)
            canvas.drawRect(x,y,x+width,y+height,paint);
        else
            buttonSprite.draw(canvas,x,y);
    }
}
