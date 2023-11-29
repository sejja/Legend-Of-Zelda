//
//	Vector2D.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 15/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.Math;

public class Vector2D<T> {
    public T x;
    public T y;

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Initializes the values to 0
    */ //----------------------------------------------------------------------
    public Vector2D() {
    }

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Initializes the values to the given values
    */ //----------------------------------------------------------------------
    public Vector2D(final T x, final T y) {
        this.x = x;
        this.y = y;
    }

    // ------------------------------------------------------------------------
    /*! Set
    *
    *   Sets the vector to two distinct values
    */ //----------------------------------------------------------------------
    public void Set(final T x, final T y) {
        this.x = x;
        this.y = y;
    }

    // ------------------------------------------------------------------------
    /*! Copy Constructor
    *
    *   Copies another vector
    */ //----------------------------------------------------------------------
    public Vector2D(final Vector2D<T> rhs) {
        x = rhs.x;
        y = rhs.y;
    } 

    // ------------------------------------------------------------------------
    /*! To String
    *
    *   Converts the vector to a string to ease debugging
    */ //----------------------------------------------------------------------
    @Override
    public String toString() {
        return x + ", " + y;
    }
}
