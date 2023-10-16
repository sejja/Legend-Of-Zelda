//
//	Normblock.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 15/09/2023
//	Copyright Deusto © 2023. All Rights reserved
// 

package Engine.Graphics.Tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Engine.Math.Vector2D;
import Engine.Physics.AABB;

public class Normblock extends Block {
    protected boolean blocked = false;

    public Normblock(BufferedImage img, Vector2D<Integer> position, int w, int h) {
        super(img, position, w, h);
    }
    public void setBlocked(boolean b){
        blocked = b;
    }

    @Override
    public boolean Update(AABB p) {
       return false;
    }

    public void Render(Graphics2D g, Vector2D<Float> camerapos) {
        super.Render(g, camerapos);
    }

    @Override
    public boolean IsInside(AABB p) {
        return false;
    }
}
