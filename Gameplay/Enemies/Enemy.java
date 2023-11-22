package Gameplay.Enemies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.beans.VetoableChangeListenerProxy;
import java.util.Stack;
import java.util.Vector;
import Engine.Assets.AssetManager;
import Engine.Audio.Audio;
import Engine.Audio.Sound;
import Engine.Developer.Logger.Log;
import Engine.Developer.Logger.Logger;
import java.util.logging.Level;
import Engine.ECSystem.World;
import Engine.ECSystem.ObjectManager;
import Engine.ECSystem.Types.Actor;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Animations.Animation;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Graphics.Components.CameraComponent;
import Engine.Graphics.Components.Renderable;
import Engine.Math.Vector2D;
import Engine.Physics.Components.BoxCollider;
import Engine.Physics.Components.ColliderManager;
import Gameplay.AnimatedObject.DeadAnimation;
import Gameplay.Enemies.Search.*;
import Gameplay.LifeBar.LifeBar;
import Gameplay.Link.DirectionObject;
import Gameplay.Link.Arrow;
import Gameplay.Link.DIRECTION;
import Gameplay.Link.Player;

public abstract class Enemy extends Engine.ECSystem.Types.Actor implements Renderable{

    protected final int UP = 0;
    protected final int DOWN = 2;
    protected final int RIGHT = 1;
    protected final int LEFT = 3;

    //direction and normalized direction vector
    protected DIRECTION direction = DIRECTION.RIGHT;
    protected Vector2D<Float> normalizedDirection = new Vector2D<Float>(0f,0f);

    //player detected
    protected boolean chase = false;
    protected boolean knockback=false;
    protected int knockbackCounter=0;

    //A* search
    protected static AStarSearch aStarSearch = new AStarSearch();

    //pathfinding variables
    protected Pair finalDestination= new Pair(0, 0);
    protected Pair lastFinalDestination= new Pair(0, 0);
    protected Pair currentDestination= new Pair(0, 0);
    protected Stack<Pair> path = new Stack<Pair>();
    protected Vector2D<Float> pos = GetPosition();
    protected Vector2D<Float> pseudoPos = getPseudoPosition();
    protected Player player = (Player) ObjectManager.GetObjectManager().GetObjectByName(Player.class, "Player");
    protected Vector2D<Float> playerPos = player.getPseudoPosition();
    protected Vector2D<Float> lowerBounds;
    protected Vector2D<Float> upperBounds;

    //stats
    protected int healthPoints = 1;
    protected int damage = 1;
    protected float speed = 1;

    //components
    protected int mCurrentAnimation;
    protected AnimationMachine mAnimation;
    protected BoxCollider mCollision;

