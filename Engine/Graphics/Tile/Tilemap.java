//
//	Tilemap.java
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
import java.util.logging.Level;
import java.util.Arrays;

import Engine.Developer.Logger.Logger;
import Engine.Graphics.Components.CameraComponent;
import Engine.Math.Vector2D;
import Engine.Physics.AABB;

public class Tilemap {
    private BufferedImage mBuffer;

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

    // ------------------------------------------------------------------------
    /*! Render To Buffer
    *
    *   Renders the whole tilemap into a buffer, allowing for pre-computed rendering
    */ //----------------------------------------------------------------------
    protected void RenderToBuffer(final Vector2D<Float> position, final Vector2D<Integer> scale, final int height, final Block[] blocks) {
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
                "Tilemap - Can't render tile: " + e.getMessage(), Level.WARNING, 4, Color.yellow);
            }
        });
    }
}