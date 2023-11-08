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
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
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

public class DecorativeLayer extends Tilemap {
    private BufferedImage mBuffer;

    // ------------------------------------------------------------------------
    /*! 
    *
    *   Renders the whole tilemap into a buffer, allowing for pre-computed rendering
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
                    transform.concatenate(AffineTransform.getScaleInstance(tilescale.x, tilescale.y));
                    mBlocks[i] = new Normblock(sp, transform);
                }
            }
        }

        RenderToBuffer(position, tilescale, scale.y, mBlocks);
    }

    // ------------------------------------------------------------------------
    /*! Render To Buffer
    *
    *   Renders the whole tilemap into a buffer, allowing for pre-computed rendering
    */ //----------------------------------------------------------------------
    private void RenderToBuffer(final Vector2D<Float> position, final Vector2D<Integer> scale, final int height, final Block[] blocks) {
        GraphicsConfiguration gfxConfig = GraphicsEnvironment.
        getLocalGraphicsEnvironment().getDefaultScreenDevice().
        getDefaultConfiguration();
        mBuffer = gfxConfig.createCompatibleImage(
            height * scale.x, height * scale.y, Transparency.TRANSLUCENT);
        final Graphics2D gfx = mBuffer.createGraphics();

        Arrays.stream(blocks).forEach(b -> {

            //In case the transform matrix is not inverstible, which is mathematically impossible (we are only translating)
            try {
                if(b != null)
                    b.RenderAtPosition(gfx, AffineTransform.getTranslateInstance(-position.x, -position.y));
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
