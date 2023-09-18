//
//	State.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 15/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.StateMachine;

import java.awt.Graphics;
import java.awt.Graphics2D;

import Engine.Input.InputManager;

public abstract class State {
    private StateMachine mSuper;

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    public State(StateMachine superm) {
        mSuper = superm;
    }

    public abstract void Update();
    public abstract void Input(InputManager inputmanager);
    public abstract void Render(Graphics2D g);
}