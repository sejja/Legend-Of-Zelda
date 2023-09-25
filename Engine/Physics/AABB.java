//
//	InputManager.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 15/09/2023
//	Copyright Deusto © 2023. All Rights reserved
// 

package Engine.Physics;

import Engine.Math.Vector2D;

public class AABB {
    private Vector2D<Float> mPosition;
    private Vector2D<Float> mSize;

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Constructs an AABB with a position, a width and a height
    */ //----------------------------------------------------------------------
    public AABB(Vector2D<Float> pos, Vector2D<Float> size) {
        mPosition = pos;
        mSize = size;
    }

    // ------------------------------------------------------------------------
    /*! Get Position
    *
    *   Returns the position
    */ //----------------------------------------------------------------------
    public Vector2D<Float> GetPosition() {
        return mPosition;
    }

    // ------------------------------------------------------------------------
    /*! Get Height
    *
    *   Returns the height
    */ //----------------------------------------------------------------------
    public float GetHeight() {
        return mSize.y;
    }

    // ------------------------------------------------------------------------
    /*! Get Width
    *
    *   Returns the width
    */ //----------------------------------------------------------------------
    public float GetWidth() {
        return mSize.x;
    }

    // ------------------------------------------------------------------------
    /*! Set Box
    *
    *   Sets the box, as with a position, a width and a height
    */ //----------------------------------------------------------------------
    public void SetBox(Vector2D<Float> pos, Vector2D<Float> size) {
        mPosition = pos;
        mSize = size;
    }

    // ------------------------------------------------------------------------
    /*! Set WIdth
    *
    *   Sets the Width
    */ //----------------------------------------------------------------------
    public void SetWidth(float f) {
        mSize.x = f;
    }

    // ------------------------------------------------------------------------
    /*! Set Height
    *
    *   Sets the Height
    */ //----------------------------------------------------------------------
    public void SetHeight(float f) {
        mSize.y = f;
    }

    // ------------------------------------------------------------------------
    /*! Collides
    *
    *   Returns wether two boxes collide or not
    */ //----------------------------------------------------------------------
    public boolean Collides(AABB box) {
        final float halfxsize = mSize.x / 2;
        final float halfysize = mSize.y / 2;

        //If they overlap on the X axis
        if(Math.abs(((mPosition.x) + halfxsize) - ((box.mPosition.x) + halfxsize)) < halfxsize + (box.mSize.x / 2))

            //If they overlap on the Y Axis
            if(Math.abs(((mPosition.y) + halfysize) - ((box.mPosition.x) + halfysize)) < halfysize + (box.mSize.x / 2))
                return true;

        return false;
    }

    // ------------------------------------------------------------------------
    /*! Collides a Circle with a Box
    *
    *   CPretty much the name. A collision test
    */ //----------------------------------------------------------------------
    public boolean CollidesCircleBox(AABB box) {
        return (Math.pow(mPosition.x - Math.max(box.mPosition.x + box.GetWidth() / 2, Math.min(mPosition.x, box.mPosition.x)), 2) 
            + Math.pow(mPosition.y - Math.max(box.mPosition.y + box.GetHeight() / 2,  Math.min(mPosition.y, box.mPosition.y)), 2))
            < Math.pow(Math.max(mSize.x, mSize.y) / Math.sqrt(2), 2);
    }
}