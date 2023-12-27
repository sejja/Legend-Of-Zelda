//
//	InputManager.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 15/09/2023
//	Copyright Deusto © 2023. All Rights reserved
// 

package Engine.Physics;

import Engine.Graphics.Tile.Block;
import Engine.Graphics.Tile.HoleBlock;
import Engine.Graphics.Tile.TileManager;
import Engine.Math.Vector2D;

public class AABB {
    private Vector2D<Float> mPosition;
    private Vector2D<Float> mSize;

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Constructs an AABB with a position, a width and a height
    */ //----------------------------------------------------------------------
    public AABB(final Vector2D<Float> pos, final Vector2D<Float> size) {
        mPosition = pos;
        mSize = size;
    }

    // ------------------------------------------------------------------------
    /*! Get Position
    *
    *   Returns the position
    */ //----------------------------------------------------------------------
    public Vector2D<Float> GetPosition() {
        return mPosition;
    }

    // ------------------------------------------------------------------------
    /*! Set Position
    *
    *   Sets the position of the AABB (as an offset)
    */ //----------------------------------------------------------------------
    public void SetPosition (final Vector2D<Float> position) {
        this.mPosition = position;
    }

    // ------------------------------------------------------------------------
    /*! Get Scale
    *
    *   Returns the scale
    */ //----------------------------------------------------------------------
    public Vector2D<Float> GetScale() {
        return this.mSize;
    }

    // ------------------------------------------------------------------------
    /*! Get Height
    *
    *   Returns the height
    */ //----------------------------------------------------------------------
    public float GetHeight() {
        return mSize.y;
    }

    // ------------------------------------------------------------------------
    /*! Get Width
    *
    *   Returns the width
    */ //----------------------------------------------------------------------
    public float GetWidth() {
        return mSize.x;
    }

    // ------------------------------------------------------------------------
    /*! Set Box
    *
    *   Sets the box, as with a position, a width and a height
    */ //----------------------------------------------------------------------
    public void SetBox(final Vector2D<Float> pos, final Vector2D<Float> size) {
        mPosition = pos;
        mSize = size;
    }

    // ------------------------------------------------------------------------
    /*! Set Size
    *
    *   Sets the Size of the AABB
    */ //----------------------------------------------------------------------
    public void SetSize(final Vector2D<Float> size){
        this.mSize = size;
    }

    // ------------------------------------------------------------------------
    /*! Set WIdth
    *
    *   Sets the Width
    */ //----------------------------------------------------------------------
    public void SetWidth(final float f) {
        mSize.x = f;
    }

    // ------------------------------------------------------------------------
    /*! Set Height
    *
    *   Sets the Height
    */ //----------------------------------------------------------------------
    public void SetHeight(final float f) {
        mSize.y = f;
    }

    // ------------------------------------------------------------------------
    /*! Collides
    *
    *   Returns wether two boxes collide or not
    */ //----------------------------------------------------------------------
    public boolean Collides(final AABB box) {
        return mPosition.x < box.mPosition.x + box.mSize.x &&
        mPosition.x + mSize.x > box.mPosition.x &&
        mPosition.y < box.mPosition.y + box.mSize.y &&
        mPosition.y + mSize.y > box.mPosition.y;
    }

    // ------------------------------------------------------------------------
    /*! Collides a Circle with a Box
    *
    *   Pretty much the name. A collision test
    */ //----------------------------------------------------------------------
    public boolean CollidesCircleBox(final AABB box) {
        return (Math.pow(mPosition.x - Math.max(box.mPosition.x + box.GetWidth() / 2, Math.min(mPosition.x, box.mPosition.x)), 2) 
            + Math.pow(mPosition.y - Math.max(box.mPosition.y + box.GetHeight() / 2,  Math.min(mPosition.y, box.mPosition.y)), 2))
            < Math.pow(Math.max(mSize.x, mSize.y) / Math.sqrt(2), 2);
    }

    // ------------------------------------------------------------------------
    /*! Collision Tile
    *
    *   Test against tile collision
    */ //----------------------------------------------------------------------
    public CollisionResult CollisionTile(final float ax, final float ay) {
        //Loop for 4 bytes
        for(int c = 0; c < 4; c++) {
            final Vector2D<Integer> pos = new Vector2D<>(
                (int)((mPosition.x + ax) + (c % 2) * mSize.x) / 64, 
                (int)((mPosition.y + ay) + (int)(c / 2) * mSize.y) / 64);

            //If we are within bounds
            if(pos.x < TileManager.sLevelObjects.mWidth && pos.y < 
                TileManager.sLevelObjects.mHeight) {
                final Block block = TileManager.sLevelObjects.GetBlockAt(pos);
                return block != null ? 
                    (block instanceof HoleBlock ? 
                    CollisionHole(ax, ay, pos.x, pos.y, block) ? 
                        CollisionResult.Hole : CollisionResult.None 
                    : block.HasCollision() ? 
                        CollisionResult.Wall : CollisionResult.None) 
                    : CollisionResult.None;
            }
        }

        return CollisionResult.None;
    }

    // ------------------------------------------------------------------------
    /*! Collision Hole
    *
    *   Test against a Hole
    */ //----------------------------------------------------------------------
    private boolean CollisionHole(final float ax, final float ay, final float xt,
        final float yt, final Block block) {
        final Vector2D<Integer> pos = new Vector2D<>(
            (int)((mPosition.x + ax) / 64 + mSize.x / 64), 
            (int)((mPosition.y + ay) / 64 + mSize.y / 64));

        return (pos.y == yt + 1) || (pos.x == xt + 1) ? 
            (TileManager.sLevelObjects.GetBlockAt(pos) != null ? 
            TileManager.sLevelObjects.GetBlockAt(pos).HasCollision() : false) 
            : block.IsInside(this) ? block.HasCollision() : false;
    }
}