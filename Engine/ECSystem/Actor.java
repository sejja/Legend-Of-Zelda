//
//	Entity.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 18/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.ECSystem;

import java.awt.Graphics2D;

import Engine.Graphics.Animation;
import Engine.Graphics.Sprite;
import Engine.Input.InputManager;
import Engine.Math.Vector2D;
import Engine.Physics.AABB;

public abstract class Actor extends Entity {
    protected Sprite mSprite;
    protected Vector2D mPosition;
    protected int mSize;
    protected AABB mBounds;
    protected Animation mAnimation;

    // ------------------------------------------------------------------------
    /*! Conversion Constructor
    *
    *   Checks the input, and sets directions
    */ //----------------------------------------------------------------------
    public Actor(Sprite sprite, Vector2D position) {
        mSprite = sprite;
        mPosition = position;
        mSize = 1;
        mBounds = new AABB(mPosition, 1, 1);
        mAnimation = new Animation();
    }

    // ------------------------------------------------------------------------
    /*! Update
    *
    *   Updates the Animation
    */ //----------------------------------------------------------------------
    public void Update() {
        Animate();
        mAnimation.update();
    }

    public abstract void Animate();

    // ------------------------------------------------------------------------
    /*! Render
    *
    *   Renderiza la imagen de la animación
    */ //----------------------------------------------------------------------
    public void Render(Graphics2D g) {
        g.drawImage(mAnimation.GetCurrentFrame(), (int)mPosition.x, (int)mPosition.y, mSize, mSize, null);
    }

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
    public void SetSize(int i) {
        mSize = i;
        mBounds.SetHeight(i);
        mBounds.SetWidth(i);
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
