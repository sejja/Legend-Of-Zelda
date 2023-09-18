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

import Engine.Graphics.Sprite;

public class TilemapNorm extends Tilemap {

    private ArrayList<Tileblock> mBlocks;

    public TilemapNorm(String data, Sprite sprite, int width , int height, int tilewidth, int tileheight, int tilecolumns) {
        mBlocks = new ArrayList<>();

        String[] block = data.split(",");

        for(int i= 0; i < (width * height); i++) {
            int temp = Integer.parseInt(block[i].replaceAll("\\s+",""));

            if(temp != 0) {
                mBlocks.add(new Normblock());
            }
        }
    }

    public void Render(Graphics2D g) {
        for(int i = 0; i < mBlocks.size(); i++) {
            mBlocks.get(i).Render(g);
        }
    }
}
