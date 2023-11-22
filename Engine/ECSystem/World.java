package Engine.ECSystem;

import java.sql.ResultSet;
import java.sql.SQLException;

import Engine.Developer.DataBase.Database;
import Engine.ECSystem.Types.Actor;
import Engine.ECSystem.Types.Entity;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.ZeldaCameraComponent;
import Engine.Graphics.Tile.TileManager;
import Engine.Graphics.Tile.TilemapEntities;
import Engine.Math.Util;
import Engine.Math.Vector2D;
import Engine.Physics.AABB;
import Engine.Window.GameLoop;
import Gameplay.AnimatedObject.Torch;
import Gameplay.Enemies.Search.Pair;
import Gameplay.Enemies.Units.GreenKnight;
import Gameplay.Interactives.Blocks.Rock;
import Gameplay.Link.Player;

public class World {
    private Integer mRight;
    private Integer mLeft;
    private Integer mUpper;
    private Integer mLower;
    public TileManager mTilemap;
    static public World mCurrentLevel = null;
    static private boolean sTransitioning = false;
    static private Vector2D<Float> sPreviousTopRight;
    static private Vector2D<Float> sPreviousBottomLeft;
    static private float sElapsedTime = 0;
    static private World sPreviusLevel;
    
    private boolean visited = false;

    public static void Reset() {
        mCurrentLevel = null;
        sTransitioning = false;
        sElapsedTime = 0;
    }

