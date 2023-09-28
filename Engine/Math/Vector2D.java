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
    public Vector2D(T x, T y) {
        this.x = x;
        this.y = y;
    }

    // ------------------------------------------------------------------------
    /*! Copy Constructor
    *
    *   Copies another vector
    */ //----------------------------------------------------------------------
    public Vector2D(Vector2D<T> rhs) {
         new Vector2D<T>(rhs.x, rhs.y);
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

    public Float getModuleDistance(Vector2D<Float> a){
        Float xf = Math.abs((Float)this.x - a.x);
        Float yf = Math.abs((Float)this.y - a.y);
        return (Float)(float) Math.sqrt(Math.pow(xf,2) + Math.pow(yf, 2));
    }
}
