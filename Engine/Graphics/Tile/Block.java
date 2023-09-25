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

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Block {
    protected int mWidth;
    protected int mHeight;

    protected BufferedImage mImg;
    protected Vector2D<Integer> mPosition;

    public Block(BufferedImage img, Vector2D<Integer> position, int w, int h) {
        mImg = img;
        mPosition = position;
        mWidth = w;
        mHeight = h;
    }

    public abstract boolean Update(AABB p);

    public void Render(Graphics2D g, Vector2D<Float> camerapos) {
        g.drawImage(mImg, (int)(float)mPosition.x - (int)(float)camerapos.x, (int)(float)mPosition.y - (int)(float)camerapos.y, mWidth, mHeight, null);
    }
}