//
//	Bloock.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 15/09/2023
//	Copyright Deusto © 2023. All Rights reserved
//

package Engine.Graphics.Tile;

import Engine.Math.Vector2D;
import Engine.Physics.AABB;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public abstract class Block {
    protected static int mWidth;
    protected static int mHeight;

    protected BufferedImage mImg;
    protected Vector2D<Integer> mPosition;
    protected int opacity;

    public Block(BufferedImage img, Vector2D<Integer> position, int w, int h) {
        mImg = img;
        mPosition = position;
        mWidth = w;
        mHeight = h;
    }

    public abstract boolean Update(AABB p);
    public abstract boolean IsInside(AABB p);

    public void Render(Graphics2D g, Vector2D<Float> camerapos) {
        //g.drawImage(mImg, (int)(float)mPosition.x - (int)(float)camerapos.x, (int)(float)mPosition.y - (int)(float)camerapos.y, 64, 64, new Color(0,0,0), null);
        g.drawImage(mImg, (int)(float)mPosition.x - (int)(float)camerapos.x, (int)(float)mPosition.y - (int)(float)camerapos.y, 64, 64, null);
    }


    public static int getWidth() {
        return mWidth;
    }

    public static int getHeight() {
        return mHeight;
    }

    private static BufferedImage scale1(BufferedImage before, double scale) {
        int w = before.getWidth();
        int h = before.getHeight();
        // Create a new image of the proper size
        int w2 = (int) (w * scale);
        int h2 = (int) (h * scale);
        BufferedImage after = new BufferedImage(w2, h2, BufferedImage.TYPE_INT_ARGB);
        AffineTransform scaleInstance = AffineTransform.getScaleInstance(scale, scale);
        AffineTransformOp scaleOp 
            = new AffineTransformOp(scaleInstance, AffineTransformOp.TYPE_BILINEAR);
    
        scaleOp.filter(before, after);
        return after;
    }
}