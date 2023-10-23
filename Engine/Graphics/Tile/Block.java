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
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

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
        
        if(mImg != null){
            //setImageTransparency(35);;
        }
    }

    public abstract boolean Update(AABB p);
    public abstract boolean IsInside(AABB p);

    public void Render(Graphics2D g, Vector2D<Float> camerapos) {
        //g.drawImage(mImg, (int)(float)mPosition.x - (int)(float)camerapos.x, (int)(float)mPosition.y - (int)(float)camerapos.y, 64, 64, new Color(0,0,0, 128), null);
        g.drawImage(mImg, (int)(float)mPosition.x - (int)(float)camerapos.x, (int)(float)mPosition.y - (int)(float)camerapos.y, 64, 64, null);
    }


    public static int getWidth() {
        return mWidth;
    }

    public static int getHeight() {
        return mHeight;
    }

    private void setImageTransparency(int alpha){
        //mImg = scale1(mImg, 1);
        for(int i = 0; i< 16 ; i++){
            for (int j = 0; j < 16; j++){
                if(mImg.getRGB(i, j) != 0){
                    int pixel = mImg.getRGB(i, j);
                    Color model = new Color(pixel, true);
                    mImg.setRGB(i,j, ( new Color(model.getRed(), model.getGreen(), model.getBlue(), alpha)  ).getRGB());
                }
             }
        }
    }
}