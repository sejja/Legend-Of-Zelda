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
import Engine.Graphics.Sprite;
import Engine.Input.InputManager;
import Engine.Math.Vector2D;
import Engine.StateMachine.State;
import Engine.StateMachine.StateMachine;

public class PlayState extends State {

    private Font mFont;
    private Sprite mImage;
    private Vector2D mPos;

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Just assigns the statemachine child
    */ //----------------------------------------------------------------------
    public PlayState(StateMachine superm) {
        super(superm);
        mFont = new Font("Content/Fonts/ZeldaFont.png", 16, 16);
        mImage = new Sprite("Content/Tiles/Overworld.png");
        mPos = new Vector2D(300, 600);
    }

    // ------------------------------------------------------------------------
    /*! Update
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    @Override
    public void Update() {
        
    }

    // ------------------------------------------------------------------------
    /*! Input
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    @Override
    public void Input(InputManager inputmanager) {
        if(inputmanager.down.down)
            mPos.y -= 1;
        
        if(inputmanager.up.down)
            mPos.y += 1;

        if(inputmanager.left.down)
            mPos.x -= 1;

        if(inputmanager.right.down)
            mPos.x += 1;
    }

    // ------------------------------------------------------------------------
    /*! Render
    *
    *   Renders onto the screen
    */ //----------------------------------------------------------------------
    @Override
    public void Render(Graphics2D g) {
        Sprite.DrawArray((Graphics2D) g, mFont, "THE LEGEND OF ANDONI", new Vector2D(100, 100), 32, 32, 56, 0);
        Sprite.DrawArray((Graphics2D) g, mFont, "Un dialogo de Zelda", mPos, 32, 32, 16, 0); 
    }
}
