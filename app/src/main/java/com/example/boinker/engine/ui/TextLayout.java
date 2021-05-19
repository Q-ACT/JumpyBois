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

    public void newBox(String tag,int size,boolean ltr){
        textBoxes.add(new TextBox(charSheet,tag,size,ltr));
    }

//    public void newBox(String tag,int size){
//        textBoxes.add(new TextBox(charSheet,tag,size,false));
//    }

     public void draw(Canvas canvas,int x, int y, String text, String tag){
       for(int i = 0; i <= textBoxes.size()-1; i++){
           if(textBoxes.get(i).tag.equals(tag)){
               Log.d("text", "render");
               textBoxes.get(i).draw(canvas,x,y,text);
           }
       }
    }
}
