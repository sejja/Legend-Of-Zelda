package Engine.Math;

public class TileCoordinates extends Vector2D<Integer> {
    public TileCoordinates(Integer x, Integer y) {
        super(x, y);
    }

    public TileCoordinates(Vector2D<Float> vector2d) {
        super((int)(float)vector2d.x, (int)(float)vector2d.y);
    }

    public Vector2D<Integer> getTilePosition(){
        return new Vector2D<Integer>((Integer)(int)(float)this.x/64, (Integer)(int)(float)this.y/64);
    }

    static public Vector2D<Integer> ToTilePosition(Vector2D<Float> vector2d) {
        return new Vector2D<Integer>((Integer)(int)(float)vector2d.x/64, (Integer)(int)(float)vector2d.y/64);
    }

    static public Vector2D<Integer> ToTilePosition(EuclideanCoordinates vector2d) {
        return new Vector2D<Integer>((Integer)(int)(float)vector2d.x/64, (Integer)(int)(float)vector2d.y/64);
    }
}
