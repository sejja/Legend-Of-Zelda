package Gameplay.Interactives;



import java.awt.image.BufferedImage;

import Engine.ECSystem.ObjectManager;
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

public abstract class Interactive extends Actor{

    protected Pair mPositionPair;

    //stats
    protected int healthPoints = 1;

    //components
    protected int mCurrentAnimation;
    protected AnimationMachine mAnimation;
    protected BoxCollider mCollision;
    // ------------------------------------------------------------------------
    /*! Conversion Constructor
    *
    *   Constructs in a position,
    */ //----------------------------------------------------------------------
    public Interactive( Vector2D<Float> position) {
        super(position);
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
    /*! Animate
    *
    *   Adds the needed animation to the Enemy
    */ //----------------------------------------------------------------------
    public void Animate() {
        SetAnimation(0, mAnimation.GetSpriteSheet().GetSpriteArray(0), -1);
    }

    // ------------------------------------------------------------------------
    /*! Update
    *
    *   Adds Behavior to the Enemy
    */ //----------------------------------------------------------------------
    public void Update() {
        super.Update();
        Animate();
        Move();
    }

    // ------------------------------------------------------------------------
    /*! PositionToPair
    *
    *   Changes the position given to a Pair
    */ //----------------------------------------------------------------------
    public Pair PositionToPair(Vector2D<Float> position) {
        int divisior = 64;
        Pair pair = new Pair((int)Math.floor(position.x/divisior), (int)Math.floor(position.y/divisior));
        return pair;
    }

    // ------------------------------------------------------------------------
    /*! Move
    *
    *   Receives the movements in a stack and sets the movement of the Enemy with the A* search
    */ //----------------------------------------------------------------------
    public void Move() {
        
    }



    public int getHealthPoints() {
        return healthPoints; 
    }
    
    public BoxCollider getmCollision() {
        return mCollision;
    }

    @Override
    public Class GetSuperClass(){return Interactive.class;}
}
