package com.example.boinker.engine.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

public class TextBox {

    private ArrayList<Bitmap> characters;
    private Bitmap charSheet;
    private final String chars = "abcdefghijklmnopqrstuvwxyz0123456789?! ";
    private int columns = 7;
    private int rows = 6;

    private int frameWidth;
    private int frameHeight;
    private int x;
    private int y;
    private String text;
    private int size;
    public String tag;

    public TextBox(int x, int y, String text, int size, Bitmap charSheet, String tag){
        characters = new ArrayList<>();
        this.tag = tag;
        this.size = size;
        this.text = text.toLowerCase();
        this.charSheet = Bitmap.createScaledBitmap(charSheet,(9+size)*columns,(13+size)*rows,false);
        this.x = x;
        this.y = y;
        this.frameHeight = this.charSheet.getHeight()/rows;
        this.frameWidth = this.charSheet.getWidth()/columns;
        for (int i = 0; i < text.length(); i++){
           addCharacter(chars.indexOf(this.text.charAt(i)));
        }
    }


    private void addCharacter(int frame){
        int row = frame / columns;
        int column = frame % columns;
        characters.add(Bitmap.createBitmap(charSheet,column * frameWidth,row*frameHeight,frameWidth,frameHeight));
        Log.d("text", "addedCharacter: "+frame);
    }

    public void draw(Canvas canvas){
        for (int i = 0; i < characters.size(); i++){
            canvas.drawBitmap(characters.get(i),x+ frameWidth*i,y,null);
        }
    }
}
