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
import Engine.Math.Vector2D;

public class TilemapObject extends Tilemap {

    public static HashMap<String, Block> mBlocks;

    public TilemapObject(String data, Spritesheet sprite, int width , int height, int tilewidth, int tileheight, int tilecolumns) {
        Block temp;
        mBlocks = new HashMap<>();

        String[] block = data.split(",");

        for(int i= 0; i < (width * height); i++) {
            int tempint = Integer.parseInt(block[i].replaceAll("\\s+",""));

            if(tempint != 0) {
                if(tempint == 172) { // TODO, Review
                    temp = new HoleBlock(sprite.GetSprite((int) ((tempint - 1) % tilecolumns), (int) ((tempint - 1) / tilecolumns)), new Vector2D<Integer>((int) (i % width) * tilewidth, (int) (i / height) * tileheight), tilewidth, tileheight);
                } else {
                    temp = new ObjectBlock(sprite.GetSprite((int) ((tempint - 1) % tilecolumns), (int) ((tempint - 1) / tilecolumns)), new Vector2D<Integer>((int) (i % width) * tilewidth, (int) (i / height) * tileheight), tilewidth, tileheight);
                }

                mBlocks.put(String.valueOf((int) (i % width)) + ", " + String.valueOf((int) (i / height)), temp);
            }
        }
    }

    public void Render(Graphics2D g, Vector2D<Float> camerapos) {
        for(Block block : mBlocks.values()) {
            block.Render(g, camerapos); 
        }
    }
}
