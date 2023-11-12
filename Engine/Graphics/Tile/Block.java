//
//	Bloock.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 15/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.Graphics.Tile;

import Engine.Graphics.Components.CameraComponent;
import Engine.Math.Vector2D;
import Engine.Physics.AABB;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public abstract class Block {
    static protected Vector2D<Integer> sScale = new Vector2D<>();
    static private HashMap<BufferedImage, BufferedImage> sScaledBuffers;
    private BufferedImage mImg;
    protected AffineTransform mPosition;

    // ------------------------------------------------------------------------
    /*! Custom Constructor
    *
    *   Constructs a block with a custom image, a position, and dimensions
    */ //----------------------------------------------------------------------
    public Block(final BufferedImage img, final AffineTransform transform) {
        final int sX = (int)transform.getScaleX();
        final int sY = (int)transform.getScaleY();
        
        try {
            mImg = ScaleBuffer(img, sX, sY);
        } catch (IOException e) {
            System.err.println("Exception thrown when resizing buffered image, with Stack Trace:");
            e.printStackTrace();
            mImg = img;
        } finally {
            mPosition = AffineTransform.getTranslateInstance(transform.getTranslateX(), transform.getTranslateY());
            sScale.x = sX;
            sScale.y = sY;
        }
    }

    // ------------------------------------------------------------------------
    /*! Cleanse Buffers
    *
    *   Empties the Scaled Buffers
    */ //----------------------------------------------------------------------
    public static void CleanseBuffers() {
        sScaledBuffers.clear();
    }

    // ------------------------------------------------------------------------
    /*! Scale Buffer
    *
    *   Returns a new Buffered Image, scaled already
    */ //----------------------------------------------------------------------
    private static BufferedImage ScaleBuffer(final BufferedImage image, final int width, final int height) throws IOException {      
        if(image == null || image.getType() == 0) return image;
        
        return new AffineTransformOp(
            AffineTransform.getScaleInstance(
                (double)width/image.getWidth(), 
                (double)height/image.getHeight()), 
                AffineTransformOp.TYPE_NEAREST_NEIGHBOR)
                .filter(
                    image,
                    new BufferedImage(width, height, image.getType()
                ));
    }

    public abstract boolean HasCollision();
    public abstract boolean IsInside(final AABB p);

    // ------------------------------------------------------------------------
    /*! Render
    *
    *   Renders the image, on a certain position given the Camera Position
    */ //----------------------------------------------------------------------
    public void Render(final Graphics2D g, final CameraComponent camera) {
        mPosition.concatenate(camera.GetViewMatrix());
        g.drawImage(mImg, mPosition, null);
        mPosition.concatenate(camera.GetCameraMatrix());
    }

    // ------------------------------------------------------------------------
    /*! Render
    *
    *   Renders the image, on a certain position given the Camera Position
    */ //----------------------------------------------------------------------
    public void RenderAtPosition(final Graphics2D g, final AffineTransform tranf) throws NoninvertibleTransformException {
        mPosition.concatenate(tranf);
        g.drawImage(mImg, mPosition, null);
        mPosition.concatenate(tranf.createInverse());
    }

    // ------------------------------------------------------------------------
    /*! Get Width
    *
    *   returns the Width of the blocks
    */ //----------------------------------------------------------------------
    public static int GetWidth() {
        return sScale.x;
    }

    // ------------------------------------------------------------------------
    /*! Get Height
    *
    *   Returns the height of the blocks
    */ //----------------------------------------------------------------------
    public static int GetHeight() {
        return sScale.y;
    }
}