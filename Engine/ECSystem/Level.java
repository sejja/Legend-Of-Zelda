package Engine.ECSystem;

import Engine.ECSystem.Types.Entity;
import Engine.Graphics.Tile.TileManager;
import Engine.Math.Vector2D;
import Engine.Physics.AABB;

public class Level {
    private Level mRight;
    private Level mLeft;
    private Level mUpper;
    private Level mLower;
    private TileManager mTilemap;

    protected Level(Level right, Level left, Level up, Level down, TileManager tiles) {
        mRight = right;
        mLeft = left;
        mUpper = up;
        mLower = down;
        mTilemap = tiles;
    }

    protected AABB GetBounds() {
        return mTilemap.GetBounds();
    }

    public void Init(Vector2D<Float> position) {
        mTilemap.CreateTileMap(64, 64, position);
    }

    public void Update() {
    }

    void ShutDown() {}

    Level GetRightLevel() {
        return mRight;
    }

    Level GetLeftLevel() {
        return mLeft;
    }

    Level GetUpperLevel() {
        return mUpper;
    }

    Level GetLowerLevel() {
        return mLower;
    }

    protected void SpawnEntity(Entity e) {
        Vector2D<Float> finalpos = new Vector2D<>(e.GetPosition());
        finalpos.x += GetBounds().GetPosition().x;
        finalpos.y += GetBounds().GetPosition().y;
        e.SetPosition(finalpos);
        ObjectManager.GetObjectManager().AddEntity(e);
    }

    void DespawnEntity(Entity e) {
        ObjectManager.GetObjectManager().RemoveEntity(e);
    }
}
