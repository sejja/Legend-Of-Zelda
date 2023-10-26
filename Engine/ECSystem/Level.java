package Engine.ECSystem;

import Engine.ECSystem.Types.Actor;
import Engine.ECSystem.Types.Entity;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.ZeldaCameraComponent;
import Engine.Graphics.Tile.TileManager;
import Engine.Math.Util;
import Engine.Math.Vector2D;
import Engine.Physics.AABB;
import Engine.Window.GameLoop;
import Gameplay.AnimatedObject.Torch;
import Gameplay.Enemies.Units.GreenKnight;
import Gameplay.Interactives.Blocks.Rock;
import Gameplay.Link.Player;

public class Level {
    private Level mRight;
    private Level mLeft;
    private Level mUpper;
    private Level mLower;
    public TileManager mTilemap;
    static public Level mCurrentLevel = null;
    static private boolean sTransitioning = false;
    static private Vector2D<Float> sPreviousTopRight;
    static private Vector2D<Float> sPreviousBottomLeft;
    static private float sElapsedTime = 0;
    static private Level sPreviusLevel;
    
    private boolean visited = false;

    protected Level(Level right, Level left, Level up, Level down, TileManager tiles) {
        mRight = right;
        mLeft = left;
        mUpper = up;
        mLower = down;
        mTilemap = tiles;
    }

    public AABB GetBounds() {
        return mTilemap.GetBounds();
    }

    public void Init(Vector2D<Float> position) {
        mCurrentLevel = this;
        mTilemap.CreateTileMap(64, 64, position);
        if(!visited) {
            visited = true;
            spawnEntities();
        }
    }

    private void spawnEntities() {
        //System.out.println(mTilemap.entityQueue);
        int firstEntity = mTilemap.firstEntity;
        while(mTilemap.entityQueue != null && !mTilemap.entityQueue.isEmpty() ) {
            //System.out.println("Juan");
            var e = mTilemap.entityQueue.poll();
            //System.out.println(e.type+"  "+ firstEntity);
            if(e.type == firstEntity) {
                SpawnEntity(new GreenKnight(e.position));
            }else if(e.type == firstEntity+1) {
                SpawnEntity(new Rock(e.position));
            }else if(e.type == firstEntity+2) {
                SpawnEntity(new Torch(e.position));
            }
            
        }
    }

