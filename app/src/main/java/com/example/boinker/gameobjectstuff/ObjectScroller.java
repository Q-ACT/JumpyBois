package com.example.boinker.gameobjectstuff;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import com.example.boinker.engine.Game;
import com.example.boinker.gameobjectstuff.gameobjects.Crate;
import com.example.boinker.gameobjectstuff.gameobjects.Platform;
import com.example.boinker.gameobjectstuff.gameobjects.Spike;

import java.util.ArrayList;
import java.util.Random;

public class ObjectScroller {
    private ArrayList<GroundSegment> groundSegments;
    private int groundHeight = (int) (Game.screenHeight * 0.8);
    private Bitmap texture;
    private boolean spawning;
    private int spawnGap;
    private int tick = 0;
    private int x = Game.screenWidth + 100, y;
    private Random random;
    private Bitmap[] objectSprite;

    public ObjectScroller(Bitmap texture,Bitmap[] objectSprite){
        this.objectSprite = objectSprite;
        groundSegments = new ArrayList<>();
        random = new Random();
        this.texture = texture;
        fillGround(texture);
    }

    private void fillGround(Bitmap texture){
        while(getTotalWidth() < Game.screenWidth + texture.getWidth()*2){
            groundSegments.add(new GroundSegment(texture,getTotalWidth(),groundHeight));
            Log.d("FILL", "added ground segment");
        }
    }

    private int getTotalWidth(){
        return groundSegments.size() * texture.getWidth();
//        int width = 0;
//        for(int i = 0;i < groundSegments.size(); i++){
//            width += groundSegments.get(i).sprite.getWidth();
//        }
//        return width;
    }

    public void update(int xVelocity,int y){
        this.y = y;
        for(int i = 0;i < groundSegments.size(); i++){
            if(groundSegments.get(i).x + texture.getWidth() <= 0) {
                groundSegments.get(i).x += getTotalWidth();
                doSpawnTick();
            }
            groundSegments.get(i).x -= xVelocity;
        }
    }

    public void draw(Canvas canvas){
        for(int i = 0;i < groundSegments.size(); i++){
            groundSegments.get(i).draw(canvas);
        }
    }

    private void doSpawnTick(){
        if(!spawning) {
            spawnGap = random.nextInt(5);
            spawning = true;
        }
        if(tick == spawnGap) {
            spawnObject();
            spawning = false;
            tick = 0;
        }else{
            tick++;
        }
    }

    private void spawnObject(){
        int spawnType = random.nextInt(9);
        if(spawnType < 6){
            Game.gameObjects.add(new Spike(objectSprite[0],x,y));
        }else if(spawnType == 6){
            Game.gameObjects.add(new Platform(objectSprite[1],x,groundHeight - 200));
        }else /*if(spawnType >= 6)*/{
            Game.gameObjects.add(new Crate(objectSprite[2],x,y));
        }
    }
}
