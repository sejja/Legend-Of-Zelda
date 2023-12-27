package Engine.ECSystem;

import java.sql.ResultSet;
import java.sql.SQLException;

import Engine.Developer.DataBase.Database;
import Engine.ECSystem.Types.Actor;
import Engine.ECSystem.Types.Entity;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Components.ZeldaCameraComponent;
import Engine.Graphics.Tile.ShadowLayer;
import Engine.Graphics.Tile.TileManager;
import Engine.Graphics.Tile.TilemapEntities;
import Engine.Math.Util;
import Engine.Math.Vector2D;
import Engine.Physics.AABB;
import Engine.Window.GameLoop;
import Gameplay.AnimatedObject.Torch;
import Gameplay.AnimatedObject.Water;
import Gameplay.Enemies.Search.Pair;
import Gameplay.Enemies.Units.BlueKnight;
import Gameplay.Enemies.Units.GreenKnight;
import Gameplay.Interactives.Blocks.Rock;

public class World {
    private Integer mRight;
    private Integer mLeft;
    private Integer mUpper;
    private Integer mLower;
    public TileManager mTilemap;
    static public World mCurrentLevel = null;
    static private boolean sTransitioning = false;
    static private Vector2D<Integer> sTransitionDir = new Vector2D<Integer>();
    static private float sElapsedTime = 0;
    static private World sPreviusLevel;
    private boolean visited = false;

    public static void Reset() {
        mCurrentLevel = null;
        sTransitioning = false;
        sElapsedTime = 0;
    }

