package Engine.Graphics.Tile;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import Engine.ECSystem.World;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.CameraComponent;
import Engine.Math.Vector2D;
import Engine.Physics.AABB;
import Gameplay.Enemies.Units.*;

/**
 * Represents a tile map that includes entities.
 * Extends the Tilemap class.
 */
public class TilemapEntities extends Tilemap {
    public Block[] mBlocks;
    private int mTileWidth;
    private int mTileHeight;
    public int mHeight;
    public int mWidth;
    public Queue<parseEntity> entityQueue;
    static public int firstEntity;

    /**
     * Constructs a tile map with entities at the specified position.
     *
     * @param position     The initial position of the tilemap.
     * @param data         The data representing the tilemap and entities.
     * @param sprite       The spritesheets used in the tilemap.
     * @param width        The width of the tilemap.
     * @param height       The height of the tilemap.
     * @param tilewidth    The width of each tile.
     * @param tileheight   The height of each tile.
     * @param tilecolumns  The columns of tiles.
     * @param ids          The IDs of the entities.
     */
    public TilemapEntities(Vector2D<Float> position, String data, ArrayList<Spritesheet> sprite, int width , int height, int tilewidth, int tileheight, ArrayList<Integer> tilecolumns, ArrayList<Integer> ids) {
        mTileHeight = tileheight;
        mTileWidth = tilewidth;
        mHeight = height;
        mWidth = width;
        entityQueue = new LinkedList<parseEntity>();
        firstEntity = ids.get(1);

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


    /**
     * Renders the tilemap with entities.
     *
     * @param g           The graphics context.
     * @param camerapos   The camera position.
     * @param tilemappos  The position of the tilemap.
     */
    public void Render(Graphics2D g, CameraComponent camerapos, AABB tilemappos) {
    }
}

