//
//	StateMachine.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 15/09/2023
//	Copyright Deusto © 2023. All Rights reserved
// 

package Engine.StateMachine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import Engine.Input.InputManager;
import Engine.Math.Vector2D;
import Engine.StateMachine.States.PlayState;
import Engine.Window.PresentBuffer;

public class StateMachine {
    ArrayList<State> mStates;
    public static Vector2D mCoordinates; 

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Adds some basic states
    */ //----------------------------------------------------------------------
    public StateMachine() {
        mCoordinates = new Vector2D(PresentBuffer.mWidth, PresentBuffer.mHeight);

        mStates = new ArrayList<State>();
        mStates.add(new PlayState(this));
    }

    // ------------------------------------------------------------------------
    /*! Update
    *
    *   Calls the Update method on every sub-state
    */ //----------------------------------------------------------------------
    public void Update() {
        //Iterate through every state
        for(State x : mStates)
            x.Update();
    }

    // ------------------------------------------------------------------------
    /*! Pop
    *
    *   Removes a ceratain state
    */ //----------------------------------------------------------------------
    public void Pop(State state) {
        mStates.remove(state);
    }

    // ------------------------------------------------------------------------
    /*! Add
    *
    *   Adds one state
    */ //----------------------------------------------------------------------
    public void Add(State state) {
        mStates.add(state);
    }

    // ------------------------------------------------------------------------
    /*! Input
    *
    *   Calls the Input method on every sub-state
    */ //----------------------------------------------------------------------
    public void Input(InputManager inputmanager) {
        //Iterate through every state
        for(State x: mStates)
            x.Input(inputmanager);
    }

    // ------------------------------------------------------------------------
    /*! Render
    *
    *   Calls the Render method on every sub-state
    */ //----------------------------------------------------------------------
    public void Render(Graphics2D grphics) {
        //Iterate through every state
        for(State x : mStates)
            x.Render(grphics);
    }
}
