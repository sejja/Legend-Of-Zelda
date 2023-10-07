package Gameplay.Enemies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Stack;
import Engine.ECSystem.ObjectManager;
import Engine.Graphics.Animation;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Graphics.Components.CameraComponent;
import Engine.Graphics.Components.Renderable;
import Engine.Math.Vector2D;
import Engine.Physics.Components.BoxCollider;
import Gameplay.Enemies.Search.*;
import Gameplay.LifeBar.LifeBar;
import Gameplay.Link.DirectionObject;
import Gameplay.Link.DIRECTION;

public abstract class Enemy extends Engine.ECSystem.Types.Actor implements Renderable{
    protected final int UP = 0;
    protected final int DOWN = 2;
    protected final int RIGHT = 1;
    protected final int LEFT = 3;

    //direction and normalized direction vector
    protected DIRECTION direction = DIRECTION.RIGHT;
    protected Vector2D<Float> normalizedDirection = new Vector2D(0f,0f);

    //player detected
    protected boolean chase = false;

    //A* search
    protected static AStarSearch aStarSearch = new AStarSearch();

    //Pathfinding variables
    protected Pair finalDestination;
    protected Pair lastFinalDestination= new Pair(0, 0);
    protected Pair currentDestination;
    protected Stack<Pair> path = new Stack<Pair>();
    protected Vector2D<Float> pos = GetPosition();
    protected Vector2D<Float> playerPos = ObjectManager.GetObjectManager().GetObjectByName("Player").GetPosition();
    protected float xlowerBound;
    protected float xupperBound;
    protected float ylowerBound;
    protected float yupperBound;

    //stats
    protected int healthPoints = 1;
    protected int damage = 1;
    protected float speed = 1;

    //components
    protected int mCurrentAnimation;
    protected AnimationMachine mAnimation;
    protected BoxCollider mCollision;

    //offsets of position
    protected int xoffset = 0;
    protected int yoffset = 0;

    
    
    LifeBar lifeBar;
    // ------------------------------------------------------------------------
    /*! Conversion Constructor
    *
    *   Constructs an Enemy with a sprite, a position, and gives it a size
    */ //----------------------------------------------------------------------
    public Enemy( Vector2D<Float> position) {
        super(position);
        //Render path (add to pipeline)
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
        //System.out.println("pdsofhiusf");
        super.Update();
        Pathfinding();
        GetDirection(normalizedDirection);
        Animate();
        Move();
        mAnimation.GetAnimation().SetDelay(20);
        //System.out.println(playerPos.x + " " + playerPos.y + " " + normalizedDirection+ " " );
    }

    // ------------------------------------------------------------------------
    /*! Pathfinding
    *
    *   Calculates the path to the player if the path is unblocked and is not the same as the last time A* was called
    */ //----------------------------------------------------------------------
    public void Pathfinding() {
        Pair enemyTile = PositionToPair(pos);
        finalDestination = PositionToPair(playerPos);
        if(isDestinationChanged() && isDestinationReachable()){
            lastFinalDestination = finalDestination;
            path = aStarSearch.aStarSearch(enemyTile, finalDestination);
        }
    }

    // ------------------------------------------------------------------------
    /*! PositionToPair
    *
    *   Changes the position given to a Pair
    */ //----------------------------------------------------------------------
    public Pair PositionToPair(Vector2D<Float> position) {
        int divisior = 64;
        Pair pair = new Pair(Math.round((position.x +xoffset)/divisior), Math.round((position.y +yoffset)/divisior));
        return pair;
    }



    // ------------------------------------------------------------------------
    /*! isDestinationChanged
    *
    *   Checks if the destination (Player) has changed of Tile
    */ //----------------------------------------------------------------------
    public boolean isDestinationChanged() {
        if(lastFinalDestination.getFirst() != finalDestination.getFirst() || lastFinalDestination.getSecond() != finalDestination.getSecond()){
            return true;
        }else{
            return false;
        }
    }

    // ------------------------------------------------------------------------
    /*! isDestinationReachable
    *
    *   Checks if the destination is reachable by the Enemy
    */ //----------------------------------------------------------------------
    public boolean isDestinationReachable() {
        if(aStarSearch.isUnBlocked(finalDestination.getFirst(), finalDestination.getSecond())){
            return true;
        }else{
            return false;
        }
    }



    // ------------------------------------------------------------------------
    /*! MovementVector
    *
    *   Calculates the movement of the Enemy
    */ //----------------------------------------------------------------------
    public void MovementVector() {
        Vector2D<Float> dir = new Vector2D<Float>(playerPos.x - (pos.x +xoffset), playerPos.y - (pos.y +yoffset));
        normalizedDirection=Normalize(dir);
    }


    // ------------------------------------------------------------------------
    /*! Move
    *
    *   Receives the movements in a stack and sets the movement of the Enemy with the A* search
    */ //----------------------------------------------------------------------
    public void Move() {
        if(chase){
            speed = 3;
        }
        if(!path.isEmpty()){
            if(!path.isEmpty()){
                currentDestination = path.peek();
            }
            // Margin of error for the movement
            xlowerBound = currentDestination.getFirst()*64 - 3;
            xupperBound = currentDestination.getFirst()*64 + 3;
            ylowerBound = currentDestination.getSecond()*64 - 3;
            yupperBound = currentDestination.getSecond()*64 + 3;

            //If currentDestination reached, pop next destination
            if(((((xlowerBound <= pos.x+xoffset) && (pos.x+xoffset <= xupperBound)) && ((ylowerBound <= pos.y+yoffset) && (pos.y+yoffset <= yupperBound)))) && (currentDestination != finalDestination) && !path.isEmpty()){
                path.pop();
                if(!path.isEmpty()){
                    currentDestination = path.peek();
                }
                normalizedDirection = Normalize(new Vector2D<Float>(currentDestination.getFirst()*64 - (pos.x+xoffset), currentDestination.getSecond()*64 - (pos.y+yoffset)));
                pos.x += normalizedDirection.x * speed;
                pos.y += normalizedDirection.y * speed;
            }else{
                normalizedDirection = Normalize(new Vector2D<Float>(currentDestination.getFirst()*64 - (pos.x+xoffset), currentDestination.getSecond()*64 - (pos.y+yoffset)));
                pos.x += normalizedDirection.x * speed;
                pos.y += normalizedDirection.y * speed;
            }
        //If finalDestination reached, chase player directly
        }else if(currentDestination.getFirst() == finalDestination.getFirst() && currentDestination.getSecond() == finalDestination.getSecond()){
                MovementVector();
                pos.x += normalizedDirection.x * speed;
                pos.y += normalizedDirection.y * speed;
        }

        SetPosition(pos);
    } 
    
    public void KnockBack() {
        Vector2D<Float> dir = pos.getVectorToAnotherActor(playerPos);
        normalizedDirection=Normalize(dir);
        pos.x -= normalizedDirection.x * 60;
        pos.y -= normalizedDirection.y * 60;
        SetPosition(pos);
    }
    

    public void setHealthPoints(int damage){
        this.healthPoints -= damage;
        if (healthPoints <= 0){
            //System.out.println("aibfhdp`");
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

    // Getters and Setters
    public void setOffset(int x, int y){
        xoffset = x;
        yoffset = y;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setHp(int hp){
        this.healthPoints = hp;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public float getSpeed() {
        return speed;
    }

    public int getDamage() {
        return damage;
    }
    public Enemy getEnemy(){
        return (Enemy)this;
    }
    
    
}
