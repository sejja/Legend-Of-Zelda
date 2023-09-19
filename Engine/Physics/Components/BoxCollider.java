package Engine.Physics.Components;

import Engine.ECSystem.Component;
import Engine.ECSystem.Entity;
import Engine.Physics.AABB;

public class BoxCollider extends Component {
    private AABB mBounds;

    protected BoxCollider(Entity parent) {
        super(parent);
        mBounds = new AABB(parent.GetPosition(), 1, 1);
    }

    @Override
    public void Init() {
        
    }

    @Override
    public void Update() {
        mBounds.SetHeight(GetParent().GetScale().x);
        mBounds.SetWidth(GetParent().GetScale().y);
    }

    @Override
    public void ShutDown() {
        
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
