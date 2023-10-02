//
//	PlayState.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 15/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Gameplay.States;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Engine.ECSystem.ObjectManager;
import Engine.Graphics.GraphicsPipeline;
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

public class PlayState extends State {
    private FontObject mFont;
    private Player mPlayer;
    private Enemy mEnemy;
    private Enemy mEnemy2;
    private Enemy mEnemy3;
    private Vector2D<Float> mPos;
    private TileManager mTilemap;
    private boolean mPause = false;

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Just assigns the statemachine child
    */ //----------------------------------------------------------------------
    public PlayState() {
        mTilemap = new TileManager("Content/TiledProject/TestRoom.tmx");
        mFont =(FontObject)ObjectManager.GetObjectManager().AddEntity(new FontObject("Content/Fonts/ZeldaFont.png", "THE LEGEND OF ZELDA"));
        mFont.SetPosition(new Vector2D<>(100.f, 100.f));
        mFont.SetScale(new Vector2D<>(32.f, 32.f));
        mPlayer = (Player)ObjectManager.GetObjectManager().AddEntity(new Player(new Spritesheet("Content/Animations/Link/Link.png"), new Vector2D<Float>(700.f, 400.f), new Vector2D<Float>(100.f, 100.f)));
        mPos = new Vector2D<Float>(300.f, 600.f);
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
        if(!mPause) {
            ObjectManager.GetObjectManager().Update();
        }
    }
}