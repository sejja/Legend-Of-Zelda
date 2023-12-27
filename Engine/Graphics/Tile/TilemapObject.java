//
//	TilemapObject.java
//	Legend Of Zelda
//
//	Created by Diego Revilla on 15/09/2023
//	Copyright Deusto © 2023. All Rights reserved
// 

package Engine.Graphics.Tile;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;

import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.CameraComponent;
import Engine.Math.Vector2D;
import Engine.Physics.AABB;
import Engine.Graphics.Tile.TilemapObject;

public class TilemapObject extends Tilemap {

    public Block[] mBlocks;
    private int mTileWidth;
    private int mTileHeight;
    public int mHeight;
    public int mWidth;

    public TilemapObject(Vector2D<Float> position, String data, Spritesheet sprite, int width , int height, int tilewidth, int tileheight, int tilecolumns) {
        Block temp;
        mTileHeight = tileheight;
        mTileWidth = tilewidth;
        mHeight = height;
        mWidth = width;
        mBlocks = new Block[width * height];

        String[] block = data.split(",");

        for(int i= 0; i < (width * height); i++) {
            int tempint = Integer.parseInt(block[i].replaceAll("\\s+",""));

            Vector2D<Integer> positiontemp = new Vector2D<Integer>((int) (i % width) * tilewidth, (int) (i / height) * tileheight);
                positiontemp.x += (int)(float)position.x;
                positiontemp.y += (int)(float)position.y;

                
            if(tempint != 0) {
                AffineTransform transform = AffineTransform.getTranslateInstance(positiontemp.x, positiontemp.y);
                transform.concatenate(AffineTransform.getScaleInstance(tilewidth, tileheight));
                
                if(tempint == 429) { // TODO, Review
                    temp = new HoleBlock(sprite.GetSprite((int) ((tempint - 1) % tilecolumns), (int) ((tempint - 1) / tilecolumns)), transform);
                } else {
                    temp = new ObjectBlock(sprite.GetSprite((int) ((tempint - 1) % tilecolumns), (int) ((tempint - 1) / tilecolumns)), transform);
                }

                mBlocks[i] = temp;
            }
        }
    }

    public Block PlaceBlockAt(int x, int y) {
        // entiendo que el problema es el indice negativo pero la cosa es como solucionarlo 

        //TEendríamos que cargar la posición de la roca con unaç
        //posicion RELATIVA al nivel que pertenece
        //la cosa es que el spawn y asi que yo sepa funciona con 
        /*
         * coordenadas absolutas pero si es necesario la posicion relativa
         * podemos traducir la posicion absoluta a relativa con un metodo que
         * tenemos en el world
         */
        

        //Algo que puede funcionar, pero que es indeseable:
        while(x < 0) x += mWidth;

        assert(x >= 0);
        assert(y >= 0);
        //estas tres lineas no se como pero es lo que arregla

        if(GetBlockAt(x, y) != null) return GetBlockAt(x, y);

        AffineTransform transform = AffineTransform.getTranslateInstance(x, y);
        transform.concatenate(AffineTransform.getScaleInstance(mTileWidth, mTileHeight));


        //Creo que el problema es obvio, llegados a este punto

        //Mira los valores de la transofrmacion

        //Está colocando el bloque en: -13 + (26*50)
        return mBlocks[x + (y * mHeight)] = new ObjectBlock(null, transform);
    }

    public Block GetBlockAt(int x, int y) {
        return mBlocks[x + (y * mHeight)];
    }

    public void RemoveBlockAt(int x, int y) {
        mBlocks[x + (y * mHeight)] = null;
    }

    public void Render(Graphics2D g, CameraComponent camerapos, AABB tilemappos) {

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