    private int delay = 10;
    
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
        mAnimation.GetAnimation().SetDelay(this.delay);
    }

    public Animation GetAnimation() {
        return mAnimation.GetAnimation();
    }

    // ------------------------------------------------------------------------
    /*! normalize
    *
    *   Utility Function for Normalizing a Vector
    */ //----------------------------------------------------------------------
    public Vector2D<Float> normalize(Vector2D<Float> vector) {
        float magnitude = (float) Math.sqrt(vector.x * vector.x + vector.y * vector.y);
        if (magnitude > 0) {
            return new Vector2D<Float>(vector.x / magnitude, vector.y / magnitude);
        } else {
            return new Vector2D<Float>(0f, 0f);
        }
    }

    // ------------------------------------------------------------------------
    /*! getDirection
    *
    *   Utility Function for Getting the Direction of a Vector
    */ //----------------------------------------------------------------------
    public void getDirection(Vector2D<Float> vector) {

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
    /*! animate
    *
    *   Adds the needed animation to the Enemy
    */ //----------------------------------------------------------------------
    public void animate() {
        if(mAnimation.MustComplete()){return;}

        if(this.healthPoints == 0){
            this.delay = 0;
            this.speed = 0;
            //SetAnimation(UP, mAnimation.GetSpriteSheet().GetSpriteArray(UP), this.delay);
            return;
        }

        if(chase){
            switch (direction){
                case UP:
                    if(mCurrentAnimation != UP || mAnimation.GetAnimation().GetDelay() == -1) {
                        SetAnimation(UP, mAnimation.GetSpriteSheet().GetSpriteArray(UP), this.delay);
                    }
                    break;
                case DOWN:
                    if(mCurrentAnimation != DOWN || mAnimation.GetAnimation().GetDelay() == -1) {
                        SetAnimation(DOWN, mAnimation.GetSpriteSheet().GetSpriteArray(DOWN), this.delay);
                    }
                    break;
                case LEFT:
                    if(mCurrentAnimation != RIGHT || mAnimation.GetAnimation().GetDelay() == -1) {
                        SetAnimation(RIGHT, mAnimation.GetSpriteSheet().GetSpriteArray(RIGHT), this.delay);
                    }
                    break;
                case RIGHT:
                    if(mCurrentAnimation != LEFT || mAnimation.GetAnimation().GetDelay() == -1) {
                        SetAnimation(LEFT, mAnimation.GetSpriteSheet().GetSpriteArray(LEFT), this.delay);
                    }
                    break;
            }
        }else{
            switch (direction){
                case UP:
                    if(mCurrentAnimation != UP || mAnimation.GetAnimation().GetDelay() == -1) {
                        SetAnimation(UP, mAnimation.GetSpriteSheet().GetSpriteArray(UP), this.delay);
                    }
                    break;
                case DOWN:
                    if(mCurrentAnimation != DOWN || mAnimation.GetAnimation().GetDelay() == -1) {
                        SetAnimation(DOWN, mAnimation.GetSpriteSheet().GetSpriteArray(DOWN), this.delay);
                    }
                    break;
                case LEFT:
                    if(mCurrentAnimation != RIGHT || mAnimation.GetAnimation().GetDelay() == -1) {
                        SetAnimation(RIGHT, mAnimation.GetSpriteSheet().GetSpriteArray(RIGHT), this.delay);
                    }
                    break;
                case RIGHT:
                    if(mCurrentAnimation != LEFT || mAnimation.GetAnimation().GetDelay() == -1) {
                        SetAnimation(LEFT, mAnimation.GetSpriteSheet().GetSpriteArray(LEFT), this.delay);
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
        //System.out.println(vision());
        decisionMaking();
        attack();
        pseudoPositionUpdate();
        mCollision.Update();
        //System.out.println(playerPos.x + " " + playerPos.y + " " + normalizedDirection+ " " );
    }
    // ------------------------------------------------------------------------
    /*! decisionMaking
    *
    *   Decides the behavior of the Enemy based on if it is being knocked back and if the player is in its vision
    */ //----------------------------------------------------------------------
    public void decisionMaking(){
        if(!knockback){
            checkVision(); 
        }else{
            knockbackRepeat();
        }
    }
    // ------------------------------------------------------------------------
    /*! checkVision
    *
    *   If player is in vision, calls pathfinding
    */ //----------------------------------------------------------------------
    public void checkVision(){
        if(vision()){
            pathfinding();
            getDirection(normalizedDirection);
            animate();
            move();
        }else{
            animate();
            if(!path.empty()){
                path.clear();
            }
            
        }
    }

    // ------------------------------------------------------------------------
    /*! pathfinding
    *
    *   Calculates the path to the player if the path is unblocked and is not the same as the last time A* was called
    */ //----------------------------------------------------------------------
    public void pathfinding() {
        Pair enemyTile = positionToPair(World.GetLevelSpaceCoordinates(getPseudoPosition()));
        finalDestination = positionToPair(World.GetLevelSpaceCoordinates(playerPos));
        if(isDestinationChanged() && isDestinationReachable()){
            lastFinalDestination = finalDestination;
            path = aStarSearch.aStarSearch(enemyTile, finalDestination);
        }
    }

    // ------------------------------------------------------------------------
    /*! positionToPair
    *
    *   Changes the position given to a Pair
    */ //----------------------------------------------------------------------
    public Pair positionToPair(Vector2D<Float> position) {
        int divisior = 64;
        Pair pair = new Pair(Math.round((position.x/divisior)), Math.round((position.y/divisior)));
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
    /*! movementVector
    *
    *   Calculates the movement of the Enemy
    */ //----------------------------------------------------------------------
    public void movementVector() {
        Vector2D<Float> dir = new Vector2D<Float>(playerPos.x - pseudoPos.x, playerPos.y - pseudoPos.y);
        normalizedDirection=normalize(dir);
    }


    // ------------------------------------------------------------------------
    /*! move
    *
    *   Receives the movements in a stack and sets the movement of the Enemy with the A* search
    */ //----------------------------------------------------------------------
    public void move() {
        if(!path.isEmpty()){
            if(!path.isEmpty()){
                currentDestination = path.peek();
            }
            // Margin of error for the movement
            lowerBounds = Engine.ECSystem.World.GetWorldSpaceCoordinates(new Vector2D<Float>((float)((currentDestination.getFirst()*64) - 3), (float)((currentDestination.getSecond()*64) - 3)));
            upperBounds = Engine.ECSystem.World.GetWorldSpaceCoordinates(new Vector2D<Float>((float)((currentDestination.getFirst()*64) + 3), (float)((currentDestination.getSecond()*64) + 3)));

            //If currentDestination reached, pop next destination
            if(((((lowerBounds.x <= pseudoPos.x) && (pseudoPos.x <= upperBounds.x)) && ((lowerBounds.y <= pseudoPos.y+GetScale().y/2) && (pseudoPos.y+GetScale().y/2 <= upperBounds.y)))) && (currentDestination != finalDestination) && !path.isEmpty()){
                path.pop();
                if(!path.isEmpty()){
                    currentDestination = path.peek();
                }
                normalizedDirection = normalize(Engine.ECSystem.World.GetWorldSpaceCoordinates(pseudoPosToDest()));
                pos.x += normalizedDirection.x * speed;
                pos.y += normalizedDirection.y * speed;
            }else{
                normalizedDirection = normalize(Engine.ECSystem.World.GetWorldSpaceCoordinates(pseudoPosToDest()));
                pos.x += normalizedDirection.x * speed;
                pos.y += normalizedDirection.y * speed;
            }
        //If finalDestination reached, chase player directly
        }else if(currentDestination.getFirst() == finalDestination.getFirst() && currentDestination.getSecond() == finalDestination.getSecond()){
                movementVector();
                pos.x += normalizedDirection.x * speed;
                pos.y += normalizedDirection.y * speed;
        }

        SetPosition(pos);
    }
    
    public Vector2D<Float> pseudoPosToDest(){
        return new Vector2D<Float>(currentDestination.getFirst()*64 - (pseudoPos.x), currentDestination.getSecond()*64 - (pseudoPos.y+GetScale().y/2));
    }
    
    public void knockBack() {
        knockback=true;
        Vector2D<Float> dir = pos.getVectorToAnotherActor(playerPos);
        normalizedDirection=normalize(dir);
        
    }
    
    public void knockbackRepeat(){
        if (aStarSearch.isUnBlocked(positionToPair(getPseudoPosition()).getFirst(),positionToPair(getPseudoPosition()).getSecond())){
            pos.x -= normalizedDirection.x * 7;
            pos.y -= normalizedDirection.y * 7;
        }else{
            pos.x += normalizedDirection.x * 7;
            pos.y += normalizedDirection.y * 7;
        }
        
        knockbackCounter++;
        SetPosition(pos);
        if(knockbackCounter==10){
            knockback=false;
            knockbackCounter=0;
        }
    }

    public void knockBack(Vector2D<Float> attackerPos) {
        knockback=true;
        Vector2D<Float> dir = pos.getVectorToAnotherActor(attackerPos);
        normalizedDirection=normalize(dir);
    }
    

    public void setHealthPoints(int damage){
        this.healthPoints -= damage;
        if (healthPoints <= 0){
            die();
        }
        //______________________
        //______________________
    }

    private void die() {
        //Log v = Logger.Instance().GetLog("Gameplay");
        //Logger.Instance().Log(v, "Enemy died", Level.INFO, 1, Color.GREEN);

        mCollision.ShutDown();
        path.clear();
        new DeadAnimation(this);
        Sound sound = new Sound(AssetManager.Instance().GetResource("Content/Audio/Props/enemy-death.wav"));
        Audio.Instance().Play(sound);
    }
    /** Used to kill the enemy via code
     * 
     */
    public void superDie() {
        mCollision.ShutDown();
        path.clear();
        SetScale(new Vector2D<>(0f, 0f));
    }

    private boolean vision(){
        Vector2D<Float> dir = pseudoPos.getVectorToAnotherActor(playerPos);
        float distance = dir.getModule();
        if ((distance < 700)){
            if(!chase) {
                Sound sound = new Sound(AssetManager.Instance().GetResource("Content/Audio/Props/soldier.wav"));
                Audio.Instance().Play(sound);
            }

            chase = true;
            return true;
        }else{
            return false;
        }
        
    }

    @Override
    public void Render(Graphics2D g, CameraComponent camerapos) {
        var camcoord = camerapos.GetCoordinates();
        g.setColor(Color.green);
        Stack<Pair> mPath = (Stack<Pair>)path.clone();

        while (!mPath.isEmpty()) {
            Pair x = mPath.pop();
            Pair p = Engine.ECSystem.World.GetLevelPair(x);
            g.drawRect(p.getFirst() * 64 - (int)(float)camcoord.x, p.getSecond() * 64 - (int)(float)camcoord.y, 64, 64);
        }
    }

    // Getters and Setters

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

    public void attack(){
        if(ColliderManager.GetColliderManager().playerCollision(mCollision)){
            player.setDamage(damage);
        }
    }

    @Override 
    public Class GetSuperClass(){return Enemy.class;}
}
