//
//	TilemapNorm.java
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

public class DecorativeLayer extends Tilemap {
    // ------------------------------------------------------------------------
    /*! Custom Constructor
    *
    *   Creates a new Layer with only decorative blocks, render ready
    */ //----------------------------------------------------------------------
    public DecorativeLayer(final Vector2D<Float> position, final String data, 
        final ArrayList<Spritesheet> sprite, final Vector2D<Integer> scale, 
        final Vector2D<Integer> tilescale, final ArrayList<Integer> tilecolumns,
        final ArrayList<Integer> ids) {
        Block[] mBlocks = new Block[scale.x * scale.y];
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
                }
            }
        }

        RenderToBuffer(position, tilescale, scale.y, mBlocks);
    }
}
