//
//	BoxCollider.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 19/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.Physics.Components;

import Engine.ECSystem.Actor;
import Engine.ECSystem.Component;
import Engine.Physics.AABB;

public class BoxCollider extends Component {
    private AABB mBounds;

    // ------------------------------------------------------------------------
    /*! Conversion Constructor
    *
    *   Constructs a Box Collider from a parent actor
    */ //----------------------------------------------------------------------
    public BoxCollider(Actor parent) {
        super(parent);
        mBounds = new AABB(parent.GetPosition(), 1, 1);
    }

    // ------------------------------------------------------------------------
    /*! Init
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    @Override
    public void Init() {
        
    }

    // ------------------------------------------------------------------------
    /*! Update
    *
    *   Updates the bounds of the AABB
    */ //----------------------------------------------------------------------
    @Override
    public void Update() {
        mBounds.SetHeight(GetParent().GetScale().x);
        mBounds.SetWidth(GetParent().GetScale().y);
    }

    // ------------------------------------------------------------------------
    /*! Shutdown
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    @Override
    public void ShutDown() {
        
    }

    // ------------------------------------------------------------------------
    /*! Get Bounds
    *
    *   Returns the Axis Aligned Bouding Box of the Collider
    */ //----------------------------------------------------------------------
    public AABB GetBounds() {
        return mBounds;
    }
}
