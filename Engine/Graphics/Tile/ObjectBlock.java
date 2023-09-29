//
//	ObjectBlock.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 15/09/2023
//	Copyright Deusto © 2023. All Rights reserved
// 

package Engine.Graphics.Tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Engine.Math.Vector2D;
import Engine.Physics.AABB;

public class ObjectBlock extends Block {
    public ObjectBlock(BufferedImage img, Vector2D<Integer> position, int w, int h) {
        super(img, position, w, h);
    }

    @Override
    public boolean Update(AABB p) {
        return true;
    }

    public void Render(Graphics2D g, Vector2D<Float> camerapos) {
        super.Render(g, camerapos);
        //g.setColor(Color.white);
        //g.drawRect((int)(float)mPosition.x - (int)(float)camerapos.x, (int)(float)mPosition.y - (int)(float)camerapos.y, mWidth, mHeight);
    }

    @Override
    public boolean IsInside(AABB p) {
        return false;
    }  
}
