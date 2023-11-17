//
//	TilemapObject.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 15/09/2023
//	Copyright Deusto © 2023. All Rights reserved
// 

package Engine.Graphics.Tile;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Engine.Graphics.Spritesheet;
import Engine.Math.Vector2D;
import Engine.Graphics.Tile.CollisionLayer;

public class CollisionLayer extends Tilemap {
    public Block[] mBlocks;
    private int mTileWidth;
    private int mTileHeight;
    public int mHeight;
    public int mWidth;

    // ------------------------------------------------------------------------
    /*! Custom Constructor
    *
    *   Creates a new Layer with collision data, render ready
    */ //----------------------------------------------------------------------
    public CollisionLayer(final Vector2D<Float> position, final String data, 
    final ArrayList<Spritesheet> sprite, final Vector2D<Integer> scale, 
    final Vector2D<Integer> tilescale, final ArrayList<Integer> tilecolumns,
    final ArrayList<Integer> ids) {
        mTileHeight = tilescale.x;
        mTileWidth = tilescale.x;
        mHeight = scale.x;
        mWidth = scale.y;
        mBlocks = new Block[mWidth * mWidth];

        mBlocks = new Block[scale.x * scale.y];
        String[] block = data.split(",");

        //Loop through all coverable tiles
        for(int i = 0, length = scale.x * scale.y; i < length; i++) {
            int id = Integer.parseInt(block[i].replaceAll("\\s+",""));

            //If we don't have a null block
            if(id != 0) {
                Vector2D<Integer> positiontemp = new Vector2D<Integer>(
                    (int) (i % scale.x) * tilescale.x + (int)(float)position.x, 
                    (int) (i / scale.y) * tilescale.y + (int)(float)position.y);

                    int sprite_id = -1; //Might be -1

                    //Find the corresponding sprite
                    for(Integer j : ids) {
                        if(j > id) break;
                        sprite_id++;
                    }

                final BufferedImage sp = sprite.get(sprite_id).GetSprite((int) ((id - ids.get(sprite_id)) % tilecolumns.get(sprite_id)), (int) ((id - ids.get(sprite_id)) / tilecolumns.get(sprite_id)));

                //If we have a valid subsprite
                if(sp != null) {
                    AffineTransform transform = AffineTransform.getTranslateInstance(positiontemp.x, positiontemp.y);
                    transform.concatenate(AffineTransform.getScaleInstance(tilescale.x, tilescale.y));
                    mBlocks[i] = new Normblock(sp, transform);

                    if(id == 429) { // TODO, Review
                        mBlocks[i] = new HoleBlock(sp, transform);
                    } else {
                        mBlocks[i] = new ObjectBlock(sp, transform);
                    }
                }
            }
        }

        RenderToBuffer(position, tilescale, scale.y, mBlocks);
    }

    // ------------------------------------------------------------------------
    /*! Place Block At
    *
    *   Place a new Block at a desired position
    */ //----------------------------------------------------------------------
    public Block PlaceBlockAt(final Vector2D<Integer> position) {
        final Block b = GetBlockAt(position);

        //If we already have a block, there don't place another one
        if(b!= null) return b;

        AffineTransform transform = AffineTransform.getTranslateInstance(position.x, 
                                                                        position.y);
        transform.concatenate(AffineTransform.getScaleInstance(mTileWidth, mTileHeight));

        return mBlocks[position.x + (position.y * mHeight)] = new ObjectBlock(null, transform);
    }

    // ------------------------------------------------------------------------
    /*! Get Block At
    *
    *   Retrieves a block at a location
    */ //----------------------------------------------------------------------
    public Block GetBlockAt(final Vector2D<Integer> position) {
        if(position.x + (position.y * mHeight) > mBlocks.length || position.x + (position.y * mHeight) < 0) return null;
        return mBlocks[position.x + (position.y * mHeight)];
    }

    // ------------------------------------------------------------------------
    /*! Remove Block At
    *
    *   Removes the Block placed at a location
    */ //----------------------------------------------------------------------
    public void RemoveBlockAt(final Vector2D<Integer> position) {
        mBlocks[position.x + (position.y * mHeight)] = null;
    }
}
