package Gameplay.Enemies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

import Engine.ECSystem.ObjectManager;
import Engine.Graphics.Animation;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Graphics.Components.CameraComponent;
import Engine.Graphics.Components.Renderable;
import Engine.Graphics.Tile.*;
import Engine.Math.Vector2D;
import Engine.Physics.AABB;
import Engine.Physics.Components.BoxCollider;
import Gameplay.Enemies.Search.*;
import Gameplay.Link.DIRECTION;

public class Enemy extends Engine.ECSystem.Types.Actor implements Renderable{
    private final int UP = 0;
    private final int DOWN = 2;
    private final int RIGHT = 1;
    private final int LEFT = 3;

    //direction and normalized direction vector
    protected DIRECTION direction = DIRECTION.RIGHT;
    protected Vector2D normalizedDirection = new Vector2D(0f,0f);

    //player detected
    protected boolean chase = false;

    //Pathfinding variables
    protected Pair finalDestination;
    protected Pair lastFinalDestination= new Pair(0, 0);
    protected Pair currentDestination;
    protected Stack<Pair> path = new Stack<Pair>();

    //stats
    protected int healthPoints = 4;
    protected int damage = 1; //magic number, it has to be defined in a constructor
    protected float speed = 2;

    //components
    protected int mCurrentAnimation;
    protected AnimationMachine mAnimation;
    protected BoxCollider mCollision;

