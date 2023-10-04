package Gameplay.Enemies;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
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
import Gameplay.Enemies.Search.*;
import Gameplay.LifeBar.LifeBar;
import Gameplay.Link.DirectionObject;

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
    //public DIRECTION direction;
    protected DirectionObject direction = new DirectionObject(up, left, right, down);

    //player detected
    protected boolean chase = false;

    //Pathfinding variables
    protected Pair finalDestination;
    protected Pair currentDestination;
    protected Stack<Pair> path;
    protected AStarSearch Pathfinding = new AStarSearch();



    //stats
    protected int healthPoints = 4;
    protected int damage = 1; //magic number, it has to be defined in a constructor

    protected float speed = 1;
    protected Vector2D ndir = new Vector2D(0f,0f);

    //components
    protected int mCurrentAnimation;
    protected AnimationMachine mAnimation;
    protected BoxCollider mCollision;
    
    //ID
    static int idx = 0;
    
    LifeBar lifeBar;
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
        mCollision = (BoxCollider)AddComponent(new BoxCollider(this, new Vector2D<Float>(100f, 200f)));
        SetAnimation(UP, sprite.GetSpriteArray(UP), 2);
        SetName("Enemy " + idx);
        idx++;

        lifeBar = new LifeBar(this, healthPoints);
        lifeBar.setVisible(true);
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
    public void GetDirection(Vector2D<Float> vector) { // hay que cambiar esto por direction

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
        direction = new DirectionObject(up, left, right, down);
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
        //System.out.println("pdsofhiusf");
        super.Update();
        Vector2D<Float> ppos = ObjectManager.GetObjectManager().GetObjectByName("Player").GetPosition();
        GetDirection(ndir);
        Pathfinding(ppos);
        Move();
        Animate();
        mAnimation.GetAnimation().SetDelay(20);
        //System.out.println(ppos.x + " " + ppos.y + " " + ndir+ " " );
        lifeBar.Update();
    }
    // ------------------------------------------------------------------------
    /*! Pathfinding
    *
    *   Checks if the Enemy can chase player
    */ //----------------------------------------------------------------------
    public void Pathfinding(Vector2D<Float> playerPos) { //Maagic numbers to change the start and ending of the pathfinding to the center of the enemy and the center of the player
        int divisior = 64;
        Vector2D pos = GetPosition();
        Pair src = new Pair((int)Math.round(((float)pos.x + 16)/divisior), (int)Math.round(((float)pos.y+32)/divisior));
        finalDestination = new Pair((int)Math.round((playerPos.x +16)/divisior), (int)Math.round((playerPos.y +16)/divisior));
        path = Pathfinding.aStarSearch(src, finalDestination); 

    }

    // ------------------------------------------------------------------------
    /*! MovementVector
    *
    *   Calculates the movement of the Enemy
    */ //----------------------------------------------------------------------
    public void MovementVector(Vector2D<Float> playerPos) {
        Vector2D<Float> pos = GetPosition();
        Vector2D<Float> dir = new Vector2D<Float>((float)playerPos.x - (pos.x +16), (float)playerPos.y - (pos.y +16));
        ndir=Normalize(dir);
    }


    // ------------------------------------------------------------------------
    /*! Move
    *
    *   Receives the movements in a stack and sets the movement of the Enemy with the A* search
    */ //----------------------------------------------------------------------
    public void Move() {
        int posoffset =20;
        Vector2D<Float> pos = GetPosition();
        Vector2D<Float> ppos = ObjectManager.GetObjectManager().GetObjectByName("Player").GetPosition();
        if(chase){
            speed = 3;
        }
        if(!path.isEmpty()){
            path.pop();
            if(!path.isEmpty()){
                currentDestination = path.peek();
            }
            // Margin of error for the movement
            float xlowerBound = currentDestination.getFirst()*64 - 3;
            float xupperBound = currentDestination.getFirst()*64 + 3;
            float ylowerBound = currentDestination.getSecond()*64 - 3;
            float yupperBound = currentDestination.getSecond()*64 + 3;

            //If currentDestination reached, pop next destination
            if(((((xlowerBound <= pos.x+posoffset) && (pos.x+posoffset <= xupperBound)) && ((ylowerBound <= pos.y+posoffset) && (pos.y+posoffset <= yupperBound)))) && (currentDestination != finalDestination) && !path.isEmpty()){
                path.pop();
                if(!path.isEmpty()){
                    currentDestination = path.peek();
                }
                ndir = Normalize(new Vector2D<Float>((float)currentDestination.getFirst()*64 - (pos.x+posoffset), (float)currentDestination.getSecond()*64 - (pos.y+posoffset)));
                pos.x += (float)ndir.x * speed;
                pos.y += (float)ndir.y * speed;
            }else{
                ndir = Normalize(new Vector2D<Float>((float)currentDestination.getFirst()*64 - (pos.x+posoffset), (float)currentDestination.getSecond()*64 - (pos.y+posoffset)));
                pos.x += (float)ndir.x * speed;
                pos.y += (float)ndir.y * speed;
            }
        //If finalDestination reached, chase player directly
        }else if((int)currentDestination.getFirst() == (int)finalDestination.getFirst() && (int)currentDestination.getSecond() == (int)finalDestination.getSecond()){
                MovementVector(ppos);
                pos.x += (float)ndir.x * speed;
                pos.y += (float)ndir.y * speed;
        }

        SetPosition(pos);
    } 
    
    public void KnockBack(Vector2D<Float> playerPos) {
        Vector2D<Float> pos = GetPosition();
        Vector2D<Float> dir = pos.getVectorToAnotherActor(playerPos);
        ndir=Normalize(dir);
        pos.x -= (float)ndir.x * 60;
        pos.y -= (float)ndir.y * 60;
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
    
    public int getDamage() {
        return damage;
    }  

    public void setHealthPoints(int damage){
        this.healthPoints -= damage;
        if (healthPoints <= 0){
            //System.out.println("aibfhdp`");
            mCollision.ShutDown();
            //this.SetScale(new Vector2D<Float>(0f,0f));
            ObjectManager.GetObjectManager().RemoveEntity(this);
            path.clear();
        }
        //lifeBar.setHealthPoints(this.healthPoints);
        //______________________
        //______________________
    }
}