    protected World(Integer id) {
        ResultSet s = Database.Instance().Query("select * from Levels where id = " + id);
        String tiles = null;
        Integer left = null;
        Integer right = null;
        Integer up = null;
        Integer down = null;

        try {
            tiles = s.getString("Path");
            try {
                left = Integer.parseInt(s.getString("Left"));
            } catch(Exception e) {

            }

            try {
                right = Integer.parseInt(s.getString("Right"));
            } catch(Exception e) {

            }

            try {
                up = Integer.parseInt(s.getString("Up"));
            } catch(Exception e) {

            }

            try {
                down = Integer.parseInt(s.getString("Down"));
            } catch(Exception e) {

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        mRight = right;
        mLeft = left;
        mUpper = up;
        mLower = down;
        mTilemap = new TileManager(tiles);
    }

    public AABB GetBounds() {
        return mTilemap.GetBounds();
    }

    public void Init(Vector2D<Float> position) {
        mCurrentLevel = this;
        mTilemap.CreateTileMap(position, new Vector2D<>(64, 64));
        if(!visited) {
            visited = true;
            spawnEntities();
        }
    }

    public static Vector2D<Float> GetWorldSpaceCoordinates(Vector2D<Float> levelcoordinate) {
        Vector2D<Float> finalpos = new Vector2D<Float>(levelcoordinate.x,levelcoordinate.y);
        finalpos.x += mCurrentLevel.GetBounds().GetPosition().x;
        finalpos.y += mCurrentLevel.GetBounds().GetPosition().y;
        return finalpos;
    }

    public static Vector2D<Float> GetLevelSpaceCoordinates(Vector2D<Float> worldcoordinate) {
        Vector2D<Float> finalpos = new Vector2D<Float>(worldcoordinate.x,worldcoordinate.y);
        finalpos.x -= mCurrentLevel.GetBounds().GetPosition().x;
        finalpos.y -= mCurrentLevel.GetBounds().GetPosition().y;
        return finalpos;
    }

    public static Pair GetLevelPair(Pair pair) {
        int x= pair.getFirst();
        int y= pair.getSecond();
        x += mCurrentLevel.GetBounds().GetPosition().x/64;
        y += mCurrentLevel.GetBounds().GetPosition().y/64;
        return new Pair(x,y);
    }

    private void spawnEntities() {
        //System.out.println(mTilemap.entityQueue);
        int firstEntity = TilemapEntities.firstEntity;
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
                    World v = new World(mRight);
                    v.Init(new Vector2D<>(GetBounds().GetPosition().x + GetBounds().GetWidth(), GetBounds().GetPosition().y));
                    sTransitioning = true;
                    var z = (ZeldaCameraComponent) GraphicsPipeline.GetGraphicsPipeline().GetBindedCamera();
                    sPreviousTopRight = z.GetTopRightBound();
                    sPreviousBottomLeft = z.GetBottomLeftBound();
                }

                //RIGHT
                if(position.x < (GetBounds().GetPosition().x) && mLeft != null) {
                    World v = new World(mLeft);
                    var b = v.mTilemap.EstimateBounds(new Vector2D<>(64, 64));
                    Vector2D<Float> scale = new Vector2D<>(b.GetWidth(), b.GetHeight());
                    var pos = GetBounds().GetPosition();
                    pos.x -= scale.x;

                    v.Init(pos);
                    sTransitioning = true;
                    var z = (ZeldaCameraComponent) GraphicsPipeline.GetGraphicsPipeline().GetBindedCamera();
                    sPreviousTopRight = z.GetTopRightBound();
                    sPreviousBottomLeft = z.GetBottomLeftBound();
                }
                //UP
                if(position.y < (GetBounds().GetPosition().y) && mUpper != null) {
                    World v = new World(mUpper);
                    var b = v.mTilemap.EstimateBounds(new Vector2D<>(64, 64));
                    Vector2D<Float> scale = new Vector2D<>(b.GetWidth(), b.GetHeight());
                    var pos = GetBounds().GetPosition();
                    pos.y -= scale.y;

                    v.Init(pos);
                    sTransitioning = true;
                    var z = (ZeldaCameraComponent) GraphicsPipeline.GetGraphicsPipeline().GetBindedCamera();
                    sPreviousTopRight = z.GetTopRightBound();
                    sPreviousBottomLeft = z.GetBottomLeftBound();
                }

                //DOWN
                if(position.y > (GetBounds().GetPosition().y + GetBounds().GetHeight()) && mLower != null) {
                    World v = new World(mUpper);
                    v.Init(new Vector2D<>(GetBounds().GetPosition().x, GetBounds().GetPosition().y + GetBounds().GetHeight()));
                    sTransitioning = true;
                    var z = (ZeldaCameraComponent) GraphicsPipeline.GetGraphicsPipeline().GetBindedCamera();
                    sPreviousTopRight = z.GetTopRightBound();
                    sPreviousBottomLeft = z.GetBottomLeftBound();
                }
            }
        } else {
            var z = (ZeldaCameraComponent) GraphicsPipeline.GetGraphicsPipeline().GetBindedCamera();
            z.Update();

            if(sElapsedTime < 0.5) {
                GameLoop.SetPaused(true);
                Actor p = ObjectManager.GetObjectManager().GetPawn();

                Vector2D<Float> goaltopright = new Vector2D<>(p.GetPosition().x, p.GetPosition().y);

                Vector2D<Float> goaltopleft = new Vector2D<>(p.GetPosition().x + p.GetScale().x - 1280.f / 2, p.GetPosition().y + p.GetScale().y - 760.f / 2);
                z.SetBounds(new Vector2D<Float>(Util.LinearInterpolate(sPreviousTopRight.x, goaltopright.x, sElapsedTime * 2), Util.LinearInterpolate(sPreviousTopRight.y, goaltopright.y, sElapsedTime * 2)),
                            new Vector2D<Float>(Util.LinearInterpolate(sPreviousBottomLeft.x, goaltopleft.x, sElapsedTime * 2), Util.LinearInterpolate(sPreviousBottomLeft.x, goaltopleft.x, sElapsedTime * 2)));
                sElapsedTime += 0.016;
            } else if(sElapsedTime < 1.f) {
                Actor p = ObjectManager.GetObjectManager().GetPawn();

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

                Vector2D<Float> topright = new Vector2D<>(GetBounds().GetPosition().x + 1280.f / 2, GetBounds().GetPosition().y + 720.f / 2);

                Vector2D<Float> bottomleft = new Vector2D<>(GetBounds().GetPosition().x + GetBounds().GetWidth() - 1280.f / 2, GetBounds().GetPosition().y + GetBounds().GetHeight() - 760.f / 2);
                z.SetBounds(topright, bottomleft);
                GraphicsPipeline.GetGraphicsPipeline().RemoveRenderable(sPreviusLevel.mTilemap);
                sElapsedTime = 0.f;
            }
        }
    }

    void ShutDown() {}

    public Integer GetRightLevel() {
        return mRight;
    }

    public Integer GetLeftLevel() {
        return mLeft;
    }

    public Integer GetUpperLevel() {
        return mUpper;
    }

    public Integer GetLowerLevel() {
        return mLower;
    }

    public void SetRightLevel(Integer l) {
        mRight = l;
    }

    public void SetUpperLevel(Integer l) {
        mUpper = l;
    }

    public void SetLowerLevel(Integer l) {
        mLower = l;
    }

    public void SetLeftLevel(Integer l) {
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