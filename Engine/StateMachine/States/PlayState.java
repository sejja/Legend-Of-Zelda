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
    private Vector2D mPos;
    private TileManager mTilemap;

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Just assigns the statemachine child
    */ //----------------------------------------------------------------------
    public PlayState(StateMachine superm) {
        super(superm);
        //mTilemap = new TileManager("TiledProject/Test.xml");
        mFont = new Font("Content/Fonts/ZeldaFont.png", 16, 16);
        mPlayer = new Player(new Spritesheet("Content/Animations/Link.png"), new Vector2D(300, 300), new Vector2D(100, 100));
        mPos = new Vector2D(300, 600);
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
        mPlayer.Input(inputmanager);
    }

    // ------------------------------------------------------------------------
    /*! Render
    *
    *   Renders onto the screen
    */ //----------------------------------------------------------------------
    @Override
    public void Render(Graphics2D g) {
        GraphicsPipeline.GetGraphicsPipeline().Render(g);
        mFont.Render(g, "THE LEGEND OF ANDONI", new Vector2D(100, 100), 32, 32, 56, 0);
    }
}
