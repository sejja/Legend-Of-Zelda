//
//	PlayState.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 15/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.StateMachine.States;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import Engine.Graphics.Font;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Tile.TileManager;
import Engine.Input.InputManager;
import Engine.Math.Vector2D;
import Engine.StateMachine.State;
import Engine.StateMachine.StateMachine;
import Gameplay.Player;

public class PlayState extends State {

    private Font mFont;
    private Player mPlayer;
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
        mFont = new Font("Content/Fonts/ZeldaFont.png", 16, 16);
        mPlayer = new Player(new Spritesheet("Content/Animations/Link.png"), new Vector2D<Float>(300.f, 300.f), new Vector2D<Float>(100.f, 100.f));
        mPos = new Vector2D<Float>(300.f, 600.f);
    }

    // ------------------------------------------------------------------------
    /*! Update
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    @Override
    public void Update() {
        mPlayer.Update();
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
        mTilemap.Render(g);
        GraphicsPipeline.GetGraphicsPipeline().Render(g);
        mFont.Render(g, "THE LEGEND OF ANDONI", new Vector2D<Float>(100.f, 100.f), 32, 32, 56, 0);
    }
}
