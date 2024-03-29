//
//	ECSBase.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 19/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.ECSystem.Types;

import Engine.ECSystem.ObjectManager;
import Engine.Math.Transform;
import Engine.Math.Vector2D;

public abstract class Entity implements Base,ClassClasifier{
    private String mName;
    private Transform mTransform;
    
    public abstract void Update();

    // ------------------------------------------------------------------------
    /*! Get Position
    *
    *   Returns the Position of the actor
    */ //----------------------------------------------------------------------
    public Vector2D<Float> GetPosition() {
        return mTransform.GetPosition();
    }

    // ------------------------------------------------------------------------
    /*! Set Position
    *
    *   Sets the position of the actor
    */ //----------------------------------------------------------------------
    public void SetPosition(Vector2D<Float> p) {
        mTransform.SetPosition(p);
    }

    // ------------------------------------------------------------------------
    /*! Get Position
    *
    *   Returns the Position of the actor
    */ //----------------------------------------------------------------------
    public Vector2D<Float> GetScale() {
        return mTransform.GetScale();
    }

    // ------------------------------------------------------------------------
    /*! Set Position
    *
    *   Sets the position of the actor
    */ //----------------------------------------------------------------------
    public void SetScale(Vector2D<Float> p) {
        mTransform.SetScale(p);
    }
    
    // ------------------------------------------------------------------------
    /*! Default Constructor
    *
    *   Sets the name to "No Name"
    */ //----------------------------------------------------------------------
    protected Entity() {
        mName = "No name";
        mTransform = new Transform();
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

    /* despawn
     *      Remove this entity
     */
    protected void despawn(){ObjectManager.GetObjectManager().RemoveEntity(this);}
    protected void spawn(){ObjectManager.GetObjectManager().AddEntity(this);}
    public Class GetSuperClass(){return Entity.class;}
}
