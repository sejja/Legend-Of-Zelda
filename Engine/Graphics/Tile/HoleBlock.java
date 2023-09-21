//
//	HoleBlock.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 19/09/2023
//	Copyright Deusto © 2023. All Rights reserved
// 

package Engine.Graphics.Tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Engine.Math.Vector2D;
import Engine.Physics.AABB;

public class HoleBlock extends Block {
    public HoleBlock(BufferedImage img, Vector2D<Integer> position, int w, int h) {
        super(img, position, w, h);
    }

    @Override
    public boolean Update(AABB p) {
        return false;
    }

    public void Render(Graphics2D g) {
        super.Render(g);
        g.setColor(Color.green);
        g.drawRect((int)(float)mPosition.x, (int)(float)mPosition.y, mWidth, mHeight);
    } 
}