    //offsets of position
    protected int xoffset = 8;
    protected int yoffset = 32;
    
    
    // ------------------------------------------------------------------------
    /*! Conversion Constructor
    *
    *   Constructs an Enemy with a sprite, a position, and gives it a size
    */ //----------------------------------------------------------------------
    public Enemy(Spritesheet originalsprite, Vector2D<Float> position, Vector2D<Float> size) {

        super(position);
        SetScale(size);
        Spritesheet sprite=new Spritesheet("Content/Animations/gknight.png", 24,28);

        // TRANSPOSE SPRITE MATRIX
        sprite.setmSpriteArray(transposeMatrix(sprite.GetSpriteArray2D()));

        // ADD ANIMATION COMPONENT
        mAnimation = AddComponent(new AnimationMachine(this, sprite));
        SetScale(new Vector2D<Float>(size.x+25, size.y));a
        
        // ADD COLLIDER COMPONENT
        mCollision = (BoxCollider)AddComponent(new BoxCollider(this, new Vector2D<Float>(size.x*2, size.y*2)));
        SetAnimation(UP, sprite.GetSpriteArray(UP), 2);

        //Render path
        GraphicsPipeline.GetGraphicsPipeline().AddRenderable(this);
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
            if (vector.x < 0) {
                this.direction=DIRECTION.RIGHT;
            } else {
                this.direction=DIRECTION.LEFT;
            }
        } else {
            if (vector.y < 0) {
                this.direction=DIRECTION.DOWN;
            } else {
                this.direction=DIRECTION.UP;
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
            switch (direction){
                case UP:
                    if(mCurrentAnimation != UP || mAnimation.GetAnimation().GetDelay() == -1) {
                        SetAnimation(UP, mAnimation.GetSpriteSheet().GetSpriteArray(UP), 2);
                    }
                    break;
                case DOWN:
                    if(mCurrentAnimation != DOWN || mAnimation.GetAnimation().GetDelay() == -1) {
                        SetAnimation(DOWN, mAnimation.GetSpriteSheet().GetSpriteArray(DOWN), 2);
                    }
                    break;
                case LEFT:
                    if(mCurrentAnimation != RIGHT || mAnimation.GetAnimation().GetDelay() == -1) {
                        SetAnimation(RIGHT, mAnimation.GetSpriteSheet().GetSpriteArray(RIGHT), 2);
                    }
                    break;
                case RIGHT:
                    if(mCurrentAnimation != LEFT || mAnimation.GetAnimation().GetDelay() == -1) {
                        SetAnimation(LEFT, mAnimation.GetSpriteSheet().GetSpriteArray(LEFT), 2);
                    }
                    break;
            }
        }else{
            switch (direction){
                case UP:
                    if(mCurrentAnimation != UP || mAnimation.GetAnimation().GetDelay() == -1) {
                        SetAnimation(UP, mAnimation.GetSpriteSheet().GetSpriteArray(UP), 2);
                    }
                    break;
                case DOWN:
                    if(mCurrentAnimation != DOWN || mAnimation.GetAnimation().GetDelay() == -1) {
                        SetAnimation(DOWN, mAnimation.GetSpriteSheet().GetSpriteArray(DOWN), 2);
                    }
                    break;
                case LEFT:
                    if(mCurrentAnimation != RIGHT || mAnimation.GetAnimation().GetDelay() == -1) {
                        SetAnimation(RIGHT, mAnimation.GetSpriteSheet().GetSpriteArray(RIGHT), 2);
                    }
                    break;
                case RIGHT:
                    if(mCurrentAnimation != LEFT || mAnimation.GetAnimation().GetDelay() == -1) {
                        SetAnimation(LEFT, mAnimation.GetSpriteSheet().GetSpriteArray(LEFT), 2);
                    }
                    break;
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
        Vector2D<Float> playerPos = ObjectManager.GetObjectManager().GetObjectByName("Player").GetPosition();
        Pathfinding(playerPos);
        GetDirection(normalizedDirection);
        Move();
        Animate();
        mAnimation.GetAnimation().SetDelay(20);
        //System.out.println(playerPos.x + " " + playerPos.y + " " + normalizedDirection+ " " );
    }
    // ------------------------------------------------------------------------
    /*! Pathfinding
    *
    *   Checks if the Enemy can chase player
    */ //----------------------------------------------------------------------
    public void Pathfinding(Vector2D<Float> playerPos) { // Tile size is 64x64 and the player is yoffsetxyoffset
        int divisior = 64;
        Vector2D pos = GetPosition();
        Pair src = new Pair((int)Math.round(((float)pos.x + xoffset)/divisior), (int)Math.round(((float)pos.y+yoffset)/divisior));
        finalDestination = new Pair((int)Math.round((playerPos.x +xoffset)/divisior), (int)Math.round((playerPos.y +yoffset)/divisior));
        if(lastFinalDestination.getFirst() != finalDestination.getFirst() || lastFinalDestination.getSecond() != finalDestination.getSecond()){
            lastFinalDestination = finalDestination;
            path = AStarSearch.aStarSearch(src, finalDestination);
        }
    }

    // ------------------------------------------------------------------------
    /*! MovementVector
    *
    *   Calculates the movement of the Enemy
    */ //----------------------------------------------------------------------
    public void MovementVector(Vector2D<Float> playerPos) {
        Vector2D<Float> pos = GetPosition();
        Vector2D<Float> dir = new Vector2D<Float>((float)playerPos.x - (pos.x +xoffset), (float)playerPos.y - (pos.y +yoffset));
        normalizedDirection=Normalize(dir);
    }


    // ------------------------------------------------------------------------
    /*! Move
    *
    *   Receives the movements in a stack and sets the movement of the Enemy with the A* search
    */ //----------------------------------------------------------------------
    public void Move() {
        Vector2D<Float> pos = GetPosition();
        Vector2D<Float> playerPos = ObjectManager.GetObjectManager().GetObjectByName("Player").GetPosition();
        if(chase){
            speed = 3;
        }
        if(!path.isEmpty()){
            if(!path.isEmpty()){
                currentDestination = path.peek();
            }
            // Margin of error for the movement
            float xlowerBound = currentDestination.getFirst()*64 - 3;
            float xupperBound = currentDestination.getFirst()*64 + 3;
            float ylowerBound = currentDestination.getSecond()*64 - 3;
            float yupperBound = currentDestination.getSecond()*64 + 3;

            //If currentDestination reached, pop next destination
            if(((((xlowerBound <= pos.x+xoffset) && (pos.x+xoffset <= xupperBound)) && ((ylowerBound <= pos.y+yoffset) && (pos.y+yoffset <= yupperBound)))) && (currentDestination != finalDestination) && !path.isEmpty()){
                path.pop();
                if(!path.isEmpty()){
                    currentDestination = path.peek();
                }
                normalizedDirection = Normalize(new Vector2D<Float>((float)currentDestination.getFirst()*64 - (pos.x+xoffset), (float)currentDestination.getSecond()*64 - (pos.y+yoffset)));
                pos.x += (float)normalizedDirection.x * speed;
                pos.y += (float)normalizedDirection.y * speed;
            }else{
                normalizedDirection = Normalize(new Vector2D<Float>((float)currentDestination.getFirst()*64 - (pos.x+xoffset), (float)currentDestination.getSecond()*64 - (pos.y+yoffset)));
                pos.x += (float)normalizedDirection.x * speed;
                pos.y += (float)normalizedDirection.y * speed;
            }
        //If finalDestination reached, chase player directly
        }else if((int)currentDestination.getFirst() == (int)finalDestination.getFirst() && (int)currentDestination.getSecond() == (int)finalDestination.getSecond()){
                MovementVector(playerPos);
                pos.x += (float)normalizedDirection.x * speed;
                pos.y += (float)normalizedDirection.y * speed;
        }

        SetPosition(pos);
    } 
    
    public void KnockBack(Vector2D<Float> playerPos) {
        Vector2D<Float> pos = GetPosition();
        Vector2D<Float> dir = pos.getVectorToAnotherActor(playerPos);
        normalizedDirection=Normalize(dir);
        pos.x -= (float)normalizedDirection.x * 60;
        pos.y -= (float)normalizedDirection.y * 60;
        SetPosition(pos);
    }
    
    public int getDamage() {
        return damage;
    }  

    public void setHealthPoints(int damage){
        this.healthPoints -= damage;
        if (healthPoints <= 0){
            mCollision.ShutDown();
            this.SetScale(new Vector2D<Float>(0f,0f));
            ObjectManager.GetObjectManager().RemoveEntity(this);
            path.clear();
        }
        //______________________
        //______________________
    }
    @Override
    public void Render(Graphics2D g, CameraComponent camerapos) {
        var camcoord = camerapos.GetCoordinates();
        g.setColor(Color.green);
        Stack<Pair> mPath = (Stack<Pair>)path.clone();

        while (!mPath.isEmpty()) {
            Pair p = mPath.pop();
            g.drawRect(p.getFirst() * 64 - (int)(float)camcoord.x, p.getSecond() * 64 - (int)(float)camcoord.y, 64, 64);
        }
    }
}
