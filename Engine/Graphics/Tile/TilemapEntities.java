package Engine.Graphics.Tile;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Queue;

import Engine.ECSystem.World;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.CameraComponent;
import Engine.Math.Vector2D;
import Engine.Physics.AABB;
import Gameplay.Enemies.Units.*;

public class TilemapEntities extends Tilemap {
    public Block[] mBlocks;
    private int mTileWidth;
    private int mTileHeight;
    public int mHeight;
    public int mWidth;
    public Queue<parseEntity> entityQueue;
    public int firstEntity;

    //explanation WIP

    public TilemapEntities(Vector2D<Float> position, String data, Spritesheet sprite, int width , int height, int tilewidth, int tileheight, int tilecolumns) {
        mTileHeight = tileheight;
        mTileWidth = tilewidth;
        mHeight = height;
        mWidth = width;
        entityQueue = new LinkedList<parseEntity>();
        

        String[] block = data.split(",");

        for(int i= 0; i < (width * height); i++) {
            int tempint = Integer.parseInt(block[i].replaceAll("\\s+",""));

            Vector2D<Integer> positiontemp = new Vector2D<Integer>((int) (i % width) * tilewidth, (int) (i / height) * tileheight);
                positiontemp.x += (int)(float)position.x;
                positiontemp.y += (int)(float)position.y;

            if(tempint != 0) {
                entityQueue.add(new parseEntity(tempint, new Vector2D<Float>((float)positiontemp.x,(float)positiontemp.y)));
            }
        }
        //System.out.println(entityQueue);
        //System.out.println(entityQueue.size());
    }


    public void Render(Graphics2D g, CameraComponent camerapos, Vector2D<Float> tilemappos) {
    }
}

