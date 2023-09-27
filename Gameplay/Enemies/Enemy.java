package Gameplay.Enemies;

import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

import Engine.ECSystem.ObjectManager;
import Engine.Graphics.Animation;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Graphics.Tile.*;
import Engine.Math.Vector2D;
import Engine.Physics.AABB;
import Engine.Physics.Components.BoxCollider;
import Gameplay.Player;
import Gameplay.Enemies.Search.*;

public class Enemy extends Engine.ECSystem.Types.Actor {
    private final int UP = 0;
    private final int DOWN = 2;
    private final int RIGHT = 1;
    private final int LEFT = 3;

    //directions
    protected boolean up = true;
    protected boolean down = false;
    protected boolean right = false;
    protected boolean left = false;

    //player detected
    protected boolean chase = false;

    //stats
    protected AtomicInteger healthPoints = new AtomicInteger(2);
    float speed = 2;
    protected Vector2D ndir = new Vector2D(0f,0f);
    
    protected int mCurrentAnimation;
    protected AnimationMachine mAnimation;
    protected BoxCollider mCollision;
    protected AStarSearch mPathfinding;
    
    static int idx = 0;
    
    // ------------------------------------------------------------------------
    /*! Conversion Constructor
    *
    *   Constructs an Enemy with a sprite, a position, and gives it a size
    */ //----------------------------------------------------------------------
    public Enemy(Spritesheet originalsprite, Vector2D<Float> position, Vector2D<Float> size) {
        super(position);
        SetScale(size);
        Spritesheet sprite=new Spritesheet(originalsprite, "Content/Animations/gknight.png");
        // TRANSPOSE SPRITE MATRIX
        sprite.setmSpriteArray(transposeMatrix(sprite.GetSpriteArray2D()));
        // ADD ANIMATION COMPONENT
        mAnimation = AddComponent(new AnimationMachine(this, sprite));
        // ADD COLLIDER COMPONENT
        mCollision = (BoxCollider)AddComponent(new BoxCollider(this, new Vector2D<Float>(100.f,200.f)));

        SetAnimation(UP, sprite.GetSpriteArray(UP), 2);
        SetName("Enemy " + idx);
        idx++;
    }

    public void SetAnimation(int i, BufferedImage[] frames, int delay) {
        mCurrentAnimation = i;
        mAnimation.GetAnimation().SetFrames(frames);
        mAnimation.GetAnimation().SetDelay(delay);
    }

    public Animation GetAnimation() {
        return mAnimation.GetAnimation();
    }

    // ------------------------------------------------------------------------
    /*! transposeMatrix
    *
    *   Utility Function for Transposing a Matrix
    */ //----------------------------------------------------------------------
    private BufferedImage[][] transposeMatrix(BufferedImage [][] m){
        BufferedImage[][] temp = new BufferedImage[m[0].length + 4][m.length];
        for (int i = 0; i < m.length; i++){
            for (int j = 0; j < m[0].length; j++){
                temp[j][i] = m[i][j];
            }
        }
        for (int i = 0; i < 4; i++){
            for (int j = 0;  j < temp[0].length; j++){
                temp[m[0].length+i][j] = temp[i][0];
            }
        }
        return temp;
    }

    // ------------------------------------------------------------------------
    /*! Normalize
    *
    *   Utility Function for Normalizing a Vector
    */ //----------------------------------------------------------------------
    public Vector2D<Float> Normalize(Vector2D<Float> vector) {
        float magnitude = (float) Math.sqrt(vector.x * vector.x + vector.y * vector.y);
        if (magnitude > 0) {
            return new Vector2D<Float>(vector.x / magnitude, vector.y / magnitude);
        } else {
            return new Vector2D<Float>(0f, 0f);
        }
    }

    // ------------------------------------------------------------------------
    /*! GetDirection
    *
    *   Utility Function for Getting the Direction of a Vector
    */ //----------------------------------------------------------------------
    public void GetDirection(Vector2D<Float> vector) {

        if (Math.abs(vector.x) > Math.abs(vector.y)) {
            if (vector.x > 0) {
                setUp(false);
                setDown(false);
                setLeft(false);
                setRight(true);
            } else {
                setUp(false);
                setDown(false);
                setLeft(true);
                setRight(false);
            }
        } else {
            if (vector.y < 0) {
                setUp(false);
                setDown(true);
                setLeft(false);
                setRight(false);
            } else {
                setUp(true);
                setDown(false);
                setLeft(false);
                setRight(false);
            }
        }
    }

