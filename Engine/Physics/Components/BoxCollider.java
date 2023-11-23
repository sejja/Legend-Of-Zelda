//
//	BoxCollider.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 19/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.Physics.Components;

import Engine.ECSystem.Types.Actor;
import Engine.ECSystem.Types.Component;
import Engine.Math.Vector2D;
import Engine.Physics.AABB;
import Engine.Physics.ColliderManager;
/* Hitbox Implementation
 *      -> Add a hitbox properties in the Actor
 *      -> Use BoxCollider(Actor parent,Vector2D<Float> scale, boolean itCollides) 
 *          -> set itCollides true in case of it has Collision
 *      -> Add hitBox.Update() at the end of the Actor.Update()
 *      -> Add pseudoPositionUpdate() at the end of the Actor.Update()
 * 
 */
public class BoxCollider extends Component {
    private AABB mBounds;
    private boolean mRigid;

    // ------------------------------------------------------------------------
    /*! Conversion Constructor
    *
    *   Constructs a Box Collider from a parent actor
    */ //----------------------------------------------------------------------
    public BoxCollider(final Actor parent) {
        super(parent);
        SetUp(false, GetParent().GetScale());
    }

    // ------------------------------------------------------------------------
    /*! Conversion Constructor
    *
    *   Constructs a Box Collider from a parent actor and a scale
    */ //----------------------------------------------------------------------
    public BoxCollider(final Actor parent, final Vector2D<Float> scale) {
        super(parent);
        SetUp(false, scale);
    }

    // ------------------------------------------------------------------------
    /*! Conversion Constructor
    *
    *   Constructs a Box Collider from a parent actor and a scale, along with collision information
    */ //----------------------------------------------------------------------
    public BoxCollider(final Actor parent, final Vector2D<Float> scale, 
        final boolean hasCollision){ //This construct will bild a Hitbox
        super(parent);
        SetUp(hasCollision, scale);
    }

    // ------------------------------------------------------------------------
    /*! Set Up
    *
    *   Builds the Box Collider and adds it to the collider manager
    */ //----------------------------------------------------------------------
    private void SetUp(final boolean rigid, final Vector2D<Float> scale) {
        mBounds = new AABB(GetParent().GetPosition(), scale);
        mRigid = rigid;
        ColliderManager.GetColliderManager().addCollider(this, mRigid);
    }

    // ------------------------------------------------------------------------
    /*! Set Scale
    *
    *  Sets the scale of the box collider
    */ //----------------------------------------------------------------------
    public void SetScale(final Vector2D <Float> scale){
        mBounds = new AABB(new Vector2D<Float>(GetParent().getPseudoPosition().x
            -(scale.x/2), GetParent().getPseudoPosition().y-(scale.y/2)), scale);
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
        mBounds.SetPosition(GetParent().getPseudoPosition());

        //If the box collider is rigid
        if(mRigid)
            mBounds.SetPosition(new Vector2D<Float>(super.GetParent().
                getPseudoPosition().x-(mBounds.GetWidth()/2), 
                super.GetParent().getPseudoPosition().y-(mBounds.GetHeight()/2)));
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

    // ------------------------------------------------------------------------
    /*! Set Position
    *
    *   Sets the position of the box collider
    */ //----------------------------------------------------------------------
    public void SetPosition(final Vector2D<Float> position){
        mBounds.SetPosition(position);
    }
}
