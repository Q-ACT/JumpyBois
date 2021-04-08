package com.example.boinker.engine.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

public class TextLayout {
    private Bitmap charSheet;
    private ArrayList<TextBox> textBoxes;

    public TextLayout(Bitmap charSheet){
        textBoxes = new ArrayList<>();
        this.charSheet = charSheet;
    }

    public void newBox(int x, int y, String text, int size, String tag){
        textBoxes.add(new TextBox(x,y,text,size,charSheet,tag));
    }

     public void draw(Canvas canvas,String tag){
       for(int i = 0; i <= textBoxes.size()-1; i++){
           //if(textBoxes.get(i).tag.equals(tag)){
               Log.d("text", "render");
               textBoxes.get(i).draw(canvas);
           }
       }
    //}
}
