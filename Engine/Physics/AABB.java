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
import Engine.Graphics.Tile.Normblock;
import Engine.Graphics.Tile.TileManager;
import Engine.Graphics.Tile.TilemapObject;
import Engine.Math.Vector2D;

public class AABB {
    private Vector2D<Float> mPosition;
    private Vector2D<Float> mSize;

    // ------------------------------------------------------------------------
    /*! Constructor
    *
    *   Constructs an AABB with a position, a width and a height
    */ //----------------------------------------------------------------------
    public AABB(Vector2D<Float> pos, Vector2D<Float> size) {
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
    public void SetPosition (Vector2D<Float> position){
        this.mPosition = position;
    }
    public Vector2D<Float> GetScale(){
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
    public void SetBox(Vector2D<Float> pos, Vector2D<Float> size) {
        mPosition = pos;
        mSize = size;
    }

    public void SetSize(Vector2D<Float> size){
        this.mSize = size;
    }
    // ------------------------------------------------------------------------
    /*! Set WIdth
    *
    *   Sets the Width
    */ //----------------------------------------------------------------------
    public void SetWidth(float f) {
        mSize.x = f;
    }

    // ------------------------------------------------------------------------
    /*! Set Height
    *
    *   Sets the Height
    */ //----------------------------------------------------------------------
    public void SetHeight(float f) {
        mSize.y = f;
    }

    // ------------------------------------------------------------------------
    /*! Collides
    *
    *   Returns wether two boxes collide or not
    */ //----------------------------------------------------------------------
    public boolean Collides(AABB box) {
        /*final float halfxsize = mSize.x / 2;
        final float halfysize = mSize.y / 2;

        //If they overlap on the X axis
        if(Math.abs(((mPosition.x) + halfxsize) - ((box.mPosition.x) + halfxsize)) < halfxsize + (box.mSize.x / 2))

            //If they overlap on the Y Axis
            if(Math.abs(((mPosition.y) + halfysize) - ((box.mPosition.x) + halfysize)) < halfysize + (box.mSize.x / 2))
                return true;

        return false;*/

        if(mPosition.x < box.mPosition.x + box.mSize.x &&
            mPosition.x + mSize.x > box.mPosition.x &&
            mPosition.y < box.mPosition.y + box.mSize.y &&
            mPosition.y + mSize.y > box.mPosition.y)
                return true;
        return false;
    }

    // ------------------------------------------------------------------------
    /*! Collides a Circle with a Box
    *
    *   CPretty much the name. A collision test
    */ //----------------------------------------------------------------------
    public boolean CollidesCircleBox(AABB box) {
        return (Math.pow(mPosition.x - Math.max(box.mPosition.x + box.GetWidth() / 2, Math.min(mPosition.x, box.mPosition.x)), 2) 
            + Math.pow(mPosition.y - Math.max(box.mPosition.y + box.GetHeight() / 2,  Math.min(mPosition.y, box.mPosition.y)), 2))
            < Math.pow(Math.max(mSize.x, mSize.y) / Math.sqrt(2), 2);
    }

    public CollisionResult collisionTile(float ax, float ay) {
        for(int c = 0; c < 4; c++) {
            int xt = (int)((mPosition.x + ax) + (c % 2) * mSize.x) / 64;
            int yt = (int)((mPosition.y + ay) + (int)(c / 2) * mSize.y) / 64;
            if(!(TileManager.sLevelObjects.GetBlockAt(xt, yt) instanceof Normblock)
                && xt < TileManager.sLevelObjects.mWidth && yt < TileManager.sLevelObjects.mHeight) {
                Block block = TileManager.sLevelObjects.GetBlockAt(xt, yt);
                if(block instanceof HoleBlock) {
                    return collisionHole(ax, ay, xt, yt, block) ? CollisionResult.Hole : CollisionResult.None;
                }
                return block != null ? (block.HasCollision() ? CollisionResult.Wall : CollisionResult.None) : CollisionResult.None;
            }
        }

        return CollisionResult.None;
    }

    private boolean collisionHole(float ax, float ay, float xt, float yt, Block block) {
        int nextXT = (int)((mPosition.x + ax) / 64 + mSize.x / 64);
        int nextYT = (int)((mPosition.y + ay) / 64 + mSize.y / 64);

        if((nextYT == yt + 1) || (nextYT == xt + 1)) {
            if(TileManager.sLevelObjects.GetBlockAt(nextXT, nextYT) != null) {
                Block neighbour = TileManager.sLevelObjects.GetBlockAt(nextXT, nextYT);
                return neighbour.HasCollision(this);
            }
        } else {
            if(block.IsInside(this)) {
                return block.HasCollision(this);
            }
        }

        return false;
    }
}