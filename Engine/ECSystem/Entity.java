//
//	ECSBase.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 19/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.ECSystem;

import Engine.Math.Transform;
import Engine.Math.Vector2D;

public abstract class Entity implements Base {
    private String mName;
    private Transform mTransform;
    
    public abstract void Update();

    // ------------------------------------------------------------------------
    /*! Get Position
    *
    *   Returns the Position of the actor
    */ //----------------------------------------------------------------------
    public Vector2D<Float> GetPosition() {
        return mTransform.mPosition;
    }

    // ------------------------------------------------------------------------
    /*! Set Position
    *
    *   Sets the position of the actor
    */ //----------------------------------------------------------------------
    public void SetPosition(Vector2D<Float> p) {
        mTransform.mPosition = p;
    }

    // ------------------------------------------------------------------------
    /*! Get Position
    *
    *   Returns the Position of the actor
    */ //----------------------------------------------------------------------
    public Vector2D<Float> GetScale() {
        return mTransform.mScale;
    }

    // ------------------------------------------------------------------------
    /*! Set Position
    *
    *   Sets the position of the actor
    */ //----------------------------------------------------------------------
    public void SetScale(Vector2D<Float> p) {
        mTransform.mScale = p;
    }
    
    // ------------------------------------------------------------------------
    /*! Default Constructor
    *
    *   Sets the name to "No Name"
    */ //----------------------------------------------------------------------
    protected Entity() {
        mName = "No name";
        mTransform = new Transform();
        mTransform.mScale = new Vector2D<Float>(1.f, 1.f);
    }

    // ------------------------------------------------------------------------
    /*! Conversion Constructor
    *
    *   Constructs an entity with a name, but makes sure the name is not empty
    */ //----------------------------------------------------------------------
    protected Entity(String name) {
        assert(name != "");
        mName = name;
    }

    // ------------------------------------------------------------------------
    /*! Get Name
    *
    *   Returns the name of the entity
    */ //----------------------------------------------------------------------
    public String GetName() {
        return mName;
    }

    // ------------------------------------------------------------------------
    /*! Set Name
    *
    *   Sets the name of the Entity
    */ //----------------------------------------------------------------------
    public void SetName(String name) {
        assert(name != "");
        mName = name;
    }
}
