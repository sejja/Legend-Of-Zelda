//
//	StateMachine.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 15/09/2023
//	Copyright Deusto © 2023. All Rights reserved
// 

package Engine.StateMachine;

import java.awt.Graphics;
import java.util.ArrayList;

import Engine.Input.InputManager;
import Engine.StateMachine.States.PlayState;

public class StateMachine {
    ArrayList<State> mStates;

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Adds some basic states
    */ //----------------------------------------------------------------------
    public StateMachine() {
        mStates = new ArrayList<State>();
        mStates.add(new PlayState(this));
    }

    // ------------------------------------------------------------------------
    /*! Update
    *
    *   Calls the Update method on every sub-state
    */ //----------------------------------------------------------------------
    public void Update() {
        for(State x : mStates)
            x.Update();
    }

    // ------------------------------------------------------------------------
    /*! Input
    *
    *   Calls the Input method on every sub-state
    */ //----------------------------------------------------------------------
    public void Input(InputManager inputmanager) {
        for(State x: mStates)
            x.Input(inputmanager);
    }

    // ------------------------------------------------------------------------
    /*! Render
    *
    *   Calls the Render method on every sub-state
    */ //----------------------------------------------------------------------
    public void Render(Graphics grphics) {
        for(State x : mStates)
            x.Render(grphics);
    }
}
