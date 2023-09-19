//
//	Entity.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 18/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.ECSystem;

import java.awt.Graphics2D;
import java.util.ArrayList;

import Engine.Graphics.Animation;
import Engine.Graphics.Sprite;
import Engine.Graphics.Components.Renderable;
import Engine.Input.InputManager;
import Engine.Math.Transform;
import Engine.Math.Vector2D;
import Engine.Physics.AABB;

public abstract class Actor extends Entity {
    private ArrayList<Component> mComponents;
    protected AABB mBounds;

    // ------------------------------------------------------------------------
    /*! Custom Constructor
    *
    *   Checks the input, and sets directions
    */ //----------------------------------------------------------------------
    public Actor(Sprite sprite, Vector2D position) {
        super();
        mTransform = new Transform();
        mTransform.mPosition = position;
        mTransform.mScale = new Vector2D(1, 1);
        mBounds = new AABB(mTransform.mPosition, 1, 1);
    }

    // ------------------------------------------------------------------------
    /*! Custom Constructor
    *
    *   Checks the input, and sets directions with a name
    */ //----------------------------------------------------------------------
    public Actor(Sprite sprite, Vector2D position, String name) {
        super(name);
        mTransform.mPosition = position;
        mTransform.mScale = new Vector2D(1, 1);
        mBounds = new AABB(mTransform.mPosition, 1, 1);
    }

    // ------------------------------------------------------------------------
    /*! Update
    *
    *   Updates the Animation
    */ //----------------------------------------------------------------------
    public void Update() {
        Animate();

        for(Component x : mComponents)
            x.Update();
    }

    public abstract void Animate();

    // ------------------------------------------------------------------------
    /*! Input
    *
    *   EMPTY FUNCTION
    */ //----------------------------------------------------------------------
    public void Input(InputManager input) {}

    // ------------------------------------------------------------------------
    /*! Set Sprite
    *
    *   Sets the Sprite that we are going to animate
    */ //----------------------------------------------------------------------
    public void SetAnimationSprite(Sprite sp) {
        mSprite = sp;
    }

    // ------------------------------------------------------------------------
    /*! Set Size
    *
    *   Sets the Size of the Actor
    */ //----------------------------------------------------------------------
    public void SetScale(Vector2D vec) {
        mTransform.mScale = vec;
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