    public void Update() {
        if(!sTransitioning) {
            Actor p = ObjectManager.GetObjectManager().GetPawn();
            var position = p.GetPosition();
            sPreviusLevel = this;

            if(!GetBounds().Collides(new AABB(position, new Vector2D<>(1.f, 1.f)))) {
                //LEFT
                if(position.x > (GetBounds().GetPosition().x + GetBounds().GetWidth()) && mRight != null) {
                    mRight.Init(new Vector2D<>(GetBounds().GetPosition().x + GetBounds().GetWidth(), GetBounds().GetPosition().y));
                    sTransitioning = true;
                     var z = (ZeldaCameraComponent) GraphicsPipeline.GetGraphicsPipeline().GetCamera();
                    sPreviousTopRight = z.GetTopRightBound();
                    sPreviousBottomLeft = z.GetBottomLeftBound();
                }

                //RIGHT
                if(position.x < (GetBounds().GetPosition().x) && mLeft != null) {
                    var b = mLeft.mTilemap.EstimateBounds(64, 64);
                    Vector2D<Float> scale = new Vector2D<>(b.GetWidth(), b.GetHeight());
                    var pos = GetBounds().GetPosition();
                    pos.x -= scale.x;

                    mLeft.Init(pos);
                    sTransitioning = true;
                    var z = (ZeldaCameraComponent) GraphicsPipeline.GetGraphicsPipeline().GetCamera();
                    sPreviousTopRight = z.GetTopRightBound();
                    sPreviousBottomLeft = z.GetBottomLeftBound();
                }
                //UP
                if(position.y < (GetBounds().GetPosition().y) && mUpper != null) {
                    var b = mUpper.mTilemap.EstimateBounds(64, 64);
                    Vector2D<Float> scale = new Vector2D<>(b.GetWidth(), b.GetHeight());
                    var pos = GetBounds().GetPosition();
                    pos.y -= scale.y;

                    mRight.Init(pos);
                    sTransitioning = true;
                    var z = (ZeldaCameraComponent) GraphicsPipeline.GetGraphicsPipeline().GetCamera();
                    sPreviousTopRight = z.GetTopRightBound();
                    sPreviousBottomLeft = z.GetBottomLeftBound();
                }

                //DOWN
                if(position.y > (GetBounds().GetPosition().y + GetBounds().GetHeight()) && mLower != null) {
                    mLower.Init(new Vector2D<>(GetBounds().GetPosition().x, GetBounds().GetPosition().y + GetBounds().GetHeight()));
                    sTransitioning = true;
                    var z = (ZeldaCameraComponent) GraphicsPipeline.GetGraphicsPipeline().GetCamera();
                    sPreviousTopRight = z.GetTopRightBound();
                    sPreviousBottomLeft = z.GetBottomLeftBound();
                }
            }
        } else {
            if(sElapsedTime < 0.5) {
                GameLoop.SetPaused(true);
                Actor p = ObjectManager.GetObjectManager().GetPawn();

                var z = (ZeldaCameraComponent) GraphicsPipeline.GetGraphicsPipeline().GetCamera();

                Vector2D<Float> goaltopright = new Vector2D<>(p.GetPosition().x, p.GetPosition().y);

                Vector2D<Float> goaltopleft = new Vector2D<>(p.GetPosition().x + p.GetScale().x - 1280.f / 2, p.GetPosition().y + p.GetScale().y - 760.f / 2);
                z.SetBounds(new Vector2D<Float>(Util.LinearInterpolate(sPreviousTopRight.x, goaltopright.x, sElapsedTime * 2), Util.LinearInterpolate(sPreviousTopRight.y, goaltopright.y, sElapsedTime * 2)),
                            new Vector2D<Float>(Util.LinearInterpolate(sPreviousBottomLeft.x, goaltopleft.x, sElapsedTime * 2), Util.LinearInterpolate(sPreviousBottomLeft.x, goaltopleft.x, sElapsedTime * 2)));
                sElapsedTime += 0.016;
            } else if(sElapsedTime < 1.f) {
                Actor p = ObjectManager.GetObjectManager().GetPawn();

                var z = (ZeldaCameraComponent) GraphicsPipeline.GetGraphicsPipeline().GetCamera();

                Vector2D<Float> goaltopright = new Vector2D<>(p.GetPosition().x, p.GetPosition().y);

                Vector2D<Float> goaltopleft = new Vector2D<>(p.GetPosition().x + p.GetScale().x - 1280.f / 2, p.GetPosition().y + p.GetScale().y - 760.f / 2);

                Vector2D<Float> goaltopright2 = new Vector2D<>(GetBounds().GetPosition().x + 1280.f / 2, GetBounds().GetPosition().y + 720.f / 2);

                Vector2D<Float> goaltopleft2 = new Vector2D<>(GetBounds().GetPosition().x + GetBounds().GetWidth() - 1280.f / 2, GetBounds().GetPosition().y + GetBounds().GetHeight() - 760.f / 2);
                z.SetBounds(new Vector2D<Float>(Util.LinearInterpolate(goaltopright.x, goaltopright2.x, (sElapsedTime - 0.5f) * 2), Util.LinearInterpolate(goaltopright.y, goaltopright2.y, (sElapsedTime - 0.5f) * 2)),
                            new Vector2D<Float>(Util.LinearInterpolate(goaltopleft.x, goaltopleft2.x, (sElapsedTime - 0.5f) * 2), Util.LinearInterpolate(goaltopleft.x, goaltopleft2.y, (sElapsedTime - 0.5f) * 2)));
                sElapsedTime += 0.016;
            } else {
                GameLoop.SetPaused(false);
                sTransitioning = false;
                var z = (ZeldaCameraComponent) GraphicsPipeline.GetGraphicsPipeline().GetCamera();

                Vector2D<Float> topright = new Vector2D<>(GetBounds().GetPosition().x + 1280.f / 2, GetBounds().GetPosition().y + 720.f / 2);

                Vector2D<Float> bottomleft = new Vector2D<>(GetBounds().GetPosition().x + GetBounds().GetWidth() - 1280.f / 2, GetBounds().GetPosition().y + GetBounds().GetHeight() - 760.f / 2);
                z.SetBounds(topright, bottomleft);
                GraphicsPipeline.GetGraphicsPipeline().RemoveRenderable(sPreviusLevel.mTilemap);
                sElapsedTime = 0.f;
            }
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

    public void SpawnEntity(Entity e) {
        Vector2D<Float> finalpos = new Vector2D<Float>(e.GetPosition().x,e.GetPosition().y);
        finalpos.x += GetBounds().GetPosition().x;
        finalpos.y += GetBounds().GetPosition().y;
        e.SetPosition(finalpos);
        ObjectManager.GetObjectManager().AddEntity(e);
    }

    void DespawnEntity(Entity e) {
        ObjectManager.GetObjectManager().RemoveEntity(e);
    }
}