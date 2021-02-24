package com.example.boinker.engine.animator;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import java.util.ArrayList;

public class SpriteAnimation {

    public Bitmap sprite;
    private int columns;
    private float frameHeight, frameWidth;
    private ArrayList<Animation> animations;
    private int previousAnimation = 0;

    public SpriteAnimation(Bitmap spriteSheet, int rows, int columns){

        sprite = spriteSheet;
        frameHeight = (float)sprite.getHeight() / rows;
        frameWidth = (float)sprite.getWidth() / columns;
        this.columns = columns;
        animations = new ArrayList<>();
    }

    public void addAnimation(int startFrame, int endFrame, boolean randomStart, boolean loop){
        animations.add(new Animation(startFrame, endFrame, columns, frameWidth, frameHeight, randomStart, loop));
    }

    public void update(int animation){
        if(animation != previousAnimation) {
            animations.get(previousAnimation).currentFrame = animations.get(previousAnimation).startFrame;
        }
        previousAnimation = animation;
        animations.get(animation).update();
    }

    public Animation getCurrentAnimation(){
        return animations.get(previousAnimation);
    }

    public void draw(Canvas canvas, int x, int y){
         animations.get(previousAnimation).draw(canvas, x, y,sprite);
    }


    public float getSpriteHeight(){
        return frameHeight;
    }
    public float getSpriteWidth(){
        return frameWidth;
    }

    public int getSpriteHeightI(){
        return (int)frameHeight;
    }
    public int getSpriteWidthI(){
        return (int)frameWidth;
    }

    public void setCurrentFrame(int animation, int frame){
        animations.get(animation).setCurrentFrame(frame);
    }

}

