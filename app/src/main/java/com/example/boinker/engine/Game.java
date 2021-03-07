package com.example.boinker.engine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import com.example.boinker.R;
import com.example.boinker.gameobjectstuff.GameObject;
import com.example.boinker.gameobjectstuff.ObjectScroller;
import com.example.boinker.gameobjectstuff.Player;
import java.util.ArrayList;
import java.util.Random;


@SuppressLint("ViewConstructor")

public class Game extends SurfaceView implements SurfaceHolder.Callback {

    public enum GameState{
        GAME,
        MENU,
        GAMEOVER,
        CHAR_MENU,
        PAUSE
    }

    String[] gameButtons = {"pause"};
    String[] menuButtons = {"start","char"};
    String[] gameOverButtons = {"resume","main_menu"};
    String[] charMenuButtons = {"back_menu"};
    String[] pauseButtons = {"resume","main_menu"};


    public GameState gameState;
    private MainThread thread;
    private final String SHARED_PREFS = "Shared_Prefs";
    private final String HIGHSCORE = "HighScore";
    private final String COINS = "Coins";

    private Player player;
    public boolean screenTouch;

    public static int screenWidth, screenHeight;
    private boolean newGame;
    private Paint tpaint,tpaint2,fogPaint;
    private int points,highScore;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ButtonLayout buttons;
    boolean gameHoldTouch = false;
    private ObjectScroller objectScroller;
    private CharLayout charLayout;
    public static ArrayList<GameObject> gameObjects;
    private int currentPlayer = 2;


// Set extraDetails to true to show hitboxes, spawn rate, etc
    boolean extraDetails = false;
//-----------------------------------------------------------------------------------

    private Bitmap[] objectSprites = {
            BitmapFactory.decodeResource(getResources(), R.drawable.spikes2),
            BitmapFactory.decodeResource(getResources(), R.drawable.platform),
            BitmapFactory.decodeResource(getResources(), R.drawable.crate),
    };
    private Bitmap[] playerSprites = {
            BitmapFactory.decodeResource(getResources(), R.drawable.runnyboi),
            BitmapFactory.decodeResource(getResources(), R.drawable.sticky),
            BitmapFactory.decodeResource(getResources(), R.drawable.brainbot),
            BitmapFactory.decodeResource(getResources(), R.drawable.squidboi)


    };
    private Bitmap[] playerSkinIcons = {
            BitmapFactory.decodeResource(getResources(), R.drawable.runnyboiicon),
            BitmapFactory.decodeResource(getResources(), R.drawable.stickyicon),
            BitmapFactory.decodeResource(getResources(), R.drawable.brainboticon),
            BitmapFactory.decodeResource(getResources(), R.drawable.squidboiicon)
    };
    private Bitmap[] lockedPlayerSkinIcons = {
            BitmapFactory.decodeResource(getResources(), R.drawable.runnyboiicon),
            BitmapFactory.decodeResource(getResources(), R.drawable.lockedsticky),
            BitmapFactory.decodeResource(getResources(), R.drawable.lockedbrain),
            BitmapFactory.decodeResource(getResources(), R.drawable.lockedsquid)
    };
    private Boolean[] lockedPlayers = {
            false,
            false,
            false,
            false
    };
    private Bitmap
        title = BitmapFactory.decodeResource(getResources(), R.drawable.ui_title),
        pauseWindow = BitmapFactory.decodeResource(getResources(), R.drawable.ui_ppage),
        groundTexture = BitmapFactory.decodeResource(getResources(), R.drawable.ground),
        coinSheet =  BitmapFactory.decodeResource(getResources(), R.drawable.coin),
        card =  BitmapFactory.decodeResource(getResources(), R.drawable.ui_charpage),
        activeCard = BitmapFactory.decodeResource(getResources(), R.drawable.ui_charpageactive),
    //                                  ui buttons
    //-----------------------------------------------------------------------------------------
            bButton = BitmapFactory.decodeResource(getResources(), R.drawable.ui_bbutton),
            chButton = BitmapFactory.decodeResource(getResources(), R.drawable.ui_chbutton),
            stButton = BitmapFactory.decodeResource(getResources(), R.drawable.ui_stbutton),
            rsButton = BitmapFactory.decodeResource(getResources(), R.drawable.ui_rsbutton),
            mmButton = BitmapFactory.decodeResource(getResources(), R.drawable.ui_mmbutton),
            pButton = BitmapFactory.decodeResource(getResources(), R.drawable.ui_pbutton);

