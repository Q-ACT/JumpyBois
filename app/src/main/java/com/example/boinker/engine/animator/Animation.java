package com.example.boinker.engine.animator;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import java.util.Random;

class Animation {

    private int row, column;
    private int i = 0;
    private boolean loop;
    int currentFrame;
    int startFrame, endFrame;
    private int columns;
    private float frameHeight,frameWidth;
    private final int framesToSkip = 5;

    Animation(int startFrame, int endFrame, int columns, float fWidth,float fHeight, boolean randomStart, boolean loop){
        this.loop = loop;
        this.columns = columns;
        frameWidth = fWidth;
        frameHeight = fHeight;
        Random random = new Random();
        this.startFrame = startFrame;
        this.endFrame = endFrame;

        if(randomStart){
            currentFrame = random.nextInt(endFrame);
        }else{
            currentFrame = startFrame;
        }
    }

    void update(){
        if(!(!loop && currentFrame > endFrame)) {
            if (i == framesToSkip) {
                if (currentFrame > endFrame)
                    currentFrame = startFrame;
                row = currentFrame / columns;
                column = currentFrame % columns;
                currentFrame++;
                i = 0;
            }
            i++;
        }
    }

    void setCurrentFrame(int frame){
        row = frame / columns;
        column = frame % columns;
    }

    void draw(Canvas canvas, int x, int y, Bitmap sprite){
        float srcY = row * frameHeight,
                srcX = column * frameWidth;
        Rect src = new Rect( (int) srcX, (int) srcY + 1, (int) srcX + (int) frameWidth, (int) srcY + (int) frameHeight);
        RectF dst = new RectF(x, y, x + frameWidth, y + frameHeight);

        canvas.drawBitmap(sprite,src,dst,null);
    }


}
