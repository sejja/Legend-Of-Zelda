package Gameplay;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicInteger;

import Engine.ECSystem.Actor;
import Engine.Graphics.Animation;
import Engine.Graphics.Spritesheet;
import Engine.Graphics.Components.AnimationMachine;
import Engine.Input.InputFunction;
import Engine.Input.InputManager;
import Engine.Math.Vector2D;

public class Player extends Actor {
    private final int RIGHT = 0;
    private final int LEFT = 1;
    private final int DOWN = 2;
    private final int UP = 3;
    protected boolean up = false;
    protected boolean down = false;
    protected boolean right = false;
    protected boolean left = false;
    protected int mCurrentAnimation;
    protected AnimationMachine mAnimation;

    private boolean arc;
    private boolean lighter;
    private boolean bomb;
    
    protected AtomicInteger healthPoints = new AtomicInteger(10);
    final private  int damage = 2;


    /*! Conversion Constructor
    *
    *   Constructs a Player with a sprite, a position, and gives it a size
    */ //----------------------------------------------------------------------
    public Player(Spritesheet sprite, Vector2D<Float> position, Vector2D<Float> size) {
        super(position);
        SetScale(size);

        //Lets transpose the Sprite Matrix
        sprite.setmSpriteArray(transposeMatrix(sprite.GetSpriteArray2D()));
        //---------------------------------------------------------------------

        mAnimation = AddComponent(new AnimationMachine(this, sprite));
        SetAnimation(LEFT, sprite.GetSpriteArray(RIGHT), 50);

        InputManager.SubscribePressed(KeyEvent.VK_W, new InputFunction() {
            @Override
            public void Execute() {
                up = true;
                down = false;
                left = false;
                right = false;
             }
        });

        InputManager.SubscribePressed(KeyEvent.VK_S, new InputFunction() {
            @Override
            public void Execute() {
                up = false;
                down = true;
                left = false;
                right = false;
            }
        });

        InputManager.SubscribePressed(KeyEvent.VK_A, new InputFunction() {
            @Override
            public void Execute() {
               up = false;
                down = false;
                left = true;
                right = false;
            } 
        });

        InputManager.SubscribePressed(KeyEvent.VK_D, new InputFunction() {
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
        System.out.println(pos.toString());
        if(up) {
            pos.y -= 1;
        }

        if(down) {
            pos.y += 1;
        }

        if(left) {
            pos.x -= 1;
        }

        if(right) {
            pos.x += 1;
        }

        SetPosition(pos);
    }
    // ------------------------------------------------------------------------
    /*! Transpose
    *
    *   ret -> transposed BufferedImage 2D Matrix
    */ //----------------------------------------------------------------------
    private BufferedImage[][] transposeMatrix(BufferedImage [][] m){
        BufferedImage[][] temp = new BufferedImage[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
        return temp;
    }
}
