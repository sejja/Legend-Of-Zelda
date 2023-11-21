//
//	Util.java
//	Legend Of Zeldad
//
//	Created by Diego Revilla on 21/11/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.Math;

public class Util {
    // ------------------------------------------------------------------------
    /*! Linear Interpolate
    *
    *   Interpolate between an "a" and "b" values within an alpha value
    */ //----------------------------------------------------------------------
    static public float LinearInterpolate(float a, float b, float alpha) {
        return a + alpha * (b - a);
    }
}
