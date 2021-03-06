package com.example.boinker.engine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import java.util.ArrayList;

class ButtonLayout {
    private ArrayList<MenuButton> buttons;
    boolean anyTouch;
    ButtonLayout(){
        buttons = new ArrayList<>();
    }

    void newButton(int x,int y,int width,int height, String label){
        buttons.add(new MenuButton(x,y,width,height,label));
    }

    void newButton(int x, int y, int width, int height, String label, Bitmap icon,int rows, int columns){
        buttons.add(new MenuButton(x,y,width,height,label,icon,rows,columns));
    }

    void newButton(int x, int y, String label, Bitmap icon, int rows, int columns){
        buttons.add(new MenuButton(x,y,label,icon,rows,columns));
    }

    void setPress(float tX, float tY){
        for(int i = 0; i < buttons.size(); i++) {
            if(buttons.get(i).active) {
                buttons.get(i).setPressed(tX, tY);
                if (buttons.get(i).isPressed) {
                    if (buttons.get(i).icon != null) {
                        buttons.get(i).buttonSprite.setCurrentFrame(0, 1);
                    }
                    anyTouch = true;
                }
            }
        }

    }

    void buttonsUp(){
        for(int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).icon != null) {
                buttons.get(i).buttonSprite.setCurrentFrame(0, 0);

            }
        }
    }

    boolean getPress(String label){
        for(int i = 0; i < buttons.size(); i++) {
            if(buttons.get(i).label.equals(label))
                return buttons.get(i).isPressed;
        }
        return false;
    }

    void drawAll(Canvas canvas){
        for(int i = 0; i < buttons.size(); i++) {
              buttons.get(i).draw(canvas);
        }
    }

    void draw(Canvas canvas){
        for(int i = 0; i < buttons.size(); i++) {
            if(buttons.get(i).active)
                buttons.get(i).draw(canvas);
        }
    }

    void activate(String[] labels){
        for(int i = 0; i < buttons.size(); i++) {
            for(int j = 0; j < labels.length; j++) {
                if (buttons.get(i).label.equals(labels[j])) {
                    buttons.get(i).active = true;
                    break;
                } else {
                    buttons.get(i).active = false;
                }
            }
        }
    }
}
