package com.example.boinker.gameobjectstuff.gameobjects;

import android.graphics.Bitmap;

import com.example.boinker.engine.animator.SpriteAnimation;
import com.example.boinker.gameobjectstuff.GameObject;

public class Crate extends GameObject {

    public Crate(Bitmap sprite, int x, int y) {
        super(sprite, x, y);
        objectType = Type.CRATE;
        animated = true;
        animation = new SpriteAnimation(sprite,3,2);
        animation.addAnimation(0,0,false,false);
        animation.addAnimation(1,5,false,false);
        this.y -= animation.getSpriteHeight();
    }

    @Override
    public void update(int deltaX) {
        super.update(deltaX);
        if (active){
            animation.update(1);
        }else{
            animation.update(0);
        }
    }
}
