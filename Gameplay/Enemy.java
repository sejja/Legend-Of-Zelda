package Gameplay.Enemies;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import Engine.ECSystem.Actor;
import Engine.Graphics.Animation;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Math.Vector2D;

public class Enemy extends Actor{
    private final int UP = 0;
    private final int DOWN = 1;
    private final int RIGHT = 2;
    private final int LEFT = 3;
    protected boolean up = false;
    protected boolean down = false;
    protected boolean right = true;
    protected boolean left = false;
    protected boolean chase = false;
    protected int mCurrentAnimation;
    protected AnimationMachine mAnimation;
    
    
    // ------------------------------------------------------------------------
    /*! Conversion Constructor
    *
    *   Constructs a Player with a sprite, a position, and gives it a size
    */ //----------------------------------------------------------------------
    public Enemy(Spritesheet sprite, Vector2D<Float> position, Vector2D<Float> size) {
        super(position);
        SetScale(size);
        mAnimation = AddComponent(new AnimationMachine(this, sprite));
        SetAnimation(RIGHT, sprite.GetSpriteArray(RIGHT), 10);
    }

    public void SetAnimation(int i, BufferedImage[] frames, int delay) {
        mCurrentAnimation = i;
        mAnimation.GetAnimation().SetFrames(frames);
        mAnimation.GetAnimation().SetDelay(delay);
    }

    public Animation GetAnimation() {
        return mAnimation.GetAnimation();
    }

    public void Animate() {
        if(chase){
            if(up) {
                if(mCurrentAnimation != UP || mAnimation.GetAnimation().GetDelay() == -1) {
                    SetAnimation(UP, mAnimation.GetSpriteSheet().GetSpriteArray(UP), 5);
                }
            } else if(down) {
                if(mCurrentAnimation != DOWN || mAnimation.GetAnimation().GetDelay() == -1) {
                    SetAnimation(DOWN, mAnimation.GetSpriteSheet().GetSpriteArray(DOWN), 5);
                }
            } else if(right) {
                if(mCurrentAnimation != RIGHT || mAnimation.GetAnimation().GetDelay() == -1) {
                    SetAnimation(RIGHT, mAnimation.GetSpriteSheet().GetSpriteArray(RIGHT), 5);
                }
            } else {
                if(mCurrentAnimation != LEFT || mAnimation.GetAnimation().GetDelay() == -1) {
                    SetAnimation(LEFT, mAnimation.GetSpriteSheet().GetSpriteArray(LEFT), 5);
                }
            }
        }else{
            if(up) {
                if(mCurrentAnimation != UP || mAnimation.GetAnimation().GetDelay() == -1) {
                    SetAnimation(UP, mAnimation.GetSpriteSheet().GetSpriteArray(UP), 5);
                }
            } else if(down) {
                if(mCurrentAnimation != DOWN || mAnimation.GetAnimation().GetDelay() == -1) {
                    SetAnimation(DOWN, mAnimation.GetSpriteSheet().GetSpriteArray(DOWN), 5);
                }
            } else if(right) {
                if(mCurrentAnimation != RIGHT || mAnimation.GetAnimation().GetDelay() == -1) {
                    SetAnimation(RIGHT, mAnimation.GetSpriteSheet().GetSpriteArray(RIGHT), 5);
                }
            } else {
                if(mCurrentAnimation != LEFT || mAnimation.GetAnimation().GetDelay() == -1) {
                    SetAnimation(LEFT, mAnimation.GetSpriteSheet().GetSpriteArray(LEFT), 5);
                }
            }
        }
    }

    // ------------------------------------------------------------------------
    /*! Update
    *
    *   Adds Behavior to the Player
    */ //----------------------------------------------------------------------
    @Override
    public void Update() {
        super.Update();
        Move();
        Animate();
        mAnimation.GetAnimation().SetDelay(50);
    }

    // ------------------------------------------------------------------------
    /*! Move
    *
    *   Moves the sprite on a certain direction
    */ //----------------------------------------------------------------------
    public void Move() {
        Vector2D<Float> pos = GetPosition();

        if(up) {
            pos.y += 1;
        }

        if(down) {
            pos.y -= 1;
        }

        if(left) {
            pos.x -= 1;
        }

        if(right) {
            pos.x += 1;
        }

        SetPosition(pos);
    }    
}
