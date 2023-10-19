//
//	Transform.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 19/09/2023
//	Copyright Deusto © 2023. All Rights reserved
// 

package Engine.Math;

public class Transform {
    public Transform() {

    }

    public Transform(Vector2D<Float> pos, Vector2D<Float> sca) {
        mPosition = pos;
        mScale = sca;
    }

    public Vector2D<Float> mPosition;
    public Vector2D<Float> mScale;
}
