//
//	Normblock.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 15/09/2023
//	Copyright Deusto © 2023. All Rights reserved
// 

package Engine.Graphics.Tile;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import Engine.Physics.AABB;

public class Normblock extends Block {
    // ------------------------------------------------------------------------
    /*! Custom Constructor
    *
    *   Constructs a Regular Block, which does not have a collision
    */ //----------------------------------------------------------------------
    public Normblock(final BufferedImage img, final AffineTransform transform) {
        super(img, transform);
    }

    // ------------------------------------------------------------------------
    /*! Has Collision
    *
    *   This block does NOT collide
    */ //----------------------------------------------------------------------
    @Override
    public boolean HasCollision() {
       return false;
    }

    // ------------------------------------------------------------------------
    /*! Is Inside
    *
    *   This block does not collide, return false
    */ //----------------------------------------------------------------------
    @Override
    public boolean IsInside(final AABB p) {
        return false;
    }
}
