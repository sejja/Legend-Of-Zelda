//
//	Transform.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 19/09/2023
//	Copyright Deusto © 2023. All Rights reserved
// 

package Engine.Math;

public class Transform {
    private Vector2D<Float> mPosition;
    private Vector2D<Float> mScale;

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Constructs an Sprite from the filename
    */ //----------------------------------------------------------------------
    public Transform() {
        mPosition = new Vector2D<>();
        mScale = new Vector2D<>();
    }

    // ------------------------------------------------------------------------
    /*! Custom Constructor
    *
    *   Constructs a Position and a scale from two given values
    */ //----------------------------------------------------------------------
    public Transform(final Vector2D<Float> pos, final Vector2D<Float> sca) {
        mPosition = pos;
        mScale = sca;
    }

    // ------------------------------------------------------------------------
    /*! Get Position
    *
    *   Returns the Position of a Transform
    */ //----------------------------------------------------------------------
    public Vector2D<Float> GetPosition() {
        return mPosition;
    }

    // ------------------------------------------------------------------------
    /*! Get Scale
    *
    *   Returns the Scale of a Transform
    */ //----------------------------------------------------------------------
    public Vector2D<Float> GetScale() {
        return mScale;
    }
 
    // ------------------------------------------------------------------------
    /*! Set Position
    *
    *   Sets your position
    */ //----------------------------------------------------------------------
    public void SetPosition(final Vector2D<Float> pos) {
        mPosition = pos;
    }

    // ------------------------------------------------------------------------
    /*! Set Scale
    *
    *   Sets your scale
    */ //----------------------------------------------------------------------
    public void SetScale(final Vector2D<Float> sca) {
        mScale = sca;
    }
}
