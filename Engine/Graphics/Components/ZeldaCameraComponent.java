//
//	ZeldaCameraComponent.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 19/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.Graphics.Components;

import Engine.ECSystem.Types.Actor;
import Engine.Graphics.GraphicsPipeline;
import Engine.Math.Vector2D;

public class ZeldaCameraComponent extends CameraComponent {
    private Vector2D<Float> mTopRight;
    private Vector2D<Float> mBottomLeft;

    // ------------------------------------------------------------------------
    /*! Custom Constructor
    *
    *   Constructs a Camera Component from the original parent
    */ //----------------------------------------------------------------------
    public ZeldaCameraComponent(final Actor parent) {
        super(parent);
        mTopRight = new Vector2D<>(0.f, 0.f);
        mBottomLeft = new Vector2D<>(0.f, 0.f);
    }
    
    // ------------------------------------------------------------------------
    /*! Set Bounds
    *
    *   Sets the bounds of the camera
    */ //----------------------------------------------------------------------
    public void SetBounds(final Vector2D<Float> topright, final Vector2D<Float> bottomleft) {
        mTopRight = topright;
        mBottomLeft = bottomleft;
    }

    // ------------------------------------------------------------------------
    /*! Get Top Right Bound
    *
    *   Creates the Top Right Bound of the Camera
    */ //----------------------------------------------------------------------
    public Vector2D<Float> GetTopRightBound() {
        return mTopRight;
    }

    // ------------------------------------------------------------------------
    /*! Get Bottom Left Bound
    *
    *   Creates the Bottom Left Bound of the Camera
    */ //----------------------------------------------------------------------
    public Vector2D<Float> GetBottomLeftBound() {
        return mBottomLeft;
    }

    // ------------------------------------------------------------------------
    /*! Get Coordinates
    *
    *   Sets the bounds of the camera
    */ //----------------------------------------------------------------------
    @Override
    public Vector2D<Float> GetCoordinates() {
        final Vector2D<Float> pos = GetParent().GetPosition();
        final Vector2D<Integer> dim = GraphicsPipeline.GetGraphicsPipeline().GetDimensions();
        return new Vector2D<>(Math.max(Math.min(pos.x, mBottomLeft.x), mTopRight.x) - dim.x / 2,
            Math.max(Math.min(pos.y, mBottomLeft.y), mTopRight.y) - dim.y / 2);
    }
}
