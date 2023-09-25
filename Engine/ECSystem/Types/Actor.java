//
//	Entity.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 18/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.ECSystem.Types;

import java.util.ArrayList;

import Engine.Input.InputManager;
import Engine.Math.Vector2D;

public abstract class Actor extends Entity {
    private ArrayList<Component> mComponents;

    // ------------------------------------------------------------------------
    /*! Add Component
    *
    *   Adds a Component to the actor
    */ //----------------------------------------------------------------------
    public <T extends Component> T AddComponent(T c) {
        c.Init();
        mComponents.add(c);
        return (T) c;
    }

    // ------------------------------------------------------------------------
    /*! Remove Component
    *
    *   Removes a Component from the Actor
    */ //----------------------------------------------------------------------
    public void RemoveComponent(Component comp) {
        mComponents.remove(comp);
        comp.ShutDown();
    }

    // ------------------------------------------------------------------------
    /*! Custom Constructor
    *
    *   Checks the input, and sets directions
    */ //----------------------------------------------------------------------
    public Actor(Vector2D<Float> position) {
        super();
        SetPosition(position);
        mComponents = new ArrayList<>();
    }

    // ------------------------------------------------------------------------
    /*! Custom Constructor
    *
    *   Checks the input, and sets directions with a name
    */ //----------------------------------------------------------------------
    public Actor(String name, Vector2D<Float> position) {
        super(name);
        SetPosition(position);
    }

    // ------------------------------------------------------------------------
    /*! Update
    *
    *   Updates the Animation
    */ //----------------------------------------------------------------------
    public void Update() {
        //Update every component
        for(Component x : mComponents)
            x.Update();
    }

    // ------------------------------------------------------------------------
    /*! Set Size
    *
    *   Sets the Size of the Actor
    */ //----------------------------------------------------------------------
    public void SetScale(Vector2D<Float> vec) {
        super.SetScale(vec);
    }
}
