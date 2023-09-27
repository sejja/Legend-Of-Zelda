//
//	Component.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 19/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.ECSystem.Types;

public abstract class Component implements Base {
    private Entity mParent;

    // ------------------------------------------------------------------------
    /*! Conversion Constructor
    *
    *   Constructs a Component with a parent
    */ //----------------------------------------------------------------------
    protected Component(Entity parent) {
        mParent = parent;
    }

    // ------------------------------------------------------------------------
    /*! Returns the parent
    *
    *   Gets the Parent of the Component (an actor)
    */ //----------------------------------------------------------------------
    public Entity GetParent() {
        return mParent;
    }
    public abstract void Init();
    public abstract void Update();
    public abstract void ShutDown();
}
