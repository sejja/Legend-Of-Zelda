//
//	TilemapNorm.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 15/09/2023
//	Copyright Deusto © 2023. All Rights reserved
// 

package Engine.Graphics.Tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

import Engine.Developer.Logger.Logger;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.CameraComponent;
import Engine.Math.Vector2D;
import Engine.Physics.AABB;

public class TilemapNorm extends Tilemap {
    private BufferedImage mBuffer;

    public TilemapNorm(Vector2D<Float> position, String data, 
        ArrayList<Spritesheet> sprite, int width , int height, int tilewidth, 
        int tileheight, ArrayList<Integer> tilecolumns, ArrayList<Integer> ids) {
        Block[] mBlocks = new Block[width * height];
        String[] block = data.split(",");

        //Loop through all coverable tiles
        for(int i = 0, length = width * height; i < length; i++) {
            int id = Integer.parseInt(block[i].replaceAll("\\s+",""));

            //If we don't have a null block
            if(id != 0) {
                Vector2D<Integer> positiontemp = new Vector2D<Integer>(
                    (int) (i % width) * tilewidth + (int)(float)position.x, 
                    (int) (i / height) * tileheight + (int)(float)position.y);

                int sprite_id = 0; //Might be -1

                //Find the corresponding sprite
                for(Integer j : ids) {
                    if(j > sprite_id) break;
                    sprite_id++;
                }

                final BufferedImage sp = sprite.get(sprite_id).GetSprite((int) ((id - 1) % tilecolumns.get(0)), (int) ((id - 1) / tilecolumns.get(0)));

                //If we have a valid subsprite
                if(sp != null) {
                    AffineTransform transform = AffineTransform.getTranslateInstance(positiontemp.x, positiontemp.y);
                    transform.concatenate(AffineTransform.getScaleInstance(tilewidth, tileheight));
                    mBlocks[i] = new Normblock(sp, transform);
                }
            }
        }

        RenderToBuffer(position, tileheight, tilewidth, height, mBlocks);
    }

    // ------------------------------------------------------------------------
    /*! Render To Buffer
    *
    *   Renders the whole tilemap into a buffer, allowing for pre-computed rendering
    */ //----------------------------------------------------------------------
    private void RenderToBuffer(final Vector2D<Float> position, final int tileheight, final int tilewidth, final int height, final Block[] blocks) {
        mBuffer = new BufferedImage(height * tilewidth, height * tileheight, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D gfx = mBuffer.createGraphics();

        Arrays.stream(blocks).forEach(b -> {

            //In case the transform matrix is not inverstible, which is mathematically impossible (we are only translating)
            try {
                if(b != null)
                    b.RenderAtPosition(gfx, AffineTransform.getTranslateInstance(position.x, position.y));
            } catch (NoninvertibleTransformException e) {
                Logger.Instance().Log(Logger.Instance().GetLog("Engine"), 
                "TilemapNorm - Can't render tile: " + e.getMessage(), Level.WARNING, 4, Color.yellow);
            }
        });
    }

    // ------------------------------------------------------------------------
    /*! Render
    *
    *   Renders the map, which was already rendered to a buffer
    */ //----------------------------------------------------------------------
    public void Render(final Graphics2D g, final CameraComponent camerapos, final AABB tilemappos) {
        final AffineTransform transform = AffineTransform.getTranslateInstance(tilemappos.GetPosition().x, tilemappos.GetPosition().y);
        transform.concatenate(camerapos.GetViewMatrix());
        g.drawImage(mBuffer, transform, null);
    }
}
