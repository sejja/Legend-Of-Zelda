//
//	TilemapNorm.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 15/09/2023
//	Copyright Deusto © 2023. All Rights reserved
// 

package Engine.Graphics.Tile;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.util.ArrayList;

import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.CameraComponent;
import Engine.Math.Vector2D;
import Engine.Physics.AABB;

public class TilemapNorm extends Tilemap {

    public static TilemapNorm tilemapNorm;
    private Block[] mBlocks;
    private int mTileWidth;
    private int mTileHeight;
    private int mHeight;

    public TilemapNorm(Vector2D<Float> position, String data, Spritesheet sprite, int width , int height, int tilewidth, int tileheight, int tilecolumns) {
        mBlocks = new Block[width * height];
        mTileHeight = tileheight;
        mTileWidth = tilewidth;
        mHeight = height;

        String[] block = data.split(",");

        for(int i= 0; i < (width * height); i++) {
            int temp = Integer.parseInt(block[i].replaceAll("\\s+",""));

            if(temp != 0) {
                Vector2D<Integer> positiontemp = new Vector2D<Integer>((int) (i % width) * tilewidth, (int) (i / height) * tileheight);
                positiontemp.x += (int)(float)position.x;
                positiontemp.y += (int)(float)position.y;
                if(sprite.GetSprite((int) ((temp - 1) % tilecolumns), (int) ((temp - 1) / tilecolumns)) != null) {
                    AffineTransform transform = AffineTransform.getTranslateInstance(positiontemp.x, positiontemp.y);
                    transform.concatenate(AffineTransform.getScaleInstance(tilewidth, tileheight));
                    mBlocks[i] = new Normblock(sprite.GetSprite((int) ((temp - 1) % tilecolumns), (int) ((temp - 1) / tilecolumns)), transform);
                }
            }
        }

        tilemapNorm = this;
    }

    public static TilemapNorm getTilemapNorm(){return tilemapNorm;}

    public Block GetBlockAt(int x, int y) {
        return mBlocks[x + (y * mHeight)];
    }

    public void Render(Graphics2D g, CameraComponent camerapos, AABB tilemappos) {
        //if(true){return;}
        var cameracoord = camerapos.GetCoordinates();
        int x = (int) ((cameracoord.x - tilemappos.GetPosition().x) / mTileWidth);
        int y = (int) ((cameracoord.y - tilemappos.GetPosition().y) / mTileHeight);

        for(int i = x; i < x + (camerapos.GetDimensions().x / mTileWidth) + 1; i++) {
            for(int j = y; j < y + (camerapos.GetDimensions().y / mTileHeight) + 2; j++) {
                if(i + (j * mHeight) > -1 && i + (j * mHeight) < mBlocks.length && mBlocks[i + (j * mHeight)] != null)
                    mBlocks[i + (j * mHeight)].Render(g, camerapos);
            }
         }
    }
}
