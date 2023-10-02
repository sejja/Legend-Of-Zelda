//
//	TilemapNorm.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 15/09/2023
//	Copyright Deusto © 2023. All Rights reserved
// 

package Engine.Graphics.Tile;

import java.awt.Graphics2D;
import java.util.ArrayList;

import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.CameraComponent;
import Engine.Math.Vector2D;
import Engine.Physics.AABB;

public class TilemapNorm extends Tilemap {

    private Block[] mBlocks;

    public TilemapNorm(Vector2D<Float> position, String data, Spritesheet sprite, int width , int height, int tilewidth, int tileheight, int tilecolumns) {
        mBlocks = new Block[width * height];

        String[] block = data.split(",");

        for(int i= 0; i < (width * height); i++) {
            int temp = Integer.parseInt(block[i].replaceAll("\\s+",""));

            if(temp != 0) {

                Vector2D<Integer> positiontemp = new Vector2D<Integer>((int) (i % width) * tilewidth, (int) (i / height) * tileheight);
                positiontemp.x += (int)(float)position.x;
                positiontemp.y += (int)(float)position.y;
                mBlocks[i] = new Normblock(sprite.GetSprite((int) ((temp - 1) % tilecolumns), (int) ((temp - 1) / tilecolumns)), positiontemp, tilewidth, tileheight);
            }
        }
    }

    public void Render(Graphics2D g, CameraComponent camerapos) {
        Vector2D<Float> camcoord = camerapos.GetCoordinates();

        for(int i = 0; i < mBlocks.length; i++) {
            if(mBlocks[i] != null && camerapos.OnBounds(new AABB(new Vector2D<>((float)mBlocks[i].mPosition.x, (float)mBlocks[i].mPosition.y), new Vector2D<>((float)mBlocks[i].mWidth, (float)mBlocks[i].mHeight))))
                mBlocks[i].Render(g, camcoord);
        }
    }
}
