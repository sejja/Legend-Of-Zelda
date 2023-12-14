//
//	Component.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 19/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.ECSystem.Types;

public abstract class Component implements Base, Comparable {
    private Actor mParent;

    // ------------------------------------------------------------------------
    /*! Conversion Constructor
    *
    *   Constructs a Component with a parent
    */ //----------------------------------------------------------------------
    protected Component(Actor parent) {
        mParent = parent;
    }

    // ------------------------------------------------------------------------
    /*! Returns the parent
    *
    *   Gets the Parent of the Component (an actor)
    */ //----------------------------------------------------------------------
    public Actor GetParent() {
        return mParent;
    }
    
    public abstract void Init();
    public abstract void Update();
    public abstract void ShutDown();
    @Override
    public int compareTo(Object o){
        Actor other = ((Component)o).GetParent();
        return Math.round(mParent.GetPosition().y - other.GetPosition().y);
    }
}