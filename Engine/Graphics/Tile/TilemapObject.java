//
//	TilemapObject.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 15/09/2023
//	Copyright Deusto © 2023. All Rights reserved
// 

package Engine.Graphics.Tile;

import java.awt.Graphics2D;
import java.util.HashMap;

import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.CameraComponent;
import Engine.Math.Vector2D;
import Engine.Physics.AABB;
import Engine.Graphics.Tile.TilemapObject;

public class TilemapObject extends Tilemap {

    public static Block[] mBlocks;
    private int mTileWidth;
    private int mTileHeight;
    private static int mHeight;
    private int mWidth;

    public TilemapObject(String data, Spritesheet sprite, int width , int height, int tilewidth, int tileheight, int tilecolumns) {
        Block temp;
        mTileHeight = tileheight;
        mTileWidth = tilewidth;
        mHeight = height;
        mWidth = width;
        mBlocks = new Block[width * height];

        String[] block = data.split(",");

        for(int i= 0; i < (width * height); i++) {
            int tempint = Integer.parseInt(block[i].replaceAll("\\s+",""));

            if(tempint != 0) {
                if(tempint == 172) { // TODO, Review
                    temp = new HoleBlock(sprite.GetSprite((int) ((tempint - 1) % tilecolumns), (int) ((tempint - 1) / tilecolumns)), new Vector2D<Integer>((int) (i % width) * tilewidth, (int) (i / height) * tileheight), tilewidth, tileheight);
                } else {
                    temp = new ObjectBlock(sprite.GetSprite((int) ((tempint - 1) % tilecolumns), (int) ((tempint - 1) / tilecolumns)), new Vector2D<Integer>((int) (i % width) * tilewidth, (int) (i / height) * tileheight), tilewidth, tileheight);
                }

                mBlocks[i] = temp;
            }
        }
    }

    public static Block GetBlockAt(int x, int y) {
        return mBlocks[x + (y * mHeight)];
    }

    public void Render(Graphics2D g, CameraComponent camerapos) {
        //Vector2D<Float> camcoord = camerapos.GetCoordinates();

        //for(Block x : mBlocks.values()) {
        //    if(camerapos.OnBounds(new AABB(new Vector2D<>((float)x.mPosition.x, (float)x.mPosition.y), new Vector2D<>((float)x.mWidth, (float)x.mHeight))))
        //        x.Render(g, camcoord);
        //}

        var cameracoord = camerapos.GetCoordinates();

        int x = (int) ((cameracoord.x) / mTileWidth);
        int y = (int) ((cameracoord.y) / mTileHeight);

        for(int i = x; i < x + (camerapos.GetDimensions().x / mTileWidth) + 1; i++) {
            for(int j = y; j < y + (camerapos.GetDimensions().y / mTileHeight) + 2; j++) {
                if(i + (j * mHeight) > -1 && i + (j * mHeight) < mBlocks.length && mBlocks[i + (j * mHeight)] != null)
                    mBlocks[i + (j * mHeight)].Render(g, cameracoord);
            }
        }
    }
}
