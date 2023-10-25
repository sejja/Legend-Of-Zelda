package Engine.Graphics.Tile;

import Engine.Math.Vector2D;

public class parseEntity {
    public int type;
    public Vector2D<Float> position;
    // type 0 = Greenknight
    public parseEntity(int type, Vector2D<Float> position) {
        this.type = type;
        this.position = position;
    }

}