    private String getLevel(int id){
        ResultSet res = Database.Instance().Query("select * from Levels where id = " + id);
        String map = "";
        try {
            map = res.getString("Path");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         return map;
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
            System.out.println(tiles);
            try {
                left = Integer.parseInt(s.getString("Left"));
                System.out.println("Left:" + left);
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
                System.out.println("Down:"+  down);
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
    public void WorldInfo(){
        System.out.println("Path:" + mTilemap.getPath() + " Right:" +mRight +" Left:" + mLeft + " Up:" + mUpper + " Down:" + mLower);
    }

    public AABB GetBounds() {
        return mTilemap.GetBounds();
    }

    public void Init(Vector2D<Float> position) {
        mCurrentLevel = this;
        mTilemap.CreateTileMap(position, new Vector2D<>(64, 64));
        try {
            ObjectManager.GetObjectManager().flush();
        }catch(NullPointerException e){
            //System.out.println("No hay nada que borrar MAMARRACHO");
        }
        
        spawnEntities();
    }

    public static Vector2D<Float> GetWorldSpaceCoordinates(Vector2D<Float> levelcoordinate) {
        Vector2D<Float> finalpos = new Vector2D<Float>(levelcoordinate.x,levelcoordinate.y);
        finalpos.x += mCurrentLevel.GetBounds().GetPosition().x;
        finalpos.y += mCurrentLevel.GetBounds().GetPosition().y;
        return finalpos;
    }

    public static Vector2D<Float> GetLevelSpaceCoordinates(Vector2D<Float> worldcoordinate) { // UwU
        Vector2D<Float> finalpos = new Vector2D<Float>(worldcoordinate.x,worldcoordinate.y);
        finalpos.x -= mCurrentLevel.GetBounds().GetPosition().x;
        finalpos.y -= mCurrentLevel.GetBounds().GetPosition().y;
        return finalpos;
    }
    public static Vector2D<Integer> GetLevelSpaceIntegerCoordinates(Vector2D<Integer> worldcoordinate) {
        Vector2D<Integer> finalpos = new Vector2D<Integer>(worldcoordinate.x,worldcoordinate.y);
        finalpos.x -= (int)(float)mCurrentLevel.GetBounds().GetPosition().x/64;
        finalpos.y -= (int)(float)mCurrentLevel.GetBounds().GetPosition().y/64;
        return finalpos;
    }

    public static Pair GetLevelPair(Pair pair) {
        int x= pair.getFirst();
        int y= pair.getSecond();
        x -= mCurrentLevel.GetBounds().GetPosition().x/64;
        y -= mCurrentLevel.GetBounds().GetPosition().y/64;
        return new Pair(x,y);
    }
    public static Pair GetWorldPair(Pair pair) {
        int x= pair.getFirst();
        int y= pair.getSecond();
        x += mCurrentLevel.GetBounds().GetPosition().x/64;
        y += mCurrentLevel.GetBounds().GetPosition().y/64;
        return new Pair(x,y);
    }

    /**
    * Spawns the entities of entityQueue based on the ID and in the position specified
    */ 
    private void spawnEntities() {
        //System.out.println(mTilemap.entityQueue);
        int firstEntity = TilemapEntities.firstEntity;
        while(mTilemap.entityQueue != null && !mTilemap.entityQueue.isEmpty() ) {
            var e = mTilemap.entityQueue.poll();
            //System.out.println(e.type+"  "+ firstEntity);
            if(e.type == firstEntity) {
                SpawnEntity(new GreenKnight(e.position));
            }else if(e.type == firstEntity+1) {
                SpawnEntity(new Rock(e.position));
            }else if(e.type == firstEntity+2) {
                SpawnEntity(new Torch(e.position));
            }else if(e.type == firstEntity+3) {
                SpawnEntity(new BlueKnight(e.position));
            }else if(e.type == firstEntity+4) {
                SpawnEntity(new Water(e.position));
            }
            
        }
    }

    public void Update() {
        if(!sTransitioning) {
            Actor p = ObjectManager.GetObjectManager().GetPawn();
            var position = p.GetPosition();
            sPreviusLevel = this;

            if(!GetBounds().Collides(new AABB(position, new Vector2D<>(1.f, 1.f)))) {
                //RIGHT
                if(position.x > (GetBounds().GetPosition().x + GetBounds().GetWidth()) && mRight != null) {
                    World v = new World(mRight);
                    v.Init(new Vector2D<>(GetBounds().GetPosition().x + GetBounds().GetWidth(), GetBounds().GetPosition().y));
                    sTransitioning = true;
                    var z = (ZeldaCameraComponent) GraphicsPipeline.GetGraphicsPipeline().GetBindedCamera();
                    sTransitionDir.x = 1;
                    sTransitionDir.y = 0;
                }

                //LEFT
                if(position.x < (GetBounds().GetPosition().x) && mLeft != null) {
                    World v = new World(mLeft);
                    v.Init(new Vector2D<>(GetBounds().GetPosition().x - 50 * 64, GetBounds().GetPosition().y));
                    sTransitioning = true;
                    var z = (ZeldaCameraComponent) GraphicsPipeline.GetGraphicsPipeline().GetBindedCamera();
                    sTransitionDir.x = -1;
                    sTransitionDir.y = 0;

                    /* 
                    World v = new World(mLeft);
                    var b = v.mTilemap.EstimateBounds(new Vector2D<>(64, 64));
                    var pos = GetBounds().GetPosition();
                    Vector2D<Float> scale = new Vector2D<>(b.GetWidth(), b.GetHeight());
                    pos.x -= scale.x;

                    v.Init(pos);
                    sTransitioning = true;
                    var z = (ZeldaCameraComponent) GraphicsPipeline.GetGraphicsPipeline().GetBindedCamera();
                    sPreviousTopRight = z.GetTopRightBound();
                    sPreviousBottomLeft = z.GetBottomLeftBound(); */
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
                    sTransitionDir.x = 0;
                    sTransitionDir.y = -1;
                }

                //DOWN
                if(position.y > (GetBounds().GetPosition().y + GetBounds().GetHeight()) && mLower != null) {
                    World v = new World(mLower);
                    v.Init(new Vector2D<>(GetBounds().GetPosition().x, GetBounds().GetPosition().y + GetBounds().GetHeight()));
                    sTransitioning = true;
                    var z = (ZeldaCameraComponent) GraphicsPipeline.GetGraphicsPipeline().GetBindedCamera();
                    sTransitionDir.x = 0;
                    sTransitionDir.y = 1;
                }
            }
        } else {
            var z = (ZeldaCameraComponent) GraphicsPipeline.GetGraphicsPipeline().GetBindedCamera();
            z.Update();

            if(sElapsedTime < 1) {
                GameLoop.SetPaused(true);
                Actor p = ObjectManager.GetObjectManager().GetPawn(); //<--- Its Link

                z.SetPanning(new Vector2D<Float>(sTransitionDir.x * sElapsedTime * 20.f * 64.f,
                sTransitionDir.y * sElapsedTime * 20.f * 64.f));
                
                sElapsedTime += 0.016;
            } else { //END TRANSITION
                GameLoop.SetPaused(false);
                sTransitioning = false;

                Vector2D<Float> topright = new Vector2D<>(GetBounds().GetPosition().x + 1280.f / 2, GetBounds().GetPosition().y + 720.f / 2);

                Vector2D<Float> bottomleft = new Vector2D<>(GetBounds().GetPosition().x + GetBounds().GetWidth() - 1280.f / 2, GetBounds().GetPosition().y + GetBounds().GetHeight() - 760.f / 2);
                z.SetBounds(topright, bottomleft);
                GraphicsPipeline.GetGraphicsPipeline().RemoveRenderable(sPreviusLevel.mTilemap);
                sElapsedTime = 0.f;
                z.SetPanning(new Vector2D<Float>(0.f, 0.f));
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