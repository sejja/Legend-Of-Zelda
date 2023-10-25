//
//	Normblock.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 15/09/2023
//	Copyright Deusto © 2023. All Rights reserved
// 

package Engine.Graphics.Tile;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.image.BufferedImage;

import Engine.Math.Vector2D;
import Engine.Physics.AABB;

public class Normblock extends Block {
    protected boolean blocked;

    public Normblock(BufferedImage img, Vector2D<Integer> position, int w, int h) {
        super(img, position, new Vector2D<>(w, h));
        blocked = false;
    }
    public void setBlocked(boolean b){
        blocked = b;
    }

    public boolean isBlocked(){
        return blocked;
    }

    @Override
    public boolean HasCollision(AABB p) {
       return false;
    }

    public void Render(Graphics2D g, AffineTransform camerapos, AffineTransform inversecamera) throws NoninvertibleTransformException {
        super.Render(g, camerapos, inversecamera);
    }

    @Override
    public boolean IsInside(AABB p) {
        return false;
    }
}
