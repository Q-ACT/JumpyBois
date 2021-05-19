package com.example.boinker.engine.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

public class TextBox {

    private ArrayList<Bitmap> buffer;
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
    private boolean ltr;
    public String tag;

    public TextBox(Bitmap charSheet, String tag, int size, boolean ltr){
        buffer = new ArrayList<>();
        this.ltr = ltr;
        this.tag = tag;
        this.charSheet = Bitmap.createScaledBitmap(charSheet,(9+size)*columns,(13+size)*rows,false);
        this.frameHeight = this.charSheet.getHeight()/rows;
        this.frameWidth = this.charSheet.getWidth()/columns;
    }

    private void addCharacter(int frame){
        int row = frame / columns;
        int column = frame % columns;
        buffer.add(Bitmap.createBitmap(charSheet,column * frameWidth,row*frameHeight,frameWidth,frameHeight));
        Log.d("text", "addedCharacter: "+frame);
    }

    public void draw(Canvas canvas,int x, int y, String text){
        this.text = text.toLowerCase();
        for (int i = 0; i < text.length(); i++){
            addCharacter(chars.indexOf(this.text.charAt(i)));
        }
        if(ltr){
            for (int i = buffer.size()-1; i > 0; i--){
                canvas.drawBitmap(buffer.get(i),x - frameWidth + frameWidth*i,y,null);
            }
        } else{
            for (int i = 0; i < buffer.size(); i++){
                canvas.drawBitmap(buffer.get(i),x+ frameWidth*i,y,null);
            }
        }


        buffer.clear();
    }
}