    public Game(Context context, Point size) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        screenWidth = size.x;
        screenHeight = size.y;
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gameState = GameState.MENU;

        buttons = new ButtonLayout();
        objectScroller = new ObjectScroller(groundTexture,objectSprites);
        gameObjects = new ArrayList<>();
        player = new Player(playerSprites[currentPlayer], coinSheet);
        charLayout = new CharLayout(lockedPlayers,playerSkinIcons,lockedPlayerSkinIcons,card,activeCard,currentPlayer);

        tpaint = new Paint();
        tpaint2 = new Paint();
        fogPaint = new Paint();
        tpaint.setColor(Color.WHITE);
        tpaint2.setColor(Color.LTGRAY);
        fogPaint.setColor(Color.argb(200,30,30,30));
        tpaint2.setTextSize(40);
        tpaint.setTextSize(50);
        points = 0;

        loadHighScore();
        buttons.newButton(100,100,"pause",pButton,2,1);
        buttons.newButton(30,screenHeight - 100,50,50,"back_menu");
        buttons.newButton(screenWidth/4, (int) (screenHeight * 0.8F),"start",stButton,2,1);
        buttons.newButton((int)(screenWidth * 0.75F),(int) (screenHeight * 0.8F),"char",chButton,2,1);

        buttons.newButton(screenWidth/2 - 20 , screenHeight/2 - 25,"resume",rsButton,2,1);
        buttons.newButton(screenWidth/2 - 20, screenHeight/2 + 110,"main_menu",mmButton,2,1);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d("Surface", "SurfaceChanged");
        gameState = GameState.MENU;
        newGame = true;
        points = 0;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        Log.d("Thread", "threadState: " + thread.getState());
        thread.start();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {

            switch (gameState) {
                case GAME:
                    drawObjects(canvas);
                    objectScroller.draw(canvas);
                    player.draw(canvas, extraDetails);

                    buttons.draw(canvas);
                    canvas.drawText(String.valueOf(points),screenWidth - 200, 100,tpaint);
                    canvas.drawText(String.valueOf(highScore),screenWidth - 200, 150,tpaint2);
                    canvas.drawText(String.valueOf(coins),screenWidth - 200, 200,tpaint2);
                    break;

                case MENU:
                    canvas.drawBitmap(title,screenWidth/2F - title.getWidth()/2F - 20, screenHeight/3F - title.getHeight()/2F,null);
                    buttons.draw(canvas);
                    break;

                case GAMEOVER:
                    drawObjects(canvas);
                    objectScroller.draw(canvas);
                    player.draw(canvas, extraDetails);

                    canvas.drawText("u ded", screenWidth / (float) 4, screenHeight / (float) 5, tpaint);
                    buttons.draw(canvas);
                    canvas.drawText(String.valueOf(points),screenWidth - 200, 100,tpaint);
                    canvas.drawText(String.valueOf(highScore),screenWidth - 200, 150,tpaint2);
                    canvas.drawText(String.valueOf(coins),screenWidth - 200, 200,tpaint2);
                    break;

                case PAUSE:
                    player.draw(canvas, extraDetails);
                    objectScroller.draw(canvas);
                    drawObjects(canvas);

                    canvas.drawRect(0,0,screenWidth,screenHeight,fogPaint);
                    canvas.drawBitmap(pauseWindow, screenWidth/2F - pauseWindow.getWidth()/2F - 20, screenHeight/20F,null);
                    buttons.draw(canvas);
                    canvas.drawText(String.valueOf(points),screenWidth - 200, 100,tpaint);
                    canvas.drawText(String.valueOf(highScore),screenWidth - 200, 150,tpaint2);
                    canvas.drawText(String.valueOf(coins),screenWidth - 200, 200,tpaint2);
                    break;

                case CHAR_MENU:
                    canvas.drawText("Character Selection", screenWidth / (float) 4, screenHeight / (float) 5, tpaint);
                    buttons.draw(canvas);
                    charLayout.draw(canvas);
                    break;
            }
        }
    }

    public void resetObjects(){
        gameObjects.clear();
    }

    public void drawObjects(Canvas canvas){
        for(int i = 0; i < gameObjects.size(); i++){
            gameObjects.get(i).draw(canvas,extraDetails);
        }
    }
    public void updateObjects(){
        for(int i = gameObjects.size()-1; i >= 0; i--){
            gameObjects.get(i).update(player.xVelocity);
            checkCollision(i);
            if( gameObjects.get(i).collisionBox.right < -100){
                gameObjects.remove(i);
            }
        }
    }



    Random random = new Random();
    int coins = 0;
    public void checkCollision(int i){
        player.onGround = false;
        if(gameObjects.get(i).collisionBox.left < player.collisionBox.right &&
            gameObjects.get(i).collisionBox.right > player.collisionBox.left &&
            gameObjects.get(i).collisionBox.top < player.collisionBox.bottom &&
            gameObjects.get(i).collisionBox.bottom > player.collisionBox.top)
        {
            switch( gameObjects.get(i).objectType) {
                case SPIKE:
                player.playerState = Player.State.DEAD;
                break;

                case PLATFORM:
                    if(player.collisionBox.bottom >= gameObjects.get(i).collisionBox.top && gameObjects.get(i).collisionBox.top > player.y){
                        player.y = gameObjects.get(i).collisionBox.top - player.playerSprite.getSpriteHeight()/2;
                        player.yVelocity = 10;
                        player.onGround = true;
                    } else if(player.collisionBox.top <= gameObjects.get(i).collisionBox.bottom && gameObjects.get(i).collisionBox.bottom < player.y){
                        player.yVelocity = 0;
                        player.y = gameObjects.get(i).collisionBox.bottom + player.playerSprite.getSpriteHeight()/2;
                    }
                    break;
                case CRATE:
                    if(!gameObjects.get(i).active) {
                        player.xVelocity -= player.xVelocity/5;
                        player.getCoin();
                        gameObjects.get(i).active = true;
                        coins++;
                    }
                    break;
                case DEF:
                    // object has undefined object type
                    break;
            }
        }
    }

    public void update() {

        switch (gameState) {

            case GAME:
                buttons.activate(gameButtons);
                if (newGame) {
                    player.reset();
                    resetObjects();
                    newGame = false;
                    points = 0;
                }
                player.handleJumpInput(screenTouch);
                player.update();
                updateObjects();
                objectScroller.update(player.xVelocity,(int)player.ground);

                if (player.playerState == Player.State.DEAD)
                    gameState = GameState.GAMEOVER;
                if (buttons.getPress("pause"))
                    gameState = GameState.PAUSE;


                points++;
                gameHoldTouch = screenTouch;
                break;

            case GAMEOVER:
                buttons.activate(gameOverButtons);
                editor.putInt(COINS,coins);
                if(points > highScore) {
                    highScore = points;
                    highScore = points;
                    saveHighScore();
                }
                player.update();
                    gameHoldTouch = false;
                newGame = true;
                if(buttons.getPress("resume"))
                    gameState = GameState.GAME;
                else if (buttons.getPress("main_menu"))
                    gameState = GameState.MENU;
                break;

            case MENU:
                player.reset();
                resetObjects();
                newGame = false;
                points = 0;
                buttons.activate(menuButtons);
                if(buttons.getPress("start"))
                    gameState = GameState.GAME;
                if(buttons.getPress("char"))
                    gameState = GameState.CHAR_MENU;
                break;

            case PAUSE:
                buttons.activate(pauseButtons);
                if(buttons.getPress("resume"))
                    gameState = GameState.GAME;
                else if (buttons.getPress("main_menu")) {
                    gameState = GameState.MENU;
                    newGame = true;
                }
                break;

            case CHAR_MENU:
                charLayout.update();
                buttons.activate(charMenuButtons);
                if (buttons.getPress("back_menu")) {
                    currentPlayer = charLayout.currentPlayer;
                    player.switchSprite(playerSprites[currentPlayer]);
                    gameState = GameState.MENU;
                }
                break;
        }
        buttons.setPress(-1,-1);
    }

    private void saveHighScore(){
        editor.putInt(HIGHSCORE,highScore);
        editor.apply();
    }

    private void loadHighScore(){
        highScore = sharedPreferences.getInt(HIGHSCORE,0);
        coins = sharedPreferences.getInt(COINS,0);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch(event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
               buttons.setPress(event.getX(),event.getY());
               if(!buttons.anyTouch)
                    screenTouch = true;
               else
                buttons.anyTouch = false;
                return true;
            case MotionEvent.ACTION_MOVE:
                charLayout.setTouch(event.getX(),event.getY());
                charLayout.touch = true;
                return true;
            case MotionEvent.ACTION_UP:
                buttons.buttonsUp();
                screenTouch = false;
                charLayout.touch = false;
                return true;
        }


        return super.onTouchEvent(event);
    }
}
