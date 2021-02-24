package com.example.boinker.gameobjectstuff.gameobjects;

import android.graphics.Bitmap;

import com.example.boinker.gameobjectstuff.GameObject;

public class Platform extends GameObject {

    public Platform(Bitmap sprite, int x, int y) {
        super(sprite, x, y);
        objectType = Type.PLATFORM;
    }
}
