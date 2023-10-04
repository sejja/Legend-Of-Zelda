//
//	PlayState.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 15/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Gameplay.States;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Vector;

import Engine.ECSystem.ObjectManager;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Sprite;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Objects.FontObject;
import Engine.Graphics.Tile.TileManager;
import Engine.Input.InputFunction;
import Engine.Input.InputManager;
import Engine.Math.Vector2D;
import Engine.StateMachine.State;
import Engine.StateMachine.StateMachine;
import Gameplay.Enemies.*;
import Gameplay.Link.Arrow;
import Gameplay.Link.Player;
import Gameplay.NPC.Npc;

public class PlayState extends State {
    private FontObject mFont;
    private FontObject mFont2;
    private Player mPlayer;
    private Npc mNpc1;
    private Npc mNpc2;
    private static ArrayList<Npc> mNpcArrayList = new ArrayList<Npc>();
    private Enemy mEnemy;
    private Enemy mEnemy2;
    private Enemy mEnemy3;
    private Vector2D<Float> mPos;
    private TileManager mTilemap;
    private ArrayList<String> dialogueArrayList = new ArrayList<String>();
    private ArrayList<String> dialogueArrayList2 = new ArrayList<String>();
    private static int gameState = 1;
    private final static int playState = 1;
    private final static int pauseState = 2;
    private boolean mPause = false;

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Just assigns the statemachine child
    */ //----------------------------------------------------------------------
    public PlayState() {
        dialogueArrayList.add("En un mundo muy lejano] vivia una princesa que buscaba a su \nprincipe] y para logralo] llamo a todos los principes del reino \nademas deberas vuscar todos los artefactos de las piedras para \necuperar el poder de hyrule");
        dialogueArrayList.add("Ahora embarcate en una nueva aventura junto a tu espada y tu \narco");
        dialogueArrayList2.add("Muy buenas caballero] mi nombre es Juan] y estoy aqui protegiendo \nla puerta de acceso al palacio");
        dialogueArrayList2.add("A si que abandona este lugar por favor");
        mTilemap = new TileManager("Content/TiledProject/TestRoom.tmx");
        mFont =(FontObject)ObjectManager.GetObjectManager().AddEntity(new FontObject("Content/Fonts/ZeldaFont.png", "THE LEGEND OF ANDONI", 56));
        mFont.SetPosition(new Vector2D<>(100.f, 100.f));
        mFont.SetScale(new Vector2D<>(32.f, 32.f));
        mNpc1 = (Npc)ObjectManager.GetObjectManager().AddEntity(new Npc("Aelarion", new Sprite("Content/Animations/NPC1.png"), new Vector2D<Float>(1415.f, 725.f), dialogueArrayList, new Vector2D<Float>(50.f, 62.f)) );
        mNpc2 = (Npc)ObjectManager.GetObjectManager().AddEntity(new Npc("Juan", new Sprite("Content/Animations/NPC1.png"), new Vector2D<Float>(1200f, 725.f), dialogueArrayList2, new Vector2D<Float>(50.f, 62.f)) );
        mPlayer = (Player)ObjectManager.GetObjectManager().AddEntity(new Player(new Spritesheet("Content/Animations/Link/Link.png"), new Vector2D<Float>(700.f, 400.f), new Vector2D<Float>(100.f, 100.f)));
        mPos = new Vector2D<Float>(300.f, 600.f);
        mNpcArrayList.add(mNpc1);
        mNpcArrayList.add(mNpc2);
        Spritesheet esprite = new Spritesheet("Content/Animations/gknight.png",16,28);
        ArrayList<Enemy> mEnemies = new ArrayList<Enemy>();
        mEnemy = (Enemy)ObjectManager.GetObjectManager().AddEntity(new Enemy(esprite, new Vector2D<Float>(450.f, 300.f), new Vector2D<Float>(50.f, 100.f)));
    
        InputManager.SubscribePressed(KeyEvent.VK_P, new InputFunction() {
            @Override
            public void Execute() {
                mPause = !mPause;
             }
        });
    }

    // ------------------------------------------------------------------------
    /*! Update
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    @Override
    public void Update() {
        if(gameState == playState){
            ObjectManager.GetObjectManager().Update();
           mEnemy.Update();
           for(int i=0; i < mNpcArrayList.size();i++){
               mNpcArrayList.get(i).Update(mPlayer.GetPosition());
            //ObjectManager.GetObjectManager().Update();
            }
            //System.out.println(ObjectManager.GetObjectManager().getmAliveEntities().size());
        } 
        if(gameState == pauseState){
            //Nothing
        }
    }

    // ------------------------------------------------------------------------
    /*! Input
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
//    @Override
//    public void Input(InputManager inputmanager) {
//    }

    // ------------------------------------------------------------------------
    /*! Render
    *
    *   Renders onto the screen
    */ //----------------------------------------------------------------------
//    @Override
//    public void Render(Graphics2D g) {
//        GraphicsPipeline.GetGraphicsPipeline().Render(g);
//        if(!mPause) {
//            ObjectManager.GetObjectManager().Update();
//        }
//   }
    public static void setGameState(int state){
        gameState = state;
    }

    public static int getPlayState() {
        return playState;
    }

    public static int getPauseState() {
        return pauseState;
    }

    public static int getGameState() {
        return gameState;
    }

    public static ArrayList<Npc> getNpcArrayList() {
        return mNpcArrayList;
    }

    public String saltoDeLinea(String texto){
        String nuevoTexto = texto;
        return nuevoTexto;
    }
    
}