    // ------------------------------------------------------------------------
    /*! Animate
    *
    *   Adds the needed animation to the Enemy
    */ //----------------------------------------------------------------------
    public void Animate() {
        if(chase){
            if(up) {
                if(mCurrentAnimation != UP || mAnimation.GetAnimation().GetDelay() == -1) {
                    SetAnimation(UP, mAnimation.GetSpriteSheet().GetSpriteArray(UP), 2);
                }
            } else if(down) {
                if(mCurrentAnimation != DOWN || mAnimation.GetAnimation().GetDelay() == -1) {
                    SetAnimation(DOWN, mAnimation.GetSpriteSheet().GetSpriteArray(DOWN), 2);
                }
            } else if(right) {
                if(mCurrentAnimation != RIGHT || mAnimation.GetAnimation().GetDelay() == -1) {
                    SetAnimation(RIGHT, mAnimation.GetSpriteSheet().GetSpriteArray(RIGHT), 2);
                }
            } else {
                if(mCurrentAnimation != LEFT || mAnimation.GetAnimation().GetDelay() == -1) {
                    SetAnimation(LEFT, mAnimation.GetSpriteSheet().GetSpriteArray(LEFT), 2);
                }
            }
        }else{
            if(up) {
                if(mCurrentAnimation != UP || mAnimation.GetAnimation().GetDelay() == -1) {
                    SetAnimation(UP, mAnimation.GetSpriteSheet().GetSpriteArray(UP), 2);
                }
            } else if(down) {
                if(mCurrentAnimation != DOWN || mAnimation.GetAnimation().GetDelay() == -1) {
                   SetAnimation(DOWN, mAnimation.GetSpriteSheet().GetSpriteArray(DOWN), 2);
                }
            } else if(right) {
                if(mCurrentAnimation != RIGHT || mAnimation.GetAnimation().GetDelay() == -1) {
                    SetAnimation(RIGHT, mAnimation.GetSpriteSheet().GetSpriteArray(RIGHT), 2);
                }
            } else {
                if(mCurrentAnimation != LEFT || mAnimation.GetAnimation().GetDelay() == -1) {
                    SetAnimation(LEFT, mAnimation.GetSpriteSheet().GetSpriteArray(LEFT), 2);
                }
            }
        }
    }

    // ------------------------------------------------------------------------
    /*! Update
    *
    *   Adds Behavior to the Enemy
    */ //----------------------------------------------------------------------
    public void Update() {
        super.Update();
        Vector2D<Float> ppos = ObjectManager.GetObjectManager().GetObjectByName("Player").GetPosition();
        CalculateMovement(ppos);
        GetDirection(ndir);
        Vision(ppos);
        Move();
        Animate();
        mAnimation.GetAnimation().SetDelay(20);
        System.out.println(ppos.x + " " + ppos.y + " " + ndir+ " " );
    }
    // ------------------------------------------------------------------------
    /*! Vision
    *
    *   Checks if the Enemy can chase player
    */ //----------------------------------------------------------------------
    public void Vision(Vector2D<Float> playerPos) {
        mPathfinding = new AStarSearch();
        int divisior = 64;
        Block srcBlock = TilemapObject.GetBlockAt((int)Math.round(this.GetPosition().x/divisior), (int)Math.round(this.GetPosition().y)/divisior);
        Block destBlock = TilemapObject.GetBlockAt((int)Math.round(playerPos.x)/divisior, (int)Math.round(playerPos.y)/divisior);
        Pair src = new Pair((int)Math.round(this.GetPosition().x/divisior), (int)Math.round(this.GetPosition().y)/divisior);
        Pair dest = new Pair((int)Math.round(playerPos.x)/divisior, (int)Math.round(playerPos.y)/divisior);
        mPathfinding.aStarSearch(src, dest);



    }

    // ------------------------------------------------------------------------
    /*! CalculateMovement
    *
    *   Calculates the movement of the Enemy
    */ //----------------------------------------------------------------------
    public void CalculateMovement(Vector2D<Float> playerPos) {
        Vector2D<Float> pos = GetPosition();
        Vector2D<Float> dir = new Vector2D<Float>((float)playerPos.x - pos.x, (float)playerPos.y - pos.y);
        ndir=Normalize(dir);
    }

    // ------------------------------------------------------------------------
    /*! Move
    *
    *   Moves the sprite on a certain direction
    */ //----------------------------------------------------------------------
    public void Move() {
        Vector2D<Float> pos = GetPosition();
        if(chase){
            speed = 3;
        }
        pos.x += (float)ndir.x * speed;
        pos.y += (float)ndir.y * speed;

        SetPosition(pos);
    }  
    
    private void setRight(boolean b) {
        this.right = b;
    }

    private void setLeft(boolean b) {
        this.left = b;
    }

    private void setDown(boolean b) {
        this.down = b;
    }

    private void setUp(boolean b) {
        this.up = b;
    }  
}
