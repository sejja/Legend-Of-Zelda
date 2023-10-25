package Engine.Graphics.Tile;

import Engine.Math.Vector2D;

public class parseEntity {
    int type;
    Vector2D<Integer> position;
    // type 0 = Greenknight
    public parseEntity(int type, Vector2D<Integer> position) {
        this.type = type;
        this.position = position;
    }

}
