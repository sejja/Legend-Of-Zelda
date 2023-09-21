package Gameplay;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import Engine.ECSystem.Types.Actor;
import Engine.Graphics.Animation;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Graphics.Components.FontComponent;
import Engine.Input.InputFunction;
import Engine.Input.InputManager;
import Engine.Math.Vector2D;

public class Player extends Actor {
    private final int UP = 0;
    private final int DOWN = 1;
    private final int RIGHT = 2;
    private final int LEFT = 3;
    protected boolean up = false;
    protected boolean down = false;
    protected boolean right = true;
    protected boolean left = false;
    protected int mCurrentAnimation;
    protected AnimationMachine mAnimation;
    protected FontComponent mFont;
    // ------------------------------------------------------------------------
    /*! Conversion Constructor
    *
    *   Constructs a Player with a sprite, a position, and gives it a size
    */ //----------------------------------------------------------------------
    public Player(Spritesheet sprite, Vector2D<Float> position, Vector2D<Float> size) {
        super(position);
        SetScale(size);
        mAnimation = AddComponent(new AnimationMachine(this, sprite));
        mFont = AddComponent(new FontComponent(this, "Content/Fonts/ZeldaFont.png"));
        mFont.SetString("Un link parlante");
        mFont.SetScale(new Vector2D<>(0.2f, 0.2f));
        mFont.SetGlyph(new Vector2D<>(32, 0));
        SetAnimation(RIGHT, sprite.GetSpriteArray(RIGHT), 10);

        InputManager.SubscribePressed(KeyEvent.VK_UP, new InputFunction() {
            @Override
            public void Execute() {
                up = true;
                down = false;
                left = false;
                right = false;
             }
        });

        InputManager.SubscribePressed(KeyEvent.VK_DOWN, new InputFunction() {
            @Override
            public void Execute() {
                up = false;
                down = true;
                left = false;
                right = false;
            }
        });

        InputManager.SubscribePressed(KeyEvent.VK_LEFT, new InputFunction() {
            @Override
            public void Execute() {
               up = false;
                down = false;
                left = true;
                right = false;
            } 
        });

        InputManager.SubscribePressed(KeyEvent.VK_RIGHT, new InputFunction() {
            @Override
            public void Execute() {
                up = false;
                down = false;
                left = false;
                right = true;
            }
        });
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
