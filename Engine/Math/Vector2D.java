//
//	Vector2D.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 14¡5/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.Math;

public class Vector2D {
    public float x;
    public float y;

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Initializes the values to 0
    */ //----------------------------------------------------------------------
    public Vector2D() {
        x = y = 0;
    }

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Initializes the values to the given values
    */ //----------------------------------------------------------------------
    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // ------------------------------------------------------------------------
    /*! Copy Constructor
    *
    *   Copies another vector
    */ //----------------------------------------------------------------------
    public Vector2D(Vector2D rhs) {
         new Vector2D(rhs.x, rhs.y);
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
