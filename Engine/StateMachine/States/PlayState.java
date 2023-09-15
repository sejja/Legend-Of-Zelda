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

import Engine.Input.InputManager;
import Engine.StateMachine.State;
import Engine.StateMachine.StateMachine;

public class PlayState extends State {
    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Just assigns the statemachine child
    */ //----------------------------------------------------------------------
    public PlayState(StateMachine superm) {
        super(superm);
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
            System.out.println("Hello");
        
    }

    // ------------------------------------------------------------------------
    /*! Render
    *
    *   Renders onto the screen
    */ //----------------------------------------------------------------------
    @Override
    public void Render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(10, 10, 100, 100);
    }
}
