//
//	HoleBlock.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 19/09/2023
//	Copyright Deusto © 2023. All Rights reserved
// 

package Engine.Graphics.Tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;

import Engine.Graphics.Components.CameraComponent;
import Engine.Math.Vector2D;
import Engine.Physics.AABB;

public class HoleBlock extends Block {
    // ------------------------------------------------------------------------
    /*! Custom Constructor
    *
    *   Creates a Hole Block with an image and a desired transformation
    */ //----------------------------------------------------------------------
    public HoleBlock(final BufferedImage img, final AffineTransform transform) {
        super(img, transform);
    }

    // ------------------------------------------------------------------------
    /*! Has Collision
    *
    *   A hole block DOES collide
    */ //----------------------------------------------------------------------
    @Override
    public boolean HasCollision() {
        return true;
    }

    // ------------------------------------------------------------------------
    /*! Is Inside
    *
    *   Returns wether an object is completely inside us
    */ //----------------------------------------------------------------------
    @Override
    public boolean IsInside(final AABB p) {
        final double x = mPosition.getTranslateX();
        final double y = mPosition.getTranslateY();
        final Vector2D<Float> pPos = p.GetPosition();

        return !(pPos.x < x || pPos.y < y || sScale.x + x <  p.GetWidth() + pPos.x ||
            sScale.y + y <  p.GetHeight() + pPos.y);
    } 
}
