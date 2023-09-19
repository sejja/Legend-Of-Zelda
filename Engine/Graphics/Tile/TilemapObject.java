//
//	TilemapObject.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 15/09/2023
//	Copyright Deusto © 2023. All Rights reserved
// 

package Engine.Graphics.Tile;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

import Engine.Graphics.Spritesheet;

public class TilemapObject {

    public static HashMap<String, Tileblock> mBlocks;

    public TilemapObject(String data, Spritesheet sprite, int width , int height, int tilewidth, int tileheight, int tilecolumns) {
        Tileblock temp;
        mBlocks = new HashMap<>();

        String[] block = data.split(",");

        for(int i= 0; i < (width * height); i++) {
            int tempint = Integer.parseInt(block[i].replaceAll("\\s+",""));

            if(tempint != 0) {
                if(tempint == 172) { // TODO, Review
                    //tempBlock = new HoleBlock();
                } else {
                    temp = new ObjectBlock();
                }

                mBlocks.put(String.valueOf((int) (i % width)) + ", " + String.valueOf((int) (i / height)), temp);
            }
        }
    }

    public void Render(Graphics2D g) {
        for(Tileblock block : mBlocks.values()) {
            block.Render(g); 
        }
    }
}
