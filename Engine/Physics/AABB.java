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
    private Vector2D mPosition;
    private float mWidth;
    private float mHeight;
    private float mRadius;
    private int mSize;

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Constructs an AABB with a position, a width and a height
    */ //----------------------------------------------------------------------
    public AABB(Vector2D pos, int w, int h) {
        mPosition = pos;
        mWidth = w;
        mHeight = h;
        mSize = Math.max(w, h);
    }

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Constructs an AABB with a position and a radius
    */ //----------------------------------------------------------------------
    public AABB(Vector2D pos, int r) {
        mPosition = pos;
        mSize = r;
        mRadius = r;
    }

    // ------------------------------------------------------------------------
    /*! Get Position
    *
    *   Returns the position
    */ //----------------------------------------------------------------------
    public Vector2D GetPosition() {
        return mPosition;
    }

    // ------------------------------------------------------------------------
    /*! Get Radius
    *
    *   Returns the radius
    */ //----------------------------------------------------------------------
    public float GetRadius() {
        return mRadius;
    }

    // ------------------------------------------------------------------------
    /*! Get Height
    *
    *   Returns the height
    */ //----------------------------------------------------------------------
    public float GetHeight() {
        return mHeight;
    }

    // ------------------------------------------------------------------------
    /*! Get Width
    *
    *   Returns the width
    */ //----------------------------------------------------------------------
    public float GetWidth() {
        return mWidth;
    }

    // ------------------------------------------------------------------------
    /*! Set Box
    *
    *   Sets the box, as with a position, a width and a height
    */ //----------------------------------------------------------------------
    public void SetBox(Vector2D pos, int w, int h) {
        mPosition = pos;
        mWidth = w;
        mHeight = h;
    }

    // ------------------------------------------------------------------------
    /*! Set Circle
    *
    *   CCreates a circle, with a position and a radius
    */ //----------------------------------------------------------------------
    public void SetCircle(Vector2D pos, int r) {
        mPosition = pos;
        mRadius = r;
        mSize = r;
    }

    // ------------------------------------------------------------------------
    /*! Set WIdth
    *
    *   Sets the Width
    */ //----------------------------------------------------------------------
    public void SetWidth(float f) {
        mWidth = f;
    }

    // ------------------------------------------------------------------------
    /*! Set Height
    *
    *   Sets the Height
    */ //----------------------------------------------------------------------
    public void SetHeight(float f) {
        mHeight = f;
    }

    // ------------------------------------------------------------------------
    /*! Collides
    *
    *   Returns wether two boxes collide or not
    */ //----------------------------------------------------------------------
    public boolean Collides(AABB box) {
        float ax = ((mPosition.x) + (mWidth / 2));
        float ay = ((mPosition.y) + (mHeight / 2));
        float bx = ((box.mPosition.x) + (mWidth / 2));
        float by = ((box.mPosition.y) + (mHeight / 2));

        //If they overlap on the X axis
        if(Math.abs(ax - bx) < (mWidth / 2) + (box.mWidth / 2))

            //If they overlap on the Y Axis
            if(Math.abs(ay - by) < (mHeight / 2) + (box.mHeight / 2))
                return true;

        return false;
    }

    // ------------------------------------------------------------------------
    /*! Collides a Circle with a Box
    *
    *   CPretty much the name. A collision test
    */ //----------------------------------------------------------------------
    public boolean CollidesCircleBox(AABB box) {
        float cx = (float)(mPosition.x + mRadius / Math.sqrt(2) - mSize / Math.sqrt(2));
        float cy = (float)(mPosition.y + mRadius / Math.sqrt(2) - mSize / Math.sqrt(2));
        float xDelta = cx - Math.max(box.mPosition.x + box.GetWidth() / 2, Math.min(cx, box.mPosition.x));
        float yDelta = cy - Math.max(box.mPosition.y + box.GetHeight() / 2, Math.min(cy, box.mPosition.y));
   
        //If the distances between each, squared, is less than the radious, squared, we are overlapping
        if((xDelta * xDelta + yDelta * yDelta)  < (mRadius / Math.sqrt(2)) * (mRadius / Math.sqrt(2))) {
            return true;
        }

        return false;
    }
}