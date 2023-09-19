//
//	Entity.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 18/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.ECSystem;

import java.util.ArrayList;

import Engine.Input.InputManager;
import Engine.Math.Vector2D;
import Engine.Physics.AABB;

public abstract class Actor extends Entity {
    private ArrayList<Component> mComponents;
    protected AABB mBounds;

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
    public Actor(Vector2D position) {
        super();
        SetPosition(position);
        mComponents = new ArrayList<>();
        mBounds = new AABB(GetPosition(), 1, 1);
    }

    // ------------------------------------------------------------------------
    /*! Custom Constructor
    *
    *   Checks the input, and sets directions with a name
    */ //----------------------------------------------------------------------
    public Actor(String name, Vector2D position) {
        super(name);
        SetPosition(position);
        mBounds = new AABB(GetPosition(), 1, 1);
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
    /*! Input
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    public void Input(InputManager input) {}

    // ------------------------------------------------------------------------
    /*! Set Size
    *
    *   Sets the Size of the Actor
    */ //----------------------------------------------------------------------
    public void SetScale(Vector2D vec) {
        super.SetScale(vec);
        mBounds.SetHeight(vec.x);
        mBounds.SetWidth(vec.y);
    }

    // ------------------------------------------------------------------------
    /*! Get Bounds
    *
    *   Returns the bounding box with the collisions
    */ //----------------------------------------------------------------------
    public AABB GetBounds() {
        return mBounds;
    }
}
