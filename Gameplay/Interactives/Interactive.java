package Gameplay.Interactives;



import java.awt.image.BufferedImage;
import java.util.Vector;

import Engine.ECSystem.ObjectManager;
import Engine.ECSystem.World;
import Engine.ECSystem.Types.Actor;
import Engine.Graphics.GraphicsPipeline;
import Engine.Graphics.Animations.Animation;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Graphics.Components.CameraComponent;
import Engine.Graphics.Tile.*;
import Engine.Math.Vector2D;
import Engine.Physics.Components.BoxCollider;
import Gameplay.Enemies.Enemy;
import Gameplay.Enemies.Search.AStarSearch;
import Gameplay.Enemies.Search.Pair;
import Gameplay.LifeBar.LifeBar;
import Gameplay.Link.DIRECTION;
import Gameplay.NPC.Npc;

/**
 * Represents an interactive object in the game.
 * Extends the Actor class.
 */
public abstract class Interactive extends Actor{

    protected Pair mPositionPair;
    protected Block block;
    protected Vector2D<Float> pos; // position for invisible blocks

    //stats
    protected int healthPoints = 1;

    //components
    protected int mCurrentAnimation;
    protected AnimationMachine mAnimation;
    protected BoxCollider mCollision;

    /**
     * Constructs an interactive object at the specified position.
     *
     * @param position The initial position of the interactive object.
     */
    public Interactive( Vector2D<Float> position) {
        super(position);
        pos = position;
        mPositionPair = World.GetLevelPair(PositionToPair(getPseudoPosition()));

        //System.out.println("Interactive placed at: " + mPositionPair.getFirst() + " " + mPositionPair.getSecond());
        block = TileManager.sLevelObjects.GetBlockAt(new Vector2D(mPositionPair.getFirst(),mPositionPair.getSecond()));
        if(block == null) {
            TileManager.sLevelObjects.PlaceBlockAt(new Vector2D<>(mPositionPair.getFirst(), mPositionPair.getSecond()));
        }
    }

    /**
     * Sets the animation for the interactive object.
     *
     * @param i      The animation index.
     * @param frames The array of animation frames.
     * @param delay  The delay between frames.
     */
    public void SetAnimation(int i, BufferedImage[] frames, int delay) {
        mCurrentAnimation = i;
        mAnimation.GetAnimation().SetFrames(frames);
        mAnimation.GetAnimation().SetDelay(delay);
    }

    /**
     * Gets the current animation of the interactive object.
     *
     * @return The current animation.
     */
    public Animation GetAnimation() {
        return mAnimation.GetAnimation();
    }

    /**
     * Adds the needed animation for the interactive object.
     */
    public void Animate() {
        SetAnimation(0, mAnimation.GetSpriteSheet().GetSpriteArray(0), 1);
    }

    /**
     * Updates the interactive object.
     */
    public void Update() {
        super.Update();
        Animate();
        Move();
    }

    /**
     * Converts the position to a pair.
     *
     * @param position The position to be converted.
     * @return The pair representing the position.
     */
    public Pair PositionToPair(Vector2D<Float> position) {
        int divisior = 64;
        Pair pair = new Pair((int)Math.floor(position.x/divisior), (int)Math.floor(position.y/divisior));
        return pair;
    }

    /**
     * Moves the interactive object.
     */
    public void Move() {
        SetPosition(pos); // This fixes invisible blocks
    }

    /**
     * Gets the health points of the interactive object.
     *
     * @return The health points.
     */
    public int getHealthPoints() {
        return healthPoints; 
    }
    
    /**
     * Gets the box collider component of the interactive object.
     *
     * @return The box collider component.
     */
    public BoxCollider getmCollision() {
        return mCollision;
    }

    /**
     * Gets the superclass of the interactive object.
     *
     * @return The class representing the superclass.
     */
    @Override
    public Class GetSuperClass(){return Interactive.class;}
}
