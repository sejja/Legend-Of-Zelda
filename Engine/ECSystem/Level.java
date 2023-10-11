package Engine.ECSystem;

import Engine.ECSystem.Types.Actor;
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
    static public Level mCurrentLevel = null;

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
        mCurrentLevel = this;
    }

    public void Update() {
        Actor p = ObjectManager.GetObjectManager().GetPawn();
        var position = p.GetPosition();
        
        if(!GetBounds().Collides(new AABB(position, new Vector2D<>(1.f, 1.f)))) {
            //LEFT
            if(position.x > (GetBounds().GetPosition().x + GetBounds().GetWidth()) && mRight != null) {
                mRight.Init(new Vector2D<>(GetBounds().GetPosition().x + GetBounds().GetWidth(), GetBounds().GetPosition().y));
            }

            //RIGHT
            if(position.x < (GetBounds().GetPosition().x) && mLeft != null) {
                var b = mLeft.mTilemap.EstimateBounds(64, 64);
                Vector2D<Float> scale = new Vector2D<>(b.GetWidth(), b.GetHeight());
                var pos = GetBounds().GetPosition();
                pos.x -= scale.x;

                mLeft.Init(pos);
            }
            //UP
            if(position.y < (GetBounds().GetPosition().y) && mUpper != null) {
                var b = mUpper.mTilemap.EstimateBounds(64, 64);
                Vector2D<Float> scale = new Vector2D<>(b.GetWidth(), b.GetHeight());
                var pos = GetBounds().GetPosition();
                pos.y -= scale.y;

                mRight.Init(pos);
            }

            //DOWN
            if(position.y > (GetBounds().GetPosition().y + GetBounds().GetHeight()) && mLower != null)
                mLower.Init(new Vector2D<>(GetBounds().GetPosition().x, GetBounds().GetPosition().y + GetBounds().GetHeight()));
        }
    }

    void ShutDown() {}

    public Level GetRightLevel() {
        return mRight;
    }

    public Level GetLeftLevel() {
        return mLeft;
    }

    public Level GetUpperLevel() {
        return mUpper;
    }

    public Level GetLowerLevel() {
        return mLower;
    }

    public void SetRightLevel(Level l) {
        mRight = l;
    }

    public void SetUpperLevel(Level l) {
        mUpper = l;
    }

    public void SetLowerLevel(Level l) {
        mLower = l;
    }

    public void SetLeftlevel(Level l) {
        mLeft = l;
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