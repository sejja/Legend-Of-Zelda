//
//	PlayState.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 15/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.StateMachine.States;

import java.awt.Graphics2D;
import java.util.ArrayList;

import Engine.ECSystem.ObjectManager;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Objects.FontObject;
import Engine.Graphics.Tile.TileManager;
import Engine.Input.InputManager;
import Engine.Math.Vector2D;
import Engine.StateMachine.State;
import Engine.StateMachine.StateMachine;
import Gameplay.Player;
import Gameplay.Enemies.*;

public class PlayState extends State {

    private FontObject mFont;
    private Player mPlayer;
    private Enemy mEnemy;
    private Enemy mEnemy2;
    private Enemy mEnemy3;
    private Vector2D<Float> mPos;
    private TileManager mTilemap;

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Just assigns the statemachine child
    */ //----------------------------------------------------------------------
    public PlayState(StateMachine superm) {
        super(superm);
        mTilemap = new TileManager("Content/TiledProject/StressTest.tmx");
        mFont =(FontObject)ObjectManager.GetObjectManager().AddEntity(new FontObject("Content/Fonts/ZeldaFont.png", "THE LEGEND OF ANDONI"));
        mFont.SetPosition(new Vector2D<>(100.f, 100.f));
        mFont.SetScale(new Vector2D<>(32.f, 32.f));
        mPlayer = (Player)ObjectManager.GetObjectManager().AddEntity(new Player(new Spritesheet("Content/Animations/Link.png"), new Vector2D<Float>(300.f, 300.f), new Vector2D<Float>(100.f, 100.f)));
        mPos = new Vector2D<Float>(300.f, 600.f);
        Spritesheet esprite = new Spritesheet("Content/Animations/gknight.png",16,28);
        mEnemy = (Enemy)ObjectManager.GetObjectManager().AddEntity(new Enemy(esprite, new Vector2D<Float>(300.f, 300.f), new Vector2D<Float>(50.f, 100.f)));
        mEnemy2 = (Enemy)ObjectManager.GetObjectManager().AddEntity(new Enemy(esprite, new Vector2D<Float>(600.f, 600.f), new Vector2D<Float>(50.f, 100.f)));
        mEnemy3 = (Enemy)ObjectManager.GetObjectManager().AddEntity(new Enemy(esprite, new Vector2D<Float>(900.f, 900.f), new Vector2D<Float>(50.f, 100.f)));
    }

    // ------------------------------------------------------------------------
    /*! Update
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    @Override
    public void Update() {
        ObjectManager.GetObjectManager().Update();

        //La cosa sería tener una función asi: ObjectManager.GetEntity("Player"), para luego hacer player.GetPosition()

        //LA COSA ES QUE CUANDO HAYA MAS ENEMIGOS SERIA ALGO EN PLAN 
        //for(int i = 0; i < mEnemies.size(); i++){
        //  mEnemies.get(i).Update(mPlayer.GetPosition());
    }

    // ------------------------------------------------------------------------
    /*! Input
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    @Override
    public void Input(InputManager inputmanager) {
    }

    // ------------------------------------------------------------------------
    /*! Render
    *
    *   Renders onto the screen
    */ //----------------------------------------------------------------------
    @Override
    public void Render(Graphics2D g) {
        GraphicsPipeline.GetGraphicsPipeline().Render(g);
    }
}