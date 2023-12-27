//
//	ObjectBlock.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 15/09/2023
//	Copyright Deusto © 2023. All Rights reserved
// 

package Engine.Graphics.Tile;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import Engine.Physics.AABB;

public class ObjectBlock extends Block {
    // ------------------------------------------------------------------------
    /*! Custom Contructor
    *
    *   Creates an Object Block, which does have a collision
    */ //----------------------------------------------------------------------
    public ObjectBlock(final BufferedImage img, final AffineTransform transform) {
        super(img, transform);
    }

    // ------------------------------------------------------------------------
    /*! Has Collision
    *
    *   This object DOES collide
    */ //----------------------------------------------------------------------
    @Override
    public boolean HasCollision() {
        return true;
    }

    // ------------------------------------------------------------------------
    /*! Is Inside
    *
    *   While this object collides, the player can never step on it
    */ //----------------------------------------------------------------------
    @Override
    public boolean IsInside(final AABB p) {
        return false;
    }
}
