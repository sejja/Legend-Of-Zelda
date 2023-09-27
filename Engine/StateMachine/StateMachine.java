//
//	StateMachine.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 15/09/2023
//	Copyright Deusto © 2023. All Rights reserved
// 

package Engine.StateMachine;

import java.awt.Graphics2D;
import java.util.ArrayList;

import Gameplay.States.PlayState;

public class StateMachine {
    private ArrayList<State> mStates;

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Adds some basic states
    */ //----------------------------------------------------------------------
    public StateMachine() {
        mStates = new ArrayList<State>();
        mStates.add(new PlayState());
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
    public void Remove(State state) {
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
}
