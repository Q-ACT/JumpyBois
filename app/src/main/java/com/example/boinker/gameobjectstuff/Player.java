package com.example.boinker.gameobjectstuff;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import com.example.boinker.engine.Game;
import com.example.boinker.engine.animator.SpriteAnimation;


public class Player {

    public float y,x;
    private Paint cpaint;

    public int yVelocity = 10;
    public int xVelocity = 10;
    private int ticks;
    private boolean touch;
    public float ground;

    public SpriteAnimation playerSprite;
    public SpriteAnimation coinSprite;
    public RectF collisionBox;
    public boolean onGround;

    public enum State{
        IDLE,
        JUMP,
        FALL,
        DEAD
    }

    public State playerState;

    public Player(Bitmap bitmap, Bitmap coin) {
        playerSprite = new SpriteAnimation(bitmap,4,4);
        playerSprite.addAnimation(0,5,false,true);
        playerSprite.addAnimation(6,15,false,false);

        coinSprite = new SpriteAnimation(coin,3,2);
        coinSprite.addAnimation(0,5,false,true);
        coinSprite.addAnimation(6,6,false,false);

        ground = (float)(Game.screenHeight * 0.8);

        x = (float) Game.screenWidth / 4;
        y = ground - playerSprite.getSpriteHeight()/2;
        coinY = (int)y;

        Paint bpaint = new Paint();
        cpaint = new Paint();

        bpaint.setColor(Color.GREEN);
        cpaint.setColor(Color.GREEN);
        Log.d("Metrics", "Width: " + Game.screenWidth + ", Height: " + Game.screenHeight);

        playerState = State.IDLE;

    }

    public void switchSprite(Bitmap sprite){
        playerSprite.sprite = sprite;
    }

    public void reset(){
        playerState = State.FALL;
        xVelocity = 10;
    }

    public void draw(Canvas canvas,boolean details) {
        if(details)
            canvas.drawRect(collisionBox, cpaint);
        if(coinGet)
            coinSprite.draw(canvas,(int)x - playerSprite.getSpriteWidthI()/2,coinY - playerSprite.getSpriteWidthI()/2);
        playerSprite.draw(canvas, (int) x - playerSprite.getSpriteWidthI()/2, (int) y - playerSprite.getSpriteHeightI()/2);
    }

    public void handleJumpInput(boolean touch) {
        this.touch = touch;
            if (touch && playerState == State.IDLE ||touch && onGround) {
                onGround = false;
                playerState = State.JUMP;
            }
    }

    private int i;
    //private int previousY;
    //private int deltaY;
    public void update() {
       // previousY = (int)y;
        if(i == 120){
            xVelocity++;
            i = 0;
        }else {
            i++;
        }

        switch(playerState){
            case JUMP:
                ticks++;
                Log.d("state", "Jumping");
                playerSprite.update(0);
                if(yVelocity <= 0){
                    playerState = State.FALL;
                    yVelocity = 0;
                    break;
                }

                y -= yVelocity * 2;
                if(!touch || ticks > 10) {
                    yVelocity -= 2;
                }
                break;
            case FALL:
                ticks = 0;
                playerSprite.update(0);
                if(y >= ground - playerSprite.getSpriteHeight()/2){
                    y = ground - playerSprite.getSpriteHeight()/2;
                    yVelocity = 10;
                    playerState = State.IDLE;
                    break;
                }
                y += yVelocity;
                yVelocity += 2;
                break;
            case IDLE:
                playerSprite.update(0);
                break;
            case DEAD:
                playerSprite.update(1);
                if(y >= ground - playerSprite.getSpriteHeight()/2){
                    y = ground - playerSprite.getSpriteHeight()/2;
                    yVelocity = 10;
                    break;
                }
                y += yVelocity;
                yVelocity += 2;
                break;
        }
       // deltaY = (int)y - previousY;
        if(coinGet){
            coinJumpTick();
        }
        collisionBox = new RectF(x - 40,y - 20,x + 32,y + playerSprite.getSpriteHeight()/2);
    }

    public void getCoin(){
        coinY = (int)y;
        coinGet = true;
        coinGetPending++;
    }

    private int coinY;
    private int coinYVelocity = 20;
    private int coinGetPending;
    private boolean coinGet = false;

    private void coinJumpTick(){
            coinSprite.update(0);
            //coinY += deltaY;
            coinY -= coinYVelocity--;
            if(coinY >= y-10 || coinYVelocity <= -20 ) {
                coinY = (int)y;
                coinYVelocity = 20;
                coinSprite.update(1);
                coinGetPending--;
                coinGet = !(coinGetPending == 0);
            }
    }
}